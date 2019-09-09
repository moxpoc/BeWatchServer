package com.bewatches.server.Model.App;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Watches {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    @NotBlank
    private String imei;
    @NotBlank
    private String name;
    @NotNull
    private int bpCorrectionHigh;
    @NotNull
    private int bpCorrectionLow;
    @NotNull
    private int bpThresholdHigh;
    @NotNull
    private int bpThresholdLow;
    @NotBlank
    private String deviceMobileNo;
    @NotBlank
    private String fallingAlarm;
    @NotNull
    private int height;
    @NotNull
    private int weight;
    @NotNull
    private int hrmTresholdHigh;
    @NotNull
    private int hrmTresholdLow;
    @NotBlank
    private String ownerBirthday;
    @NotBlank
    private String ownerBloodType;
    @NotBlank
    private String ownerGender;
    @NotBlank
    private String restricted;

    public Watches(){

    }

    public Watches(String imei, String name,
                   int bpCorrectionHigh, int bpCorrectionLow,
                   int bpThresholdHigh, int bpThresholdLow,
                   String deviceMobileNo, String fallingAlarm,
                   int height, int weight,
                   int hrmTresholdHigh, int hrmTresholdLow,
                   String ownerBirthday, String ownerBloodType,
                   String ownerGender, String restricted){
        this.imei = imei;
        this.name = name;
        this.bpCorrectionHigh = bpCorrectionHigh;
        this.bpCorrectionLow = bpCorrectionLow;
        this.bpThresholdHigh = bpThresholdHigh;
        this.bpThresholdLow = bpThresholdLow;
        this.deviceMobileNo = deviceMobileNo;
        this.fallingAlarm = fallingAlarm;
        this.height = height;
        this.weight = weight;
        this.hrmTresholdHigh = hrmTresholdHigh;
        this.hrmTresholdLow = hrmTresholdLow;
        this.ownerBirthday = ownerBirthday;
        this.ownerBloodType = ownerBloodType;
        this.ownerGender = ownerGender;
        this.restricted = restricted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBpCorrectionHigh() {
        return bpCorrectionHigh;
    }

    public void setBpCorrectionHigh(int bpCorrectionHigh) {
        this.bpCorrectionHigh = bpCorrectionHigh;
    }

    public int getBpCorrectionLow() {
        return bpCorrectionLow;
    }

    public void setBpCorrectionLow(int bpCorrectionLow) {
        this.bpCorrectionLow = bpCorrectionLow;
    }

    public int getBpThresholdHigh() {
        return bpThresholdHigh;
    }

    public void setBpThresholdHigh(int bpThresholdHigh) {
        this.bpThresholdHigh = bpThresholdHigh;
    }

    public int getBpThresholdLow() {
        return bpThresholdLow;
    }

    public void setBpThresholdLow(int bpThresholdLow) {
        this.bpThresholdLow = bpThresholdLow;
    }

    public String getDeviceMobileNo() {
        return deviceMobileNo;
    }

    public void setDeviceMobileNo(String deviceMobileNo) {
        this.deviceMobileNo = deviceMobileNo;
    }

    public String getFallingAlarm() {
        return fallingAlarm;
    }

    public void setFallingAlarm(String fallingAlarm) {
        this.fallingAlarm = fallingAlarm;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHrmTresholdHigh() {
        return hrmTresholdHigh;
    }

    public void setHrmTresholdHigh(int hrmTresholdHigh) {
        this.hrmTresholdHigh = hrmTresholdHigh;
    }

    public int getHrmTresholdLow() {
        return hrmTresholdLow;
    }

    public void setHrmTresholdLow(int hrmTresholdLow) {
        this.hrmTresholdLow = hrmTresholdLow;
    }

    public String getOwnerBirthday() {
        return ownerBirthday;
    }

    public void setOwnerBirthday(String ownerBirthday) {
        this.ownerBirthday = ownerBirthday;
    }

    public String getOwnerBloodType() {
        return ownerBloodType;
    }

    public void setOwnerBloodType(String ownerBloodType) {
        this.ownerBloodType = ownerBloodType;
    }

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }
}
