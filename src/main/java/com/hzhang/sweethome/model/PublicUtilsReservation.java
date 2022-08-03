package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "public_utils_reservation")
@JsonDeserialize(builder = MaintenanceReservation.Builder.class)
public class PublicUtilsReservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(value = "time_frame")
    private TimeFrame timeFrame;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "category")
    @JsonProperty(value = "public_utils")
    private PublicUtils publicUtils;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PublicUtilsReservation(){}

    private PublicUtilsReservation(Builder builder) {
        timeFrame = builder.timeFrame;
        date = builder.date;
        publicUtils = builder.publicUtils;
        user = builder.user;
    }

    public static class Builder {
        @JsonProperty(value = "id")
        private Long id;

        @JsonProperty(value = "time_frame")
        private TimeFrame timeFrame;

        @JsonProperty(value = "date")
        private LocalDate date;

        @JsonProperty(value = "public_utils")
        private PublicUtils publicUtils;

        @JsonProperty(value = "user")
        private User user;

        public Builder setTimeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setPublicUtils(PublicUtils publicUtils) {
            this.publicUtils = publicUtils;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public PublicUtilsReservation build() {
            return new PublicUtilsReservation(this);
        }
    }
}
