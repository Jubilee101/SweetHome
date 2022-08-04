package com.hzhang.sweethome.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TimeSlot implements Serializable {
    @JsonProperty(value = "time_frame")
    TimeFrame timeFrame;
    @JsonProperty(value = "date")
    LocalDate date;

    public TimeSlot(TimeFrame timeFrame, LocalDate date){
        this.date = date;
        this.timeFrame = timeFrame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return timeFrame == timeSlot.timeFrame && date.equals(timeSlot.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeFrame, date);
    }
}
