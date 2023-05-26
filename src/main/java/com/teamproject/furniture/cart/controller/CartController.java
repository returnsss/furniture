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

    @PostMapping("/{productId}/{userId}")
    public void addToCart(@PathVariable String userId, @PathVariable Long productId, @RequestBody AddCartDto cartDto) {
        cartService.addToCart(cartDto, userId, productId, cartDto.getCnt());

    }



    @GetMapping("/{userId}")
    public List<Cart> getCartItems(@PathVariable String userId) {
        List<Cart> cartItems = cartService.getCartItems(userId);
        return cartItems;
    }

    @DeleteMapping("/{userId}/{cartId}")
    public void removeCartItem(@PathVariable String userId, @PathVariable Long cartId) {
        cartService.removeCartItem(userId, cartId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);

    }

}
