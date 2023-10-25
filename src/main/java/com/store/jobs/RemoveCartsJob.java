package com.store.jobs;

import com.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveCartsJob {

    @Autowired
    private CartService cartService;

    //This scheduled task will remove old carts every minute
    @Scheduled(fixedRate = 60000)
    public void removeExpiredCarts() {
        cartService.removeExpiredCarts();
    }
}
