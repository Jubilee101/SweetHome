package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface MaintenanceReservationRepository extends JpaRepository<MaintenanceReservation, Long>{

    List<MaintenanceReservation> findByUser(User user);

}
