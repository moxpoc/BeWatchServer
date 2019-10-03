package com.bewatches.server.Model.App;

import com.bewatches.server.Model.Client;
import com.bewatches.server.Model.Parent.BeatHeart;
import com.bewatches.server.Model.Parent.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "watch")
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String imei;
    @NotBlank
    private String name;
    @Column(name = "bp_correction_high")
    private int bpCorrectionHigh;
    @Column(name = "bp_correction_low")
    private int bpCorrectionLow;
    @Column(name = "bp_threshold_high")
    private int bpThresholdHigh;
    @Column(name = "bp_threshold_low")
    private int bpThresholdLow;
    @Column(name = "device_mobile_no")
    private String deviceMobileNo;
    @Column(name = "falling_alarm")
    private String fallingAlarm;
    private int height;
    private int weight;
    @Column(name = "hrm_treshold_high")
    private int hrmTresholdHigh;
    @Column(name = "hrm_treshold_low")
    private int hrmTresholdLow;
    @Column(name = "owner_birthday")
    private String ownerBirthday;
    @Column(name = "owner_blood_type")
    private String ownerBloodType;
    @Column(name = "owner_gender")
    private String ownerGender;
    private String restricted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_id")
    private Blood blood;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "beatheart_id")
    private BeatHeart beatheart;

    public Watch(){

    }

    public Watch(String imei, String name,
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public BeatHeart getBeatHeart() {
        return beatheart;
    }

    public void setBeatHeart(BeatHeart beatHeart) {
        this.beatheart = beatHeart;
    }
}
