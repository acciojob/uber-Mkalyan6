package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Driver{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;
    private String mobile;
    private String password;

     @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
     private List<TripBooking> tripBookingList=new ArrayList<>();
     @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
     @JoinColumn
    private Cab cab;
    public Driver(Integer driverId, String mobile, String password) {
        this.driverId = driverId;
        this.mobile = mobile;
        this.password = password;
    }

    public Driver() {
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}