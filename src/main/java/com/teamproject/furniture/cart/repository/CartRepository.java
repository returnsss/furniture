package com.teamproject.furniture.cart.repository;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.CartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * 장바구니에서 특정 userId에 해당하는 상품들 가져오기
     * @param userId
     * @return
     */
    List<CartDto> findByUserId(String userId);

    /**
     * 장바구니에서 특정 상품 삭제
     * @param userId
     * @param cartId
     */
    //void deleteByCartIdAndUserId(Long cartId, String userId);

    /**
     * 장바구니 전체 삭제
     * @param userId
     */
    void deleteAllByUserId(String userId);

    // Optional<Cart> findByCartIdAndUserId(Long cartId, String userId);
}
