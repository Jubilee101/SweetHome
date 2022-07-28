package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "public_invoices")
@JsonDeserialize(builder = Public_invoices.Builder.class)
public class Public_invoices implements Serializable {
    private static final long serialVersionUID =1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private LocalDate date;

    public Public_invoices(){
    }

    private Public_invoices(Builder builder){
        this.id = builder.id;
        this.text = builder.text;
        this.date = builder.date;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public static class Builder{
        @JsonProperty("id")
        private Long id;
        @JsonProperty("text")
        private String text;
        @JsonProperty("date")
        private LocalDate date;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Public_invoices build(){
            return new Public_invoices(this);
        }
    }
}
