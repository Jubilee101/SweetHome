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
}
