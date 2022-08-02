package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "maintenanceReservation_image")
public class MaintenanceImage implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    private String url;

    @ManyToOne
    @JoinColumn(name = "maintenanceReservation_id")
    @JsonIgnore
    private MaintenanceReservation maintenanceReservation;

    public MaintenanceImage(){}

    public MaintenanceImage(String url, MaintenanceReservation maintenanceReservation){
        this.url = url;
        this.maintenanceReservation = maintenanceReservation;
    }

    public String getUrl() {
        return url;
    }

    public MaintenanceImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public MaintenanceReservation getMaintenanceReservation() {
        return maintenanceReservation;
    }

    public MaintenanceImage setMaintenanceReservation(MaintenanceReservation maintenanceReservation) {
        this.maintenanceReservation = maintenanceReservation;
        return this;
    }
}
