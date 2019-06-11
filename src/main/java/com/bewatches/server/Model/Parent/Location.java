package com.bewatches.server.Model.Parent;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String imei;
    @NotBlank
    private String type;
    @NotBlank
    private String lat;
    @NotBlank
    private String lon;

    public Location(){

    }

    public Location(String imei, String type, String lat, String lon){
        this.imei = imei;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
