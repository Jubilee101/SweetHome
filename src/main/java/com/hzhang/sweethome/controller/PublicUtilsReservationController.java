package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.PublicUtilsReservation;
import com.hzhang.sweethome.model.TimeFrame;
import com.hzhang.sweethome.model.TimeSlot;
import com.hzhang.sweethome.service.PublicUtilsReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class PublicUtilsReservationController {
    private PublicUtilsReservationService publicUtilsReservationService;

    @Autowired
    public PublicUtilsReservationController(PublicUtilsReservationService publicUtilsReservationService){
        this.publicUtilsReservationService = publicUtilsReservationService;
    }

    @GetMapping("/public_utils/available")
    public List<TimeSlot> getAvailableTimeSlots(@RequestParam(name = "category") String category) {
        return this.publicUtilsReservationService.getAvailableTimeSlots(category);
    }

    @GetMapping("/public_utils/list")
    public List<PublicUtilsReservation> getAllReservation(Principal principal) {
        return this.publicUtilsReservationService.getPublicUtilsByUser(principal.getName());
    }

    @PostMapping("/public_utils/reserve")
    public void reservePublicUtils(@RequestParam(name = "category") String category,
                                   @RequestParam(name = "date") String date,
                                   @RequestParam(name = "time_frame")String timeFrame,
                                   Principal principal) {
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        publicUtilsReservationService.add(category, startDate, timeFrame, principal.getName());
    }

    @DeleteMapping("/public_utils")
    public void cancelReservation(@RequestParam(name = "category") String category,
                                  @RequestParam(name = "date") String date,
                                  @RequestParam(name = "time_frame") String time,
                                  Principal principal) {
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        publicUtilsReservationService.cancel(startDate, time, category, principal.getName());
    }

}
