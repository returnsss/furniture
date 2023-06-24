package com.teamproject.furniture.cart.service;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.repository.CartRepository;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CartService(CartRepository cartRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void addToCart(CartDto cartDto, String userId, String sessionId){
        // 상품 정보
        Long productId = cartDto.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));

        // 유저 정보 가져오기
        Optional<Member> member = memberRepository.findByUserId(userId);
        if (member.isEmpty()){
            throw new IllegalStateException("유저가 존재하지 않습니다.");
        }

        // 주문번호 생성(세션아이디 넣기)
        String orderNum = sessionId;

        // 장바구니에 담을 때 개수는 1개로 고정
        int cnt = 1;

        // 장바구니에 상품 추가
        Cart cart = new Cart(cartDto);
        cart.setProductId(productId);
        cart.setProductName(product.getProductName());
        cart.setUserId(userId);
        cart.setOrderNum(orderNum);
        cart.setProductPrice(product.getProductPrice());
        cart.setCnt(cnt);

        cartRepository.save(cart);


    }

    public List<CartDto> getCartItems(String userId){
        List<Cart> carts = cartRepository.findByUserId(userId);
        List<CartDto> cartDtoList = new ArrayList<>();

        for (Cart cart : carts) {
            CartDto cartDto = new CartDto();
            cartDto.setCartId(cart.getCartId());
            cartDto.setProductId(cart.getProductId());
            cartDto.setProductName(cart.getProductName());
            cartDto.setUserId(cart.getUserId());
            cartDto.setOrderNum(cart.getOrderNum());
            cartDto.setProductPrice(cart.getProductPrice());
            cartDto.setCnt(cart.getCnt());

            Optional<Product> byId = productRepository.findById(cartDto.getProductId());
            Optional<String> s = byId.map(Product::getImgPath);
            cartDto.setImgPath(s.orElse(""));

            cartDtoList.add(cartDto);
        }

        return cartDtoList;
    }

    public void removeCartItem(String userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalStateException("존재하지 않는 cartId입니다."));

        if(!cart.getUserId().equals(userId)){
            throw new IllegalStateException("userId가 일치하지 않아서 실행 할 수 없습니다.");
        }
        cartRepository.deleteById(cartId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteAllByUserId(userId);
    }

    /**
     * 장바구니에서 수량 변경
     * @param userId
     * @param cartId
     * @param cnt
     */
    public void updateCartItemCount(String userId, Long cartId, int cnt) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalStateException("존재하지 않는 cartId입니다."));

        if(cart.getUserId().equals(userId)){
            if (cnt <= 0) {
                throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
            }

            cart.setCnt(cnt);
            cartRepository.save(cart);
        }

    }


    public int cartTotalPrice(String userId){
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        int totalPrice = 0;

        for (Cart cart : cartItems) {
            totalPrice += cart.getProductPrice() * cart.getCnt();
        }

        return totalPrice;
    }


}
