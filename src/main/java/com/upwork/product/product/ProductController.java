package com.upwork.product.product;

import com.upwork.product.category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/create", name = "create-product")
    public ModelAndView create() {
        List<ProductCategory> productCategories = productService.getProductCategories();
        ModelAndView modelAndView = new ModelAndView("product/create.html");
        modelAndView.addObject("productCategories", productCategories);
        modelAndView.addObject("title", "Create Product");
        return modelAndView;
    }

    @PostMapping(value = "/", name = "store-product")
    public ModelAndView store(@Valid @ModelAttribute() CreateProductRequest request,
                              BindingResult result,
                              ModelMap modelMap){
        System.out.println("Store product");
        System.out.println(result.hasErrors());
        System.out.println(request.getProductCategory());
        System.out.println(request.getName());
        if(result.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("product/create.html");
            return modelAndView;
        }

        this.productService.save(request);
        modelMap.addAttribute("message", "Product added successfully.");
        return new ModelAndView("redirect:/product/create", modelMap);

    }
}
