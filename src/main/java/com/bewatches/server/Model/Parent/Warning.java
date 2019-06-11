package com.bewatches.server.Model.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "warning")
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String warningType;
    @NotNull
    private int battery;
    @NotBlank
    private String address;
    @NotBlank
    private String longitude;
    @NotBlank
    private String latitude;

    public Warning(){

    }

    public Warning(String warningType, int battery, String address, String longitude, String latitude){
        this.warningType = warningType;
        this.battery = battery;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
