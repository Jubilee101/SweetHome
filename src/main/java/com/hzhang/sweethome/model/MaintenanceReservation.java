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

    @JsonProperty("room")
    private String room;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private User resident;

    @OneToMany(mappedBy = "maintenanceReservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MaintenanceImage> maintenanceImages;

    public MaintenanceReservation(){};

    private MaintenanceReservation(Builder builder){
        this.id = builder.id;
        this.room = builder.room;
        this.startTime = builder.startTime;
        this.date = builder.date;
        this.description = builder.description;
        this.resident = builder.resident;
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

    public String getRoom() { return room; }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public User getResident() {
        return resident;
    }
    public MaintenanceReservation setResident(User resident){
        this.resident = resident;
        return this;
    }



    public static class Builder{
        @JsonProperty("id")
        private Long id;
        @JsonProperty("room")
        private String room;

        @JsonProperty("start_time")
        private LocalTime startTime;

        @JsonProperty("date")
        private LocalDate date;

        @JsonProperty("description")
        private String description;

        @JsonProperty("resident")
        private User resident;

        @JsonProperty("maintenanceImages")
        private List<MaintenanceImage> maintenanceImages;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }


        public Builder setRoom(String room) {
            this.room = room;
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

        public Builder setResident(User resident){
            this.resident = resident;
            return this;
        }

        public MaintenanceReservation build(){
            return new MaintenanceReservation(this);
        }
    }
}
