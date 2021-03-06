package com.bewatches.server.Model.Parent;

import com.bewatches.server.Model.App.Watch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "beatheart")//пульс
public class BeatHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String imei;

    private int battery;

    private int pedometer;

    @JsonIgnore
    @OneToOne(optional = false, mappedBy = "beatheart")
    private Watch watch;

    public BeatHeart(){

    }
    public BeatHeart(String imei, int battery, int pedometer){
        this.imei = imei;
        this.battery = battery;
        this.pedometer = pedometer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getPedometer() {
        return pedometer;
    }

    public void setPedometer(int pedometer) {
        this.pedometer = pedometer;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }
}
