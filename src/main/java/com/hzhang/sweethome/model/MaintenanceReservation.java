package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "maintenance_reservation")
@JsonDeserialize(builder = MaintenanceReservation.Builder.class)
public class MaintenanceReservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "maintenanceReservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MaintenanceImage> maintenanceImages;

    public MaintenanceReservation(){};

    private MaintenanceReservation(Builder builder){
        this.startTime = builder.startTime;
        this.date = builder.date;
        this.description = builder.description;
        this.user = builder.user;
        this.maintenanceImages = builder.maintenanceImages;
    }

    public List<MaintenanceImage> getMaintenanceImages() {
        return maintenanceImages;
    }

    public MaintenanceReservation setMaintenanceImages(List<MaintenanceImage> maintenanceImages) {
        this.maintenanceImages = maintenanceImages;
        return this;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public User getUser() {
        return user;
    }
    public MaintenanceReservation setUser(User resident){
        this.user = resident;
        return this;
    }



    public static class Builder{
        @JsonProperty("id")
        private Long id;

        @JsonProperty("start_time")
        private LocalTime startTime;

        @JsonProperty("date")
        private LocalDate date;

        @JsonProperty("description")
        private String description;

        @JsonProperty("user_id")
        private User user;

        @JsonProperty("maintenanceImages")
        private List<MaintenanceImage> maintenanceImages;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }


        public Builder setStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }


        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }


        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setUser(User user){
            this.user = user;
            return this;
        }

        public MaintenanceReservation build(){
            return new MaintenanceReservation(this);
        }
    }
}
