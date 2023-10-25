package com.store.service;

import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class DataService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    public List<Cart> insertData() {

        //clean
        cartRepository.deleteAll();
        productRepository.deleteAll();

        //insert some  example carts
        Cart cart1 = cartRepository.save(new Cart( new ArrayList<>(), new Date()));
        Cart cart2 = cartRepository.save(new Cart( new ArrayList<>(), new Date()));
        Cart cart3 = cartRepository.save(new Cart( new ArrayList<>(), new Date()));
        Cart cart4 = cartRepository.save(new Cart( new ArrayList<>(), new Date()));

        //insert some  example carts
        Product product1 = productRepository.save(new Product( "shirt", 100L));
        Product product2 = productRepository.save(new Product( "pants", 90L));
        Product product3 = productRepository.save(new Product( "caps", 300L));
        Product product4 = productRepository.save(new Product( "socks", 2000L));

        //add products to carts
        cartService.addProductsToCart(cart1.getId(), List.of(product1,product2));
        cartService.addProductsToCart(cart2.getId(), new ArrayList<>());
        cartService.addProductsToCart(cart3.getId(), List.of(product1,product2,product3,product4));
        cartService.addProductsToCart(cart4.getId(), List.of(product1,product2));

        return cartRepository.findAll();
    }
}
