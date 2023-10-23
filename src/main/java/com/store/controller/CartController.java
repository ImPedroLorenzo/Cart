package com.store.controller;

import com.store.dto.CartDTO;
import com.store.dto.ProductDTO;
import com.store.dto.UtilDTO;
import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        List<CartDTO> cartDTOs = carts.stream()
                .map(cart -> new CartDTO(cart.getId(), cart.getProducts(), cart.getLastModified()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartDTOs);
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
    @PostMapping("/{cartId}/products")
    public ResponseEntity<?> addProductsToCart(
            @PathVariable Long cartId,
            @RequestBody List<ProductDTO> productsDto) {
        List<Product> products = productsDto.stream().map(UtilDTO::fromProductDTO).toList();
        try {
            Cart updatedCart = cartService.addProductsToCart(cartId, products);
            CartDTO updatedCartDTO = new CartDTO(updatedCart.getId(), updatedCart.getProducts(), updatedCart.getLastModified());
            return ResponseEntity.ok(updatedCartDTO);
        }catch(RuntimeException e){
            //TODO create an custom exception
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.NOT_FOUND.value());
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}

