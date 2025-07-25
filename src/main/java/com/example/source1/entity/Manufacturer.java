package com.example.source1.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "manufacturers")

public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Integer manufacturer_id;

    @Column(name = "manufacturer_name", length = 100, unique = true, nullable = false)
    private String manufacturer_name;

    @Column(name = "country", length = 100)
    private String country;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Computer> computers;

    public Manufacturer(){

    }

    public Manufacturer(Integer manufacturer_id, String manufacturer_name, String country, List<Computer> computers) {
        this.manufacturer_id = manufacturer_id;
        this.manufacturer_name = manufacturer_name;
        this.country = country;
        this.computers = computers;
    }

    public Integer getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(Integer manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }
}