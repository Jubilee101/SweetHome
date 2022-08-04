package com.hzhang.sweethome.repository;
import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MaintenanceManagerRepository {
    List<MaintenanceReservation> findAll();
    @Transactional
    @Modifying
    @Query("update MaintenanceReservation maintenancereservation set maintenancereservation.date=?1 where maintenancereservation.id=?2")
    MaintenanceReservation updateDate(LocalDate date,Long id);
    @Transactional
    @Modifying
    @Query("update MaintenanceReservation maintenancereservation set maintenancereservation.startTime=?1 where maintenancereservation.id=?2")
    MaintenanceReservation updateTime(LocalTime starTime,Long id);
}
