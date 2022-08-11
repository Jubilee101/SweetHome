package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private LocalDate date;
    @JsonProperty(value = "name_and_room")
    private String nameAndRoom;
    private LocalTime time;

    public Message(){};
    private Message(Builder builder) {
        this.text= builder.text;
        this.nameAndRoom = builder.nameAndRoom;
        this.date=builder.Date;
        this.time = builder.time;
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

    public LocalTime getTime() {return time;}

    public String getNameAndRoom(){return nameAndRoom;}
    public static class Builder{
        @JsonProperty("id")
        private Long id;

        @JsonProperty("text")
        private String text;

        @JsonProperty("date")
        private LocalDate Date;

        @JsonProperty("name_and_room")
        private String nameAndRoom;
        @JsonIgnore
        private LocalTime time;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(LocalDate Date) {
            this.Date = Date;
            return this;
        }

        public Builder setTime(LocalTime time) {
            this.time = time;
            return this;
        }
        public Builder setNameAndRoom(String nameAndRoom) {
            this.nameAndRoom = nameAndRoom;
            return this;
        }


        public Message build() {
            return new Message(this);
        }
    }
}
