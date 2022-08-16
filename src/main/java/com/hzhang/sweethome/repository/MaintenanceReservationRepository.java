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
import java.util.Optional;

@Repository
public interface MaintenanceReservationRepository extends JpaRepository<MaintenanceReservation, Long>{

    List<MaintenanceReservation> findByUser(User user);

    Optional<MaintenanceReservation> findById(Long Id);

    List<MaintenanceReservation> findAll();
    @Transactional
    @Modifying
    @Query("update MaintenanceReservation maintenancereservation set maintenancereservation.date=?1 where maintenancereservation.id=?2")
    void updateDate(LocalDate date, Long id);
    @Transactional
    @Modifying
    @Query("update MaintenanceReservation maintenancereservation set maintenancereservation.startTime=?1 where maintenancereservation.id=?2")
    void updateTime(LocalTime starTime, Long id);
}
