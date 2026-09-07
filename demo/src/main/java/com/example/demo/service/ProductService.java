package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product) {

        if (product.getDetail() != null) {
            product.getDetail().setProduct(product);
        }

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

            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}