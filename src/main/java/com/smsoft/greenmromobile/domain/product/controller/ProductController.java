package com.smsoft.greenmromobile.domain.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/product/searchList")
    public String productSearchList(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String prefItem,
            Model model
    ) {
        model.addAttribute("productName", productName);
        model.addAttribute("prefItem", prefItem);
        System.out.println("productName = " + productName);
        System.out.println("prefItem = " + prefItem);
        return "product/productSearchList";
    }
}
