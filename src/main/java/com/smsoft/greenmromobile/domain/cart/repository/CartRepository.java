package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.ciRefItem = :ciRefItem AND c.buyerPrice.bplRefItem = :bplRefItem AND c.uRefItem = :uRefItem")
    Optional<Cart> findCartByCustomQuery(Long ciRefItem, Long bplRefItem, Long uRefItem);
}
