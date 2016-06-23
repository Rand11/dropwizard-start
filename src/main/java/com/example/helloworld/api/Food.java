package com.example.helloworld.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * Created by adrianz on 21/06/16.
 */

@Entity
public class Food {

    @Id @GeneratedValue
    private long id;
    @Length(min = 2)
    private String name;
    @Min(value = 0)
    private double quantity;
    @Min(value = 0)
    private int price;


    public Food(String name, double quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Food() {
        //deserialization
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public double getQuantity() {
        return quantity;
    }

    @JsonProperty
    public int getPrice() {
        return price;
    }
}
