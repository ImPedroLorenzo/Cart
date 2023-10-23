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
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public Cart createCart() {
        return cartRepository.save(new Cart(new ArrayList<>(),new Date()));
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
    public Cart addProductsToCart(Long cartId, List<Product> productsToAdd) {
        Optional<Cart> optCart = cartRepository.findById(cartId);

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




}