package com.hzhang.sweethome.exception;

import org.springframework.context.annotation.Bean;

public class GCSUploadException extends RuntimeException{
    public GCSUploadException(String message) {
        super(message);
    }
}
