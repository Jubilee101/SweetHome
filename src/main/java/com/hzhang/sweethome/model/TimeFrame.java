package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TimeFrame {
    MORNING ("8:00 -- 12:00 AM"),
    AFTERNOON ("12:00 -- 6:00 PM"),
    EVENING ("6:00 -- 11:50 PM");

    private String time;
    private TimeFrame(String time) {
        this.time = time;
    }

    @JsonValue
    public String getTime() {
        return time;
    }
}
