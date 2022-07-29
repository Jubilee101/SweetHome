package com.hzhang.sweethome.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    private LocalDate Date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public PersonalInvoice() {}

    private PersonalInvoice(Builder builder) {
        this.id=builder.id;
        this.type=builder.type;
        this.text= builder.text;
        this.Date=builder.Date;
        this.user=builder.user;
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
        return Date;
    }

    public User getUser() {
        return user;
    }
    public static class Builder{

        @JsonProperty("id")
        private Long id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("timestamp")
        private LocalDate Date;

        @JsonProperty("user")
        private User user;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTimestamp(LocalDate Date) {
            this.Date = Date;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }


        public PersonalInvoice build() {
            return new PersonalInvoice(this);
        }
    }

}

