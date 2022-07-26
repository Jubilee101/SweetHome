package com.hzhang.sweethome.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserInvoiceTypeKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    private String type;

    public UserInvoiceTypeKey() {}

    public UserInvoiceTypeKey setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public String getType() {
        return type;
    }

    public UserInvoiceTypeKey setType(String type) {
        this.type = type;
        return this;
    }

    public UserInvoiceTypeKey setType(InvoiceType type) {
        this.type = type.name();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInvoiceTypeKey that = (UserInvoiceTypeKey) o;
        return email.equals(that.getEmail()) && type.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, type);
    }
}
