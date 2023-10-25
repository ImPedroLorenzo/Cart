package com.store.service;

import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartServiceTest {

    //random number which no exist in db
    private static final Long NO_EXIST = 717L;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    private Product product;

    private Cart cart;

    @BeforeEach
    public void init() {
        //clean
        cartRepository.deleteAll();
        productRepository.deleteAll();
        //insert one cart and one product for test
        product = productRepository.save(new Product( "shirt", 10L));
        cart = cartRepository.save(new Cart( new ArrayList<>(), new Date()));
    }


    @Test
    public void testCreateCart() {
        Cart cart = cartService.createCart();
        assertNotNull(cart);
    }

    @Test
    public void testGetCartById() {
        Cart cartFromDb = cartService.getCartById(cart.getId());
        assertNotNull(cartFromDb);
    }

    @Test
    public void testGetCartByIdNotFound() {
        Cart noExist = cartService.getCartById(NO_EXIST);
        assertNull(noExist);
    }

    @Test
    public void testDeleteCart() {
        assertNotNull(cartRepository.findById(cart.getId()));
        cartService.deleteCart(cart.getId());
        assertFalse(cartRepository.findById(cart.getId()).isPresent());
    }

    @Test
    public void testAddProductsToCart() {
        //check the last modified updated
        Date beforeUpdate = cartService.getCartById(cart.getId()).getLastModified();
        //update the cart
        Cart updatedCart = cartService.addProductsToCart(cart.getId(), List.of(product));
        assertNotNull(updatedCart);
        assertEquals(product.getId(), updatedCart.getProducts().get(0).getId());
        //different dates
        assertNotEquals(beforeUpdate,updatedCart.getLastModified());
    }


    @Test
    public void testAddProductsToNoExistingCart() {
        assertThrows(RuntimeException.class, () -> {
            cartService.addProductsToCart(NO_EXIST, List.of(product));
        });
    }
}
