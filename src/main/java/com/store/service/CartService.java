package com.store.service;

import com.store.dto.ProductDTO;
import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart createCart() {
        return cartRepository.save(new Cart(new ArrayList<>(),new Date()));
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    //TODO: methods for adding/removing products to the cart
}