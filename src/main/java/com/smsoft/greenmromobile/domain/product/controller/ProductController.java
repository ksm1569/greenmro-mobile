package com.smsoft.greenmromobile.domain.product.controller;

import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final JwtUtil jwtUtil;

    @GetMapping("/product/by-category")
    public String productCategoryList(
            @RequestParam(required = false, defaultValue = "999") Integer flag,
            @RequestParam(required = true) Long crefItem,
            @RequestParam(required = true) String categoryName,
            @CookieValue(name = "accessToken", required = false) String token,
            Model model
    ) {
        model.addAttribute("flag", flag);
        model.addAttribute("crefItem", crefItem);
        model.addAttribute("categoryName", categoryName);

        if(categoryName.equals("고객사등록상품") || categoryName.equals("고객사미등록상품")) {
            model.addAttribute("ucompanyRef", jwtUtil.getUcompanyRefFromToken(token));
        }
        return "product/productCategoryList";
    }

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
        return "product/productSearchList";
    }
}
