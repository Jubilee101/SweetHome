package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.repository.MaintenanceManagerRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MaintenanceManagerService {
    private MaintenanceManagerRepository maintenanceManagerRepository;
    public List<MaintenanceReservation> findall() {
        List<MaintenanceReservation> reservations = maintenanceManagerRepository.findAll();
        if (!reservations.isEmpty()) {
            reservations.sort((MaintenanceReservation o1, MaintenanceReservation o2) -> {
                if (o1.getDate() == null && o2.getDate() == null) {
                    return 0;
                } else if (o1.getDate() == null) {
                    return -1;
                } else if (o2.getDate() == null) {
                    return 1;
                } else {
                    if (o1.getDate().equals(o2.getDate())) {
                        return -1 * o1.getStartTime().compareTo(o2.getStartTime());
                    } else {
                        return -1 * o1.getDate().compareTo(o2.getDate());
                    }
                }
            });
        }
        return reservations;

    }

    public void updateDate(LocalDate date,Long id){
        maintenanceManagerRepository.updateDate(date,id);
    }
    public void updateTime(LocalTime startTime,Long id){
        maintenanceManagerRepository.updateTime(startTime,id);
    }
    public  void updateTimeAndDate(LocalDate date,LocalTime startTime,Long id){
        updateDate(date,id);
        updateTime(startTime,id);
    }
}
