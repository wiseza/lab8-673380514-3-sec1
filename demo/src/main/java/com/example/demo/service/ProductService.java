package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Review;
import com.example.demo.repository.ProductRepository;
import com.example.demo.strategy.DiscountContext;
import com.example.demo.strategy.DiscountStrategy;
import com.example.demo.strategy.MemberDiscountStrategy;
import com.example.demo.strategy.NoDiscountStrategy;
import com.example.demo.strategy.SeasonalSaleStrategy;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            product.setDiscountedPrice(
                    calculateDiscountedPrice(product)
            );
        }

        return products;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    private double calculateDiscountedPrice(Product product) {
        DiscountStrategy strategy;
        switch (product.getDiscountType()) {
            case "MEMBER":
                strategy = new MemberDiscountStrategy();
                break;

            case "SEASONAL":
                strategy = new SeasonalSaleStrategy();
                break;

            case "NONE":
            default:
                strategy = new NoDiscountStrategy();
                break;
        }
        DiscountContext context = new DiscountContext(strategy);
        return context.calculatePrice(product.getPrice());
    }

    public void saveProduct(Product product) {

        if (product.getDetail() != null) {
            product.getDetail().setProduct(product);
        }

        if (product.getReviews() != null) {
            for (Review review : product.getReviews()) {
                review.setProduct(product);
            }
        }

        product.setDiscountedPrice(
                calculateDiscountedPrice(product)
        );

        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {

        Product existingProduct = getProductById(id);

        if (existingProduct != null) {

            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setStock(product.getStock());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDiscountType(product.getDiscountType());

            if (product.getDetail() != null) {

                if (existingProduct.getDetail() == null) {
                    existingProduct.setDetail(product.getDetail());
                    product.getDetail().setProduct(existingProduct);

                } else {
                    ProductDetail detail = existingProduct.getDetail();

                    detail.setWarranty(
                            product.getDetail().getWarranty()
                    );

                    detail.setWeight(
                            product.getDetail().getWeight()
                    );

                    detail.setDimensions(
                            product.getDetail().getDimensions()
                    );

                    detail.setManufacturedCountry(
                            product.getDetail().getManufacturedCountry()
                    );
                }
            }

            existingProduct.setDiscountedPrice(
                    calculateDiscountedPrice(existingProduct)
            );

            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
