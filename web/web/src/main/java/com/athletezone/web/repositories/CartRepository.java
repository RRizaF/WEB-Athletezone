package com.athletezone.web.repositories;

import com.athletezone.web.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    Object save(Cart newCart);
}
