package com.store.dto;

import com.store.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private List<Product> products;
    private Date lastModified;
}