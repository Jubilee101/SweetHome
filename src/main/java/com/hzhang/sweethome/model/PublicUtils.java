package com.hzhang.sweethome.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "public_utils")
public class PublicUtils implements Serializable {
    @Id
    String category;

    String description;

    public PublicUtils(){}

    public PublicUtils(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public PublicUtils(String category) {
        this.category = category;
    }
}
