package com.store.service;

import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getAllCarts() {
        //filter carts older than 10 minutes if are not removed yet
        return cartRepository.findAll()
                                .stream()
                                .filter(cart -> !cart.isExpired())
                                .toList();
    }
    public Cart createCart() {
        return cartRepository.save(new Cart(new ArrayList<>(),new Date()));
    }

    public Cart getCartById(Long id) {
        //filter carts older than 10 minutes if are not removed yet
        return cartRepository.findById(id)
                .filter(cart -> !cart.isExpired())
                .orElse(null);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
    public Cart addProductsToCart(Long cartId, List<Product> productsToAdd) {
        //filter carts older than 10 minutes if are not removed yet
        Optional<Cart> optCart = cartRepository.findById(cartId).filter(cart -> !cart.isExpired());

        //No cart, no products
        if (!optCart.isPresent()) {
            throw new RuntimeException("Cart with ID " + cartId + " not found");
        }
        Cart cart = optCart.get();

        for (Product productToAdd : productsToAdd) {
            Optional<Product> optProduct = productRepository.findById(productToAdd.getId());
            if (optProduct.isPresent()) {
                Product productInDb = optProduct.get();

                boolean productExistsInCart = false;

                //check if product already exists
                for (Product existingProduct : cart.getProducts()) {
                    if (existingProduct.getId().equals(productToAdd.getId())) {
                        //add amount if product already exists
                        existingProduct.setAmount(existingProduct.getAmount() + productToAdd.getAmount());
                        productExistsInCart = true;
                        break;
                    }
                }

                if (!productExistsInCart) {
                    //if the product not exist in the cart then add it
                    cart.getProducts().add(productInDb);
                }

            } else {
                throw new RuntimeException("Product with ID " + productToAdd.getId() + " not found");
            }
        }

        cart.setLastModified(new Date());
        return cartRepository.save(cart);
    }




    public void removeExpiredCarts() {
        Date expiredDate = new Date(System.currentTimeMillis()-Cart.EXPIRATION_TIME);
        cartRepository.deleteByLastModifiedBefore(expiredDate);
    }
}