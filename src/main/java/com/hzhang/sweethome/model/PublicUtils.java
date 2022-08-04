package com.hzhang.sweethome.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "public_utils")
public class PublicUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    String category;

    String description;

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public PublicUtils(){}

    public PublicUtils(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public PublicUtils(String category) {
        this.category = category;
    }
}
