package com.upwork.product.product;

import com.upwork.product.category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private static final String TITLE = "title";
    private static final String SUCCESS_MESSAGE = "success_message";
    private static final String ERROR_MESSAGE = "error_message";
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product", name = "index-product")
    public ModelAndView index(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue ="10") Integer size,
                              @RequestParam(defaultValue = "") String searchText,
                              ModelAndView model) {

        Page<Product> result = productService.getAll(page - 1, size, searchText);
        Integer startIndex = (page - 1) * size + 1 ;

        model.setViewName("product/index.html");
        model.addObject("products", result.getContent());
        model.addObject(TITLE, "Product List");
        model.addObject("totalPage", result.getTotalPages());
        model.addObject("startIndex", startIndex);
        model.addObject("currentPage", page);
        return model;
    }

    @GetMapping(value = "/product/create", name = "create-product")
    public ModelAndView create(CreateProductRequest productRequest, ModelAndView model) {
        List<ProductCategory> productCategories = productService.getProductCategories();
        model.setViewName("product/create.html");
        model.addObject("productCategories", productCategories);
        model.addObject("product", productRequest);
        model.addObject("brands", productService.getBrands());
        model.addObject("countries", productService.getCountries());
        model.addObject(TITLE, "Create Product");
        return model;
    }

    @PostMapping(value = "/product", name = "store-product")
    public ModelAndView store(@Valid @ModelAttribute("product") CreateProductRequest product,
                              BindingResult result,
                              RedirectAttributes ra) {

        if (result.hasErrors()) {
            List<ProductCategory> productCategories = productService.getProductCategories();
            ModelAndView modelAndView = new ModelAndView("product/create.html");
            modelAndView.addObject("productCategories", productCategories);
            modelAndView.addObject("product", product);
            modelAndView.addObject("brands", productService.getBrands());
            modelAndView.addObject("countries", productService.getCountries());
            modelAndView.addObject(TITLE, "Create Product");
            return modelAndView;
        }


        this.productService.save(product);
        ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_CREATE_PRODUCT);
        return new ModelAndView("redirect:/product");
    }

    @GetMapping(value = "/product/{id}", name = "view-product")
    public ModelAndView view(@PathVariable("id") String productId) {
        Product byId = productService.getById(productId);

        ModelAndView modelAndView = new ModelAndView("product/view.html");
        modelAndView.addObject("product", byId);
        modelAndView.addObject(TITLE, "Product Details");
        return modelAndView;
    }

    @GetMapping(value = "/product/{id}/edit", name = "edit-product")
    public ModelAndView edit(@PathVariable("id") String productId,  ModelAndView model ) {

        model.setViewName("product/edit.html");
        model.addObject("productCategories", productService.getProductCategories());
        model.addObject("brands", productService.getBrands());
        model.addObject("countries", productService.getCountries());
        model.addObject("product", productService.getProductForEdit(productId));
        model.addObject("id", productId);
        model.addObject(TITLE, "Update Product");
        return model;
    }

    @PutMapping(value = "/product/{id}/update", name = "update-product")
    public ModelAndView update(@PathVariable("id") String productId,
                               @Valid @ModelAttribute("product") CreateProductRequest product,
                               BindingResult result,
                               RedirectAttributes ra) {

        if (result.hasErrors()) {
            List<ProductCategory> productCategories = productService.getProductCategories();
            ModelAndView modelAndView = new ModelAndView("product/edit.html");
            modelAndView.addObject("productCategories", productCategories);
            modelAndView.addObject("brands", productService.getBrands());
            modelAndView.addObject("countries", productService.getCountries());
            modelAndView.addObject("product", product);
            modelAndView.addObject("id", productId);
            modelAndView.addObject(TITLE, "Update Product");
            return modelAndView;
        }

        this.productService.update(productId, product);
        ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_UPDATE_PRODUCT);
        return new ModelAndView("redirect:/product");
    }

    @DeleteMapping(value = "/product/{id}/delete", name = "delete-product")
    public ModelAndView delete(@PathVariable("id") String productId, RedirectAttributes ra) {
        boolean delete = productService.delete(productId);
        if(delete){
            ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_DELETED_PRODUCT);
        }else{
            ra.addFlashAttribute(ERROR_MESSAGE, Messages.FAIL_PRODUCT_DELETE);
        }

        return new ModelAndView("redirect:/product");
    }
}
