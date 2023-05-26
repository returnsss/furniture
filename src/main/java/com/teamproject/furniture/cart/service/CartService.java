package com.teamproject.furniture.cart.service;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.AddCartDto;
import com.teamproject.furniture.cart.repository.CartRepository;
import com.teamproject.furniture.member.model.Member;
import com.teamproject.furniture.member.repository.MemberRepository;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    public CartService(CartRepository cartRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void addToCart(AddCartDto addCartDto, String userId, Long productId, int cnt){
        // 상품 정보 가져오기
        Product product = productRepository.findByProductId(productId);

        if(product == null){
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        // 유저 정보 가져오기
        Optional<Member> member = memberRepository.findByUserId(userId);
        member.get();

        if(member == null){
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        // 주문번호 생성(데이터 입력 날짜시간초단위 + userId)
        LocalDateTime now = LocalDateTime.now();
        String orderNum = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + userId;

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

    public void removeCartItem(String userId, Long cartId) {
        // cartRepository.findByCartId(cartId);
        cartRepository.deleteByCartIdAndUserId(cartId, userId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteAllByUserId(userId);
    }


}
