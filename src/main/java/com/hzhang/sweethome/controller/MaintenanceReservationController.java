package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.service.MaintenanceReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class MaintenanceReservationController {
    private MaintenanceReservationService maintenanceReservationService;

    @Autowired
    public MaintenanceReservationController(MaintenanceReservationService maintenanceReservationService){
        this.maintenanceReservationService = maintenanceReservationService;
    }

    // list all reservation (sorted by time)
    @GetMapping("/maintenance")
    public List<MaintenanceReservation> listMaintenanceReservation(Principal principal){
        return maintenanceReservationService.listAll();
    }

    // find the reservation by the room number of the resident
    @GetMapping("/maintenance/*")
    public MaintenanceReservation getMaintenanceReservation(
            @RequestParam(name = "room") String room,
            @RequestParam(name = "user") String name){
        return maintenanceReservationService.findByRoomAndName(room, name);
    }
    @PostMapping("/maintenance")
    public void addMaintenanceReservation(
            @RequestParam("room") String room,
            @RequestParam("resident") String resident,
            @RequestParam("description") String description,
            @RequestParam("date") LocalDate date,
            @RequestParam("start_time") LocalTime startTime,
            @RequestParam("images") MultipartFile[] images){
        MaintenanceReservation maintenanceReservation = new MaintenanceReservation.Builder()
                .setDescription(description)
                .setRoom(room)
                .setResident(new User.Builder().setName(resident).build())
                .setDate(date)
                .setStartTime(startTime)
                .build();
        maintenanceReservationService.add(maintenanceReservation, images);
    }
}
