package com.store.controller;

import com.store.dto.CartDTO;
import com.store.entity.Cart;
import com.store.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/insert-data")
    public ResponseEntity<List<CartDTO>> insertData() {
        List<Cart> carts = dataService.insertData();
        List<CartDTO> cartDTOs = carts.stream()
                .map(cart -> new CartDTO(cart.getId(), cart.getProducts(), cart.getLastModified()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartDTOs);
    }

}