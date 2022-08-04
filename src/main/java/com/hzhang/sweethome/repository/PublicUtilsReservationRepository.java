package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.model.PublicUtilsReservation;
import com.hzhang.sweethome.model.TimeFrame;
import com.hzhang.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicUtilsReservationRepository extends JpaRepository<PublicUtilsReservation, Long> {
    List<PublicUtilsReservation> findByPublicUtilsAndDateBetween(PublicUtils publicUtils, LocalDate startDate, LocalDate endDate);

    List<PublicUtilsReservation> findByUser(User user);

    void deleteByPublicUtilsAndDateAndTimeFrame(PublicUtils publicUtils, LocalDate date, TimeFrame timeFrame);

}
