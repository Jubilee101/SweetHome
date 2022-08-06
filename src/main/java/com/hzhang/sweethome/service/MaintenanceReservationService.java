package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.MaintenanceReservationNotFoundException;
import com.hzhang.sweethome.exception.UserNotExistException;
import com.hzhang.sweethome.model.InvoiceType;
import com.hzhang.sweethome.model.MaintenanceImage;
import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.MaintenanceReservationRepository;
import com.hzhang.sweethome.repository.UserRepository;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaintenanceReservationService {
    private MaintenanceReservationRepository maintenanceReservationRepository;
    private UserRepository userRepository;
    private ImageStorageService imageStorageService;
    private PersonalInvoiceService personalInvoiceService;

    @Autowired
    public MaintenanceReservationService(MaintenanceReservationRepository maintenanceReservationRepository,
                                         ImageStorageService imageStorageService,
                                         UserRepository userRepository,
                                         PersonalInvoiceService personalInvoiceService){
        this.maintenanceReservationRepository = maintenanceReservationRepository;
        this.imageStorageService = imageStorageService;
        this.userRepository = userRepository;
        this.personalInvoiceService = personalInvoiceService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(String email, String description, MultipartFile[] images){
        Optional<User> user = userRepository.findById(email);
        if (!user.isPresent()) {
            throw new UserNotExistException("Cannot find user!");
        }
        MaintenanceReservation maintenanceReservation = new MaintenanceReservation.Builder()
                .setDescription(description)
                .setUser(new User.Builder().setEmail(email).build())
                .build();

        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> imageStorageService.save(image)).collect(Collectors.toList());
        List<MaintenanceImage> maintenanceImageList = new ArrayList<>();
        for(String mediaLink : mediaLinks){
            maintenanceImageList.add(new MaintenanceImage(mediaLink, maintenanceReservation));
        }
        maintenanceReservation.setMaintenanceImages(maintenanceImageList);

        maintenanceReservationRepository.save(maintenanceReservation);
    }

    public List<MaintenanceReservation> findByUser(String email) throws MaintenanceReservationNotFoundException{
        Optional<User> user = userRepository.findById(email);
        if (!user.isPresent()) {
            throw new UserNotExistException("Cannot find user!");
        }
        List<MaintenanceReservation> reservations = maintenanceReservationRepository.findByUser(user.get());
        if (!reservations.isEmpty()) {
            reservations.sort((MaintenanceReservation o1, MaintenanceReservation o2) -> {
                if (o1.getDate() == null && o2.getDate() == null) {
                    return 0;
                }
                else if (o1.getDate() == null) {
                    return -1;
                }
                else if (o2.getDate() == null) {
                    return 1;
                }
                else {
                    if (o1.getDate().equals(o2.getDate())) {
                        return -1 * o1.getStartTime().compareTo(o2.getStartTime());
                    }
                    else {
                        return -1 * o1.getDate().compareTo(o2.getDate());
                    }
                }
            });
        }
        return reservations;
    }

    public List<MaintenanceReservation> findall() {
        List<MaintenanceReservation> reservations = maintenanceReservationRepository.findAll();
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

    public void updateDate(LocalDate date, Long id){
        maintenanceReservationRepository.updateDate(date,id);
    }
    public void updateTime(LocalTime startTime, Long id){
        maintenanceReservationRepository.updateTime(startTime,id);
    }

    public  void updateTimeAndDate(LocalDate date,LocalTime startTime,Long id){
        updateDate(date,id);
        updateTime(startTime,id);
        MaintenanceReservation reservation = maintenanceReservationRepository.findById(id).get();
        User user = reservation.getUser();
        String text = "Notice! Your maintenance request has been handled and updated!";
        personalInvoiceService.add(InvoiceType.RESERVATION.name(), text, user);
    }
}
