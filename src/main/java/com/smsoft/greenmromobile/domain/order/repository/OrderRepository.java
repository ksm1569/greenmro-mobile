package com.smsoft.greenmromobile.domain.order.repository;

import com.smsoft.greenmromobile.domain.order.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderItems, Long> {
}
