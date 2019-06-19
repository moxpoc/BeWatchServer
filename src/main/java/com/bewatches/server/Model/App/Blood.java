package com.bewatches.server.Model.App;

import javax.persistence.*;

@Entity
@Table(name = "blood")
public class Blood {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private String imei;

     private int dbp;

     private int sbp;

     @OneToOne(mappedBy = "blood")
     private Watch watch;


     public Blood(){

     }

     public Blood(String imei, int dbp, int sbp){
         this.imei = imei;
         this.dbp = dbp;
         this.sbp =sbp;
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

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }
}
