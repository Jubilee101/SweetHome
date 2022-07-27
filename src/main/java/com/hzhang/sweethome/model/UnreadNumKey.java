package com.hzhang.sweethome.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UnreadNumKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    private String type;

    public UnreadNumKey() {}

    public UnreadNumKey setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public String getType() {
        return type;
    }

    public UnreadNumKey setType(String type) {
        this.type = type;
        return this;
    }

    public UnreadNumKey setType(InvoiceType type) {
        this.type = type.name();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnreadNumKey that = (UnreadNumKey) o;
        return email.equals(that.getEmail()) && type.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, type);
    }
}
