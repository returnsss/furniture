package com.teamproject.furniture.cart.controller;

import com.teamproject.furniture.cart.domain.Cart;
import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/{productId}")
    public void addToCart(@AuthenticationPrincipal UserDto userDto, @PathVariable Long productId, @RequestBody CartDto cartDto) {
        String userId = userDto.getUserId();
        cartService.addToCart(cartDto, userId, productId);

    }



    @GetMapping("/{userId}")
    public List<CartDto> getCartItems(@PathVariable String userId) {
        List<CartDto> cartItems = cartService.getCartItems(userId);
        return cartItems;
    }

    @DeleteMapping("/{userId}/{cartId}")
    public void removeCartItem(@PathVariable String userId, @PathVariable Long cartId) {
        cartService.removeCartItem(userId, cartId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@AuthenticationPrincipal UserDto userDto) {
        String userId = userDto.getUserId();
        cartService.clearCart(userId);

    }

    @PostMapping("/{userId}/{cartId}/cnt")
    public void updatCartItemCount(@AuthenticationPrincipal UserDto userDto, @PathVariable Long cartId, @RequestParam int cnt){
        String userId = userDto.getUserId();
        cartService.updateCartItemCount(userId, cartId, cnt);
    }

    @GetMapping("/{userId}/total-price")
    public int getCartTotalPrice(@PathVariable String userId) {
        return cartService.cartTotalPrice(userId);
    }


}
