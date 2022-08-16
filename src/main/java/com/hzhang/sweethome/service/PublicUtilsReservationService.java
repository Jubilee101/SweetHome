package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.InvalidCategoryException;
import com.hzhang.sweethome.exception.PublicUtilsReservationAlreadyExistsException;
import com.hzhang.sweethome.model.InvoiceType;
import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.model.PublicUtilsReservation;
import com.hzhang.sweethome.model.TimeFrame;
import com.hzhang.sweethome.model.TimeSlot;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.PublicUtilsRepository;
import com.hzhang.sweethome.repository.PublicUtilsReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicUtilsReservationService {
    private PublicUtilsReservationRepository publicUtilsReservationRepository;
    private PersonalInvoiceService personalInvoiceService;
    private PublicUtilsRepository publicUtilsRepository;

    @Autowired
    PublicUtilsReservationService(PublicUtilsReservationRepository publicUtilsReservationRepository,
                                  PersonalInvoiceService personalInvoiceService,
                                  PublicUtilsRepository publicUtilsRepository) {
        this.publicUtilsReservationRepository = publicUtilsReservationRepository;
        this.personalInvoiceService = personalInvoiceService;
        this.publicUtilsRepository = publicUtilsRepository;
    }

    public List<TimeSlot> getAvailableTimeSlots(String category) {
        Optional<PublicUtils> util = publicUtilsRepository.findByCategory(category);
        if (!util.isPresent()) {
            throw new InvalidCategoryException("We don't have this category!");
        }
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
             date.isBefore(LocalDate.now().plusDays(6));
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
        List<PublicUtilsReservation> reservations = publicUtilsReservationRepository.findByUser(
                new User.Builder().setEmail(email).build());
        reservations.sort(new Comparator<PublicUtilsReservation>() {
            @Override
            public int compare(PublicUtilsReservation o1, PublicUtilsReservation o2) {
                if (o1.getDate().equals(o2.getDate())) {
                    return -1 * o1.getTimeFrame().compareTo(o2.getTimeFrame());
                } else {
                    return -1 * o1.getDate().compareTo(o2.getDate());
                }
            }
        });
        return reservations;
    }

    public void add(String category, LocalDate date, String time, String email) {
        TimeFrame timeFrame = parse(time);
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void cancel(LocalDate date, String time, String category, String email) {
        TimeFrame timeFrame = parse(time);
        Optional<PublicUtilsReservation> reservation = publicUtilsReservationRepository
                .findPublicUtilsReservationByPublicUtilsAndDateAndTimeFrame(new PublicUtils(category),
                        date, timeFrame);
        if (reservation.isPresent()) {
            publicUtilsReservationRepository.deleteByPublicUtilsAndDateAndTimeFrame(new PublicUtils(category),
                    date, timeFrame);
            String text = "So sorry, your reservation of "
                    + reservation.get().getCategory()
                    + " on " + reservation.get().getDate().toString() +
                    " at " + reservation.get().getTimeFrame().getTime() +
                    " is canceled!";
            personalInvoiceService.add(InvoiceType.RESERVATION.name(),
                    text, reservation.get().getUser());
        }
        PublicUtilsReservation reservationManager = new PublicUtilsReservation.Builder()
                .setDate(date)
                .setTimeFrame(timeFrame)
                .setPublicUtils(new PublicUtils(category))
                .setUser(new User.Builder().setEmail(email).build())
                .build();
        publicUtilsReservationRepository.save(reservationManager);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long id) {
        publicUtilsReservationRepository.deleteById(id);
    }

    private TimeFrame parse(String time) {
        TimeFrame timeFrame = null;
        for (TimeFrame t : TimeFrame.values()) {
            if (t.getTime().equals(time)) {
                timeFrame = t;
                break;
            }
        }
        return timeFrame;
    }
}
