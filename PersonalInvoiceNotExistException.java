package com.hzhang.sweethome.exception;

public class PersonalInvoiceNotExistException extends RuntimeException  {
    public PersonalInvoiceNotExistException(String message) {
        super(message);
    }
}
