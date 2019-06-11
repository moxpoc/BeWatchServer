package com.bewatches.server.Model.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "health")
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String imei;
    @NotNull
    private int heartRate;
    @NotNull
    private int dbp;
    @NotNull
    private int sdp;
    @NotNull
    private int oxygen;

    public Health(){

    }
    public Health(String imei, int heartRate, int dbp, int sdp, int oxygen){
        this.imei = imei;
        this.heartRate = heartRate;
        this.dbp = dbp;
        this.sdp = sdp;
        this.oxygen = oxygen;
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

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSdp() {
        return sdp;
    }

    public void setSdp(int sdp) {
        this.sdp = sdp;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }
}
