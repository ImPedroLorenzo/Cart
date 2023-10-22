package com.store.controller;

import com.store.dto.CartDTO;
import com.store.entity.Cart;
import com.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        Cart newCart = cartService.createCart();
        CartDTO cartDTO = new CartDTO(newCart.getId(), newCart.getProducts(), newCart.getLastModified());
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        Optional<Cart> cartOptional = Optional.ofNullable(cartService.getCartById(cartId));
        if (!cartOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = cartOptional.get();
        CartDTO cartDTO = new CartDTO(cart.getId(), cart.getProducts(), cart.getLastModified());
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

}

