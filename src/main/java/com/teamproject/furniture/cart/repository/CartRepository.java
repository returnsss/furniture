package com.teamproject.furniture.cart.repository;

import com.teamproject.furniture.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    //Cart findByCartId(Long CartId);

    /**
     * 장바구니에서 특정 userId에 해당하는 상품들 가져오기
     * @param userId
     * @return
     */
    List<Cart> findByUserId(String userId);

    /**
     * 장바구니에서 특정 상품 삭제
     * @param cartId
     * @param userId
     */
    void deleteByCartIdAndUserId(Long cartId, String userId);

    /**
     * 장바구니 전체 삭제
     * @param userId
     */
    void deleteAllByUserId(String userId);

}
