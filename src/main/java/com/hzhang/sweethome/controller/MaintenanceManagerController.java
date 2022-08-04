package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.service.MaintenanceManagerService;
import com.hzhang.sweethome.service.MaintenanceReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MaintenanceManagerController {
    private MaintenanceManagerService maintenanceManagerService;
    @Autowired
    public MaintenanceManagerController(MaintenanceManagerService maintenanceManagerService){
        this.maintenanceManagerService = maintenanceManagerService;
    }
    @GetMapping("/maintenance")
    public List<MaintenanceReservation> findall(){
        return maintenanceManagerService.findall();
    }
    @PutMapping("/maintenance")
    public void updateDateAndTime(@RequestParam("date") LocalDate date,
                                  @RequestParam("startTime") LocalTime startTime,
                                  @RequestParam("id") Long id){
        maintenanceManagerService.updateTimeAndDate(date,startTime,id);
    }







}
