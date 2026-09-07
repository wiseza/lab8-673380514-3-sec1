package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String warranty;
    private Double weight;
    private String dimensions;
    private String manufacturedCountry;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "detail")
    private Product product;

    public ProductDetail() {
    }

    public ProductDetail(Long id, String warranty, Double weight, String dimensions, String manufacturedCountry, String description) {
        this.id = id;
        this.warranty = warranty;
        this.weight = weight;
        this.dimensions = dimensions;
        this.manufacturedCountry = manufacturedCountry;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getManufacturedCountry() {
        return manufacturedCountry;
    }

    public void setManufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
