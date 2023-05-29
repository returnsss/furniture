package com.teamproject.furniture.cart.service;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.AddCartDto;
import com.teamproject.furniture.cart.repository.CartRepository;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void addToCart(AddCartDto addCartDto, String userId, Long productId){
        // 상품 정보 가져오기
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));

        // 유저 정보 가져오기
        Member member = memberRepository.findByUserId(userId).orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다."));


        // 주문번호 생성(데이터 입력 날짜시간초단위 + userId)
        LocalDateTime now = LocalDateTime.now();
        String orderNum = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + userId;

        // 장바구니에 담을 때 개수는 1개로 고정
        int cnt = 1;

        // 장바구니에 상품 추가
        Cart cart = new Cart(addCartDto);
        cart.setProductId(productId);
        cart.setProductName(product.getProductName());
        cart.setUserId(userId);
        cart.setOrderNum(orderNum);
        cart.setProductPrice(product.getProductPrice());
        cart.setCnt(cnt);

        cartRepository.save(cart);


    }

    public List<Cart> getCartItems(String userId){
        return cartRepository.findByUserId(userId);
    }

    public void removeCartItem(Long cartId, String userId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalStateException("존재하지 않는 cartId입니다."));

        if(userId.equals(cart.getUserId())){
            cartRepository.deleteById(cartId);
        }
        else {
            new Exception("userId가 일치하지 않아 실행할수 없습니다.");
        }
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
        //Cart cart = cartRepository.findByCartIdAndUserId(cartId, userId).orElseThrow(() -> new NoSuchElementException("장바구니 항목을 찾을 수 없습니다."));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalStateException("존재하지 않는 cartId입니다."));

        if(userId.equals(cart.getUserId())) {
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
