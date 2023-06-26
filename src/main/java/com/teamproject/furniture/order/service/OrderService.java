package com.teamproject.furniture.order.service;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.domain.OrderInfo;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.dtos.OrderStep;
import com.teamproject.furniture.order.repository.OrderDataRepository;
import com.teamproject.furniture.order.repository.OrderInfoRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderService {
    private OrderDataRepository orderDataRepository;
    private OrderInfoRepository orderInfoRepository;

    public OrderService(OrderDataRepository orderDataRepository, OrderInfoRepository orderInfoRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderInfoRepository = orderInfoRepository;
    }

    public void addToOrderData(List<OrderDataDto> orderDataDtoList, HttpSession session){
        String orderNum = getOrderNum(session);

        orderDataRepository.deleteOrderDataByOrderNum(orderNum);

        List<OrderData> orderDataList = new ArrayList<>();

        for (OrderDataDto orderDataDto : orderDataDtoList){
            OrderData orderData = new OrderData(orderDataDto);
            orderData.setOrderNum(orderNum);
            orderDataList.add(orderData);
        }

        orderDataRepository.saveAll(orderDataList);

    }

    public List<OrderDataDto> getOrderDatas(HttpSession session){
        String orderNum = getOrderNum(session);
        List<OrderData> orderDatas = orderDataRepository.findByOrderNum(orderNum);

        return orderDatas.stream().map(orderData -> {
            OrderDataDto orderDataDto = new OrderDataDto();
            orderDataDto.setNum(orderData.getNum());
            orderDataDto.setOrderNum(orderData.getOrderNum());
            orderDataDto.setCartId(orderData.getCartId());
            orderDataDto.setProductId(orderData.getProductId());
            orderDataDto.setProductName(orderData.getProductName());
            orderDataDto.setProductPrice(orderData.getProductPrice());
            orderDataDto.setCnt(orderData.getCnt());
            orderDataDto.setTotalPrice(orderData.getTotalPrice());
            return orderDataDto;
        }).collect(Collectors.toList());
    }





    public String getOrderNum(HttpSession session){
        String orderNum = (String) session.getAttribute("orderNum");
        if(orderNum == null){
            orderNum = generateOrderNum();
            session.setAttribute("orderNum", orderNum);
        }
        return orderNum;
    }

    private String generateOrderNum(){
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        return nowStr + "-" + sessionId;
    }


    public void addToOrderInfo(OrderInfoDto orderInfoDto, HttpSession session){

        String orderNum = getOrderNum(session);

        orderInfoRepository.deleteOrderInfoByOrderNum(orderNum);

        orderInfoDto.setOrderNum(orderNum);
        orderInfoDto.setOrderStep(OrderStep.ORDER_FAIL);
        OrderInfo orderInfo = new OrderInfo(orderInfoDto);

        orderInfoRepository.save(orderInfo);
    }

    public OrderInfoDto getOrderInfoDto(String orderNum){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNum).orElseThrow();
        return OrderInfoDto.builder()
                .orderNum(orderInfo.getOrderNum())
                .userId(orderInfo.getUserId())
                .orderName(orderInfo.getOrderName())
                .orderTel(orderInfo.getOrderTel())
                .orderEmail(orderInfo.getOrderEmail())
                .receiveName(orderInfo.getReceiveName())
                .receiveTel(orderInfo.getReceiveTel())
                .receiveAddress(orderInfo.getReceiveAddress())
                .orderStep(orderInfo.getOrderStep())
                .payAmount(orderInfo.getPayAmount())
                .orderDate(orderInfo.getOrderDate())
                .payDate(orderInfo.getPayDate())
                .build();

    }

    public void shippingProgress(String orderNum){
        OrderInfo orderInfo = orderInfoRepository.findById(orderNum).orElseThrow();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 사용자 인증정보 가져오기
        String currentUserId = authentication.getName(); // 현재 사용자의 아이디 가져오기

        if(!orderInfo.getUserId().equals(currentUserId)){
            throw new IllegalStateException("userId가 일치하지 않아서 실행 할 수 없습니다.");
        }
        orderInfo.setOrderStep(OrderStep.SHIPPING_PROGRESS);
        orderInfoRepository.save(orderInfo);
    }


    public String getProductName(String orderNum) throws Exception {
        List<OrderData> orderDataList = orderDataRepository.findByOrderNum(orderNum);

        if (orderDataList.isEmpty()){
            throw new Exception();
        }

        String firstProductName = orderDataList.get(0).getProductName();

        if (orderDataList.size() == 1){
            return firstProductName;
        }
        return firstProductName += " 외 " + (orderDataList.size() - 1) + "건";
    }


    public void processSuccess(HttpServletRequest req, HttpSession session) throws Exception {
        String orderId = req.getParameter("orderId");
        log.info("orderId : " + orderId);
        String paymentKey = req.getParameter("paymentKey");
        log.info("paymentKey : " + paymentKey);
        String amount = req.getParameter("amount");
        log.info("amount : " + amount);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElseThrow();

        if(!String.valueOf(orderInfo.getPayAmount()).equals(amount)){
            throw new Exception("가격이 다릅니다.");
        }


        String secretKey = "test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R"
                + ":";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic "+ new String(encodedBytes, 0, encodedBytes.length);


        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

        int code = connection.getResponseCode();


        boolean isSuccess = code >= 200 && code < 300;
        log.info("isSuccess : " + isSuccess);

        InputStream responseStream = isSuccess? connection.getInputStream(): connection.getErrorStream();
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);

        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        log.info(jsonObject.toJSONString());
        responseStream.close();

        if (isSuccess) {
            log.info("orderId : " + jsonObject.get("orderId"));
            log.info("method : " + jsonObject.get("method"));
            log.info("approvedAt : " + jsonObject.get("approvedAt"));

            orderInfo.setOrderStep(OrderStep.PAY_RECEIVE);
        }

    }



}
