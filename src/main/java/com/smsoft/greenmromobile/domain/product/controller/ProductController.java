package com.smsoft.greenmromobile.domain.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @GetMapping("/product/detail/{prefItem}")
    public String productDetailPage(@PathVariable Long prefItem, Model model) {
        model.addAttribute("prefItem", prefItem);
        return "product/productDetail";
    }

    @GetMapping("/product/search")
    public String productSearch() {
        return "product/productSearch";
    }
}
