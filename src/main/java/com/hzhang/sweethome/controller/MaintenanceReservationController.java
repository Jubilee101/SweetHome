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
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class MaintenanceReservationController {
    private MaintenanceReservationService maintenanceReservationService;

    @Autowired
    public MaintenanceReservationController(MaintenanceReservationService maintenanceReservationService){
        this.maintenanceReservationService = maintenanceReservationService;
    }

    // find the reservation by the email of the resident
    @GetMapping("/maintenance/resident")
    public List<MaintenanceReservation> getMaintenanceReservation(Principal principal){
        return maintenanceReservationService.findByUser(principal.getName());
    }
    @PostMapping("/maintenance")
    public void addMaintenanceReservation(
            @RequestParam("description") String description,
            @RequestParam("images") MultipartFile[] images,
            Principal principal){
        maintenanceReservationService.add(principal.getName(), description, images);
    }
    @GetMapping("/maintenance")
    public List<MaintenanceReservation> findall(){
        return maintenanceReservationService.findall();
    }
    @PutMapping("/maintenance")
    public void updateDateAndTime(@RequestParam("date") String date,
                                  @RequestParam("time") String time,
                                  @RequestParam("id") Long id){
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime startTime = LocalTime.parse(time,DateTimeFormatter.ofPattern("HH:mm:ss"));

        maintenanceReservationService.updateTimeAndDate(startDate,startTime,id);
    }

}
