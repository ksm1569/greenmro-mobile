package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
