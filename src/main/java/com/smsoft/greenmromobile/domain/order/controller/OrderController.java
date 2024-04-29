package com.smsoft.greenmromobile.domain.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order/orderList")
    public String orderListPage() {
        return "order/orderList";
    }
}
