package com.smsoft.greenmromobile.domain.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/cart/cartList")
    public String orderListPage() {
        return "cart/cartList";
    }
}
