package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.ciRefItem = :ciRefItem AND c.buyerPrice.bplRefItem = :bplRefItem AND c.uRefItem = :uRefItem")
    Optional<Cart> findCartByCustomQuery(@Param("ciRefItem") Long ciRefItem, @Param("bplRefItem") Long bplRefItem, @Param("uRefItem") Long uRefItem);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.ciRefItem IN :ids")
    void deleteAllByCiRefItemIdIn(@Param("ids") List<Long> ids);
}