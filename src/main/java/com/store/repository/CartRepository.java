package com.store.repository;

import com.store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Modifying
    @Transactional
    void deleteByLastModifiedBefore(Date date);
}
