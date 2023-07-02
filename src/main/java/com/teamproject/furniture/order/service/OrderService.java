package com.teamproject.furniture.order.service;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.domain.OrderInfo;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.order.dtos.OrderInfoDto;
import com.teamproject.furniture.order.dtos.OrderStep;
import com.teamproject.furniture.order.repository.OrderDataRepository;
import com.teamproject.furniture.order.repository.OrderInfoRepository;
import com.teamproject.furniture.order.repository.OrderInfoRepositoryCustom;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import com.teamproject.furniture.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderDataRepository orderDataRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final OrderInfoRepositoryCustom orderInfoRepositoryCustom;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public void addToOrderData(List<OrderDataDto> orderDataDtoList, HttpSession session){
        String orderNum = getOrderNum(session);

        List<OrderData> orderDataExist = orderDataRepository.findByOrderNum(orderNum);

        if (!orderDataExist.isEmpty()) {
            orderDataRepository.deleteOrderDataByOrderNum(orderNum);
            productService.rollBackProductLockCnt(orderDataDtoList);
        }

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
        List<OrderData> orderDataList = orderDataRepository.findByOrderNum(orderId);

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

            /*for (OrderData orderData : orderDataList){
                Product product = productRepository.findById(Long.valueOf(orderData.getProductId())).orElseThrow();

                product.setProductsInStock(product.getProductsInStock() - orderData.getCnt());
            }*/

            /**
             * 네트워크 비용 감소를 위해 해당 방식으로 변경
             */
            List<Long> productIds = orderDataList.stream()
                    .map(orderData -> Long.valueOf(orderData.getProductId()))
                    .collect(Collectors.toList());


            Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                    .collect(Collectors.toMap(Product::getProductId, Function.identity()));


            for (OrderData orderData : orderDataList) {
                Long productId = Long.valueOf(orderData.getProductId());
                Product product = productMap.get(productId);

                if (product != null) {
                    product.setLockCnt(product.getLockCnt() - orderData.getCnt());
                }
            }


            List<Product> updatedProducts = new ArrayList<>(productMap.values());
            productRepository.saveAll(updatedProducts);

        }

    }


    public Page<OrderInfoDto> selectOrderList(String searchVal, Pageable pageable, Boolean isAdmin) {

        Page<OrderInfoDto> results = orderInfoRepositoryCustom.selectOrderList(searchVal, pageable, isAdmin);


        List<OrderInfoDto> content = results.getContent();

        List<String> orderNums = content.stream().map(OrderInfoDto::getOrderNum).toList();

        List<OrderData> orderDataList = orderDataRepository.findByOrderNumIn(orderNums);

        Map<String, List<OrderDataDto>> tempMap = new HashMap<>();


//        Map<String, List<OrderDataDto>> tempMap = orderDataRepository.findByOrderNumIn(orderNums).stream()
//                .map(orderData -> {
//                    OrderDataDto orderDataDto = new OrderDataDto();
//                    orderDataDto.setOrderNum(orderData.getOrderNum());
//                    // setting 필요
//                    return orderDataDto;
//                }).collect(Collectors.groupingBy(OrderDataDto::getOrderNum));

        for (OrderData orderData : orderDataList) {
            String orderNum = orderData.getOrderNum();
            OrderDataDto orderDataDto = new OrderDataDto();
            orderDataDto.setNum(orderData.getNum());
            orderDataDto.setOrderNum(orderNum);
            orderDataDto.setCartId(orderData.getCartId());
            orderDataDto.setProductId(orderData.getProductId());
            orderDataDto.setProductName(orderData.getProductName());
            orderDataDto.setProductPrice(orderData.getProductPrice());
            orderDataDto.setCnt(orderData.getCnt());
            orderDataDto.setTotalPrice(orderData.getTotalPrice());


            List<OrderDataDto> list = tempMap.get(orderNum);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(orderDataDto);
            tempMap.put(orderNum, list);
        }

        for (OrderInfoDto orderInfoDto : content) {
            String orderNum = orderInfoDto.getOrderNum();
            List<OrderDataDto> dtoList = tempMap.get(orderNum);
            orderInfoDto.setOrderDataDtoList(dtoList);
        }


        return results;
    }
}
