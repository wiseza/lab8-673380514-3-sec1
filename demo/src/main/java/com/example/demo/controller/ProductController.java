package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {

        model.addAttribute(
            "products",
            productService.getAllProducts()
        );

        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {

        model.addAttribute("product", new Product());

        return "products/add";
    }

    @PostMapping("/save")
    public String saveProduct(
            @ModelAttribute Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @ModelAttribute Product product) {

        productService.updateProduct(id, product);

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/products";
    }
}