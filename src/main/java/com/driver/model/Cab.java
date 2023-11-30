package com.driver.model;

import javax.persistence.*;

public class Cab{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int perKmRate;
    private boolean available;

    @OneToOne()
    @JoinColumn
    private Driver driver;

    public Cab(Integer id, int perKmRate, boolean available) {
        this.id = id;
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Cab() {
    }

    public Integer getId() {
        return id;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean getAvailable(){
        return available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}