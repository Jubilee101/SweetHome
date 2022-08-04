package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.PublicUtilsReservationAlreadyExistsException;
import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.model.PublicUtilsReservation;
import com.hzhang.sweethome.model.TimeFrame;
import com.hzhang.sweethome.model.TimeSlot;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.PublicUtilsReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicUtilsReservationService {
    private PublicUtilsReservationRepository publicUtilsReservationRepository;

    @Autowired
    PublicUtilsReservationService(PublicUtilsReservationRepository publicUtilsReservationRepository) {
        this.publicUtilsReservationRepository = publicUtilsReservationRepository;
    }

    public List<TimeSlot> getAvailableTimeSlots(String category) {
        List<PublicUtilsReservation> publicUtilsReservations =
                publicUtilsReservationRepository
                        .findByPublicUtilsAndDateBetween(new PublicUtils(category)
                                , LocalDate.now()
                                , LocalDate.now().plusDays(6)
                        );
        Set<TimeSlot> used = new HashSet<>();
        for (PublicUtilsReservation reservation : publicUtilsReservations) {
            used.add(new TimeSlot(reservation.getTimeFrame(), reservation.getDate()));
        }
        List<TimeSlot> slots = new ArrayList<>();
        for (LocalDate date = LocalDate.now().plusDays(1);
             date.isBefore(LocalDate.now().plusDays(7));
             date = date.plusDays(1)) {
            for (TimeFrame timeFrame : TimeFrame.values()) {
                TimeSlot slot = new TimeSlot(timeFrame, date);
                if (used.contains(slot)){
                    continue;
                }
                slots.add(slot);
            }
        }
        return slots;
    }

    public List<PublicUtilsReservation> getPublicUtilsByUser(String email) {
        return publicUtilsReservationRepository.findByUser(
                new User.Builder().setEmail(email).build());
    }

    public void add(String category, LocalDate date, TimeFrame timeFrame, String email) {
        Optional<PublicUtilsReservation> reserve = publicUtilsReservationRepository
                .findPublicUtilsReservationByPublicUtilsAndDateAndTimeFrame(
                        new PublicUtils(category), date, timeFrame);
        if (reserve.isPresent()) {
            throw new PublicUtilsReservationAlreadyExistsException("Whoops~~ Someone else might have booked this time slots!");
        }
        PublicUtilsReservation reservation = new PublicUtilsReservation.Builder()
                .setDate(date)
                .setTimeFrame(timeFrame)
                .setPublicUtils(new PublicUtils(category))
                .setUser(new User.Builder().setEmail(email).build())
                .build();
        publicUtilsReservationRepository.save(reservation);
    }

    public void cancel(LocalDate date, TimeFrame timeFrame, String category, String email) {
        publicUtilsReservationRepository.deleteByPublicUtilsAndDateAndTimeFrame(new PublicUtils(category),
                date, timeFrame);
        PublicUtilsReservation reservation = new PublicUtilsReservation.Builder()
                .setDate(date)
                .setTimeFrame(timeFrame)
                .setPublicUtils(new PublicUtils(category))
                .setUser(new User.Builder().setEmail(email).build())
                .build();
        publicUtilsReservationRepository.save(reservation);
    }



}
