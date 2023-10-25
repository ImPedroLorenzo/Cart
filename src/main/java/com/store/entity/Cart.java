package com.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    //That are 10 minute in milliseconds
    public static final Long EXPIRATION_TIME = 600000L;

    public Cart(List<Product> products,Date lastModified) {
        this.products= products;
        this.lastModified= lastModified;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Override
    public String toString() {
        //Modified toString() to get most usable date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(lastModified);

        return String.format("Cart(id=%d, products=%s, lastModified=%s)", id, products, formattedDate);
    }
    public boolean isExpired() {
        return new Date().getTime() - getLastModified().getTime() > EXPIRATION_TIME;
    }
}
