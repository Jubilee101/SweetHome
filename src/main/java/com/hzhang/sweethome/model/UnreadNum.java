package com.hzhang.sweethome.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "unread_num")
public class UnreadNum implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserInvoiceTypeKey id;

    private int num;

    public UnreadNum() {}

    public UnreadNum(UserInvoiceTypeKey id, int num) {
        this.id = id;
        this.num = num;
    }

    public UserInvoiceTypeKey getId() {return id;}

    public int getNum() {return num;}
}
