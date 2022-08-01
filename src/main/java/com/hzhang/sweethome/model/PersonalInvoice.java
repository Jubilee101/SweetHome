package com.hzhang.sweethome.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;

@Entity
@Table(name = "personalinvoice")
@JsonDeserialize(builder = PersonalInvoice.Builder.class)
public class PersonalInvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private String text;
    private LocalDate date;
    @JsonIgnore
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    public PersonalInvoice() {}

    private PersonalInvoice(Builder builder) {
        this.type=builder.type;
        this.text= builder.text;
        this.date=builder.Date;
        this.user=builder.user;
        this.time = builder.time;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public LocalTime getTime() {return time;}
    public static class Builder{

        @JsonProperty("id")
        private Long id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("date")
        private LocalDate Date;

        @JsonProperty("user")
        private User user;

        @JsonIgnore
        private LocalTime time;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(LocalDate Date) {
            this.Date = Date;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTime(LocalTime time) {
            this.time = time;
            return this;
        }


        public PersonalInvoice build() {
            return new PersonalInvoice(this);
        }
    }

}

