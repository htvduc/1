package com.example.source1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "computers")

public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_id")
    private Integer computer_id;

    @Column(name = "computer_model", length = 100, nullable = false)
    private String computer_model;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "production_year", nullable = false)
    private Integer production_year;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    public Computer(){

    }
    public Computer(Integer computer_id, String computer_model, String type, Integer production_year, BigDecimal price, Manufacturer manufacturer) {
        this.computer_id = computer_id;
        this.computer_model = computer_model;
        this.type = type;
        this.production_year = production_year;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Integer getComputer_id() {
        return computer_id;
    }

    public void setComputer_id(Integer computer_id) {
        this.computer_id = computer_id;
    }

    public String getComputer_model() {
        return computer_model;
    }

    public void setComputer_model(String computer_model) {
        this.computer_model = computer_model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProduction_year() {
        return production_year;
    }

    public void setProduction_year(Integer production_year) {
        this.production_year = production_year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}