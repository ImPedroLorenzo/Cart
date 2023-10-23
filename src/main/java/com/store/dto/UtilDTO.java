package com.store.dto;

import com.store.entity.Product;

public class UtilDTO {

    public static Product fromProductDTO(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setAmount(dto.getAmount());
        product.setId(dto.getId());
        return product;
    }

}