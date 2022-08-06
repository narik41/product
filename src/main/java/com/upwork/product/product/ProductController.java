package com.upwork.product.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/product")
@Controller
public class ProductController {

    @GetMapping(value = "/create", name = "create-product")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("product/create.html");
        return modelAndView;
    }
}
