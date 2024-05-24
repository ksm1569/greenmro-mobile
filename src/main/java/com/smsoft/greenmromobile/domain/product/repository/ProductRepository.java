package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
