package com.hzhang.sweethome.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String email;
    private String authority;

    public Authority() {}

    public Authority(String email, String authority) {
        this.email = email;
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public Authority setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public Authority setAuthority(String authority) {
        this.authority = authority;
        return this;
    }
}
