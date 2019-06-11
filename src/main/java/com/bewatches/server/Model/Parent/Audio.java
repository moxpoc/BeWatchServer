package com.bewatches.server.Model.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "audio")
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String imei;
    @NotBlank
    private String audio;

    public Audio(){

    }

    public Audio(String imei, String audio){
        this.imei = imei;
        this.audio = audio;
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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
