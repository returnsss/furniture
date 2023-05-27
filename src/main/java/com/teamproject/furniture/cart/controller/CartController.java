package com.teamproject.furniture.cart.controller;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.AddCartDto;
import com.teamproject.furniture.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/{productId}")
    public void addToCart(@PathVariable String userId, @PathVariable Long productId, @RequestBody AddCartDto cartDto) {
        cartService.addToCart(cartDto, userId, productId);

    }



    @GetMapping("/{userId}")
    public List<Cart> getCartItems(@PathVariable String userId) {
        List<Cart> cartItems = cartService.getCartItems(userId);
        return cartItems;
    }

    @DeleteMapping("/{cartId}/{userId}")
    public void removeCartItem(@PathVariable Long cartId, @PathVariable String userId) {
        cartService.removeCartItem(cartId, userId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);

    }

    @PostMapping("/{userId}/{cartId}/{cnt}")
    public void updatCartItemCount(@PathVariable String userId, @PathVariable Long cartId, @RequestParam int cnt){
        cartService.updateCartItemCount(userId, cartId, cnt);
    }

    @GetMapping("/{userId}/total-price")
    public int getCartTotalPrice(@PathVariable String userId) {
        return cartService.cartTotalPrice(userId);
    }


}
