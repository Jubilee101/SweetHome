package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.MaintenanceReservationNotFoundException;
import com.hzhang.sweethome.exception.UserNotExistException;
import com.hzhang.sweethome.model.MaintenanceImage;
import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.MaintenanceReservationRepository;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public MaintenanceReservationService(MaintenanceReservationRepository maintenanceReservationRepository,
                                         ImageStorageService imageStorageService,
                                         UserRepository userRepository){
        this.maintenanceReservationRepository = maintenanceReservationRepository;
        this.imageStorageService = imageStorageService;
        this.userRepository = userRepository;
    }

    public void add(String email, String description, MultipartFile[] images){
        Optional<User> user = userRepository.findById(email);
        if (!user.isPresent()) {
            throw new UserNotExistException("Cannot find user!");
        }
        User resident = user.get();
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
}
