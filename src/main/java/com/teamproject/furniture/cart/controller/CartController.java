package com.teamproject.furniture.cart.controller;

import com.teamproject.furniture.cart.dtos.CartDto;
import com.teamproject.furniture.cart.service.CartService;
import com.teamproject.furniture.member.dtos.UserDto;
import com.teamproject.furniture.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/product")
    public void addToCart(@RequestBody CartDto cartDto) throws Exception {
        String userId = UserUtil.getUserId();
        String sessionId = UserUtil.getSessionId();
        cartService.addToCart(cartDto, userId, sessionId);

    }



    @GetMapping("/{userId}")
    public List<CartDto> getCartItems(@PathVariable String userId) {
        List<CartDto> cartItems = cartService.getCartItems(userId);
        return cartItems;
    }

    @DeleteMapping("/{cartId}")
    public void removeCartItem(@AuthenticationPrincipal UserDto userDto, @PathVariable Long cartId) {
        String userId = userDto.getUserId();
        cartService.removeCartItem(userId, cartId);
    }

    @DeleteMapping("")
    public void clearCart(@AuthenticationPrincipal UserDto userDto) {
        String userId = userDto.getUserId();
        cartService.clearCart(userId);

    }

    @PostMapping("/{cartId}/cnt")
    public void updateCartItemCount(@AuthenticationPrincipal UserDto userDto, @PathVariable Long cartId, @RequestParam int cnt){
        String userId = userDto.getUserId();
        cartService.updateCartItemCount(userId, cartId, cnt);
    }

    @GetMapping("/{userId}/total-price")
    public int getCartTotalPrice(@PathVariable String userId) {
        return cartService.cartTotalPrice(userId);
    }


}
