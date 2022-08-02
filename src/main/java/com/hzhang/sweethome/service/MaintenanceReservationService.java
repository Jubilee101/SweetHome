package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.MaintenanceReservationNotFoundException;
import com.hzhang.sweethome.model.MaintenanceImage;
import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.MaintenanceReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceReservationService {
    private MaintenanceReservationRepository maintenanceReservationRepository;
    private ImageStorageService imageStorageService;

    @Autowired
    public MaintenanceReservationService(MaintenanceReservationRepository maintenanceReservationRepository){
        this.maintenanceReservationRepository = maintenanceReservationRepository;
        this.imageStorageService = imageStorageService;
    }

    public void add(MaintenanceReservation maintenanceReservation, MultipartFile[] images){
        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> imageStorageService.save(image)).collect(Collectors.toList());
        List<MaintenanceImage> maintenanceImageList = new ArrayList<>();
        for(String mediaLink : mediaLinks){
            maintenanceImageList.add(new MaintenanceImage(mediaLink, maintenanceReservation));
        }
        maintenanceReservation.setMaintenanceImages(maintenanceImageList);
        maintenanceReservationRepository.save(maintenanceReservation);
    }


    // 为什么是room和name，不是id和name ？
    public MaintenanceReservation findByRoomAndName(String room, String name) throws MaintenanceReservationNotFoundException{
        MaintenanceReservation maintenanceReservation = maintenanceReservationRepository.findByRoomAndName(room, new User.Builder().setName(name).build());
        return maintenanceReservation;
    }

    public List<MaintenanceReservation> listAll() throws MaintenanceReservationNotFoundException{
        List<MaintenanceReservation> allMaintenanceReservation = maintenanceReservationRepository.findAll();
        allMaintenanceReservation.sort((o1, o2) ->{
            if (o1.getDate().equals(o2.getDate())) {
                return -1 * o1.getStartTime().compareTo(o2.getStartTime());
            }
            return -1 * o1.getDate().compareTo(o2.getDate());
        });
        return allMaintenanceReservation;
    }
    /*
    public void add(MaintenanceReservation maintenanceReservation){
        maintenanceReservationRepository.save(maintenanceReservation);
    }
    /*
    public void delete(String room, String name) throws MaintenanceReservationNotFoundException{
        MaintenanceReservation maintenanceReservation = maintenanceReservationRepository.findByRoomAndName(room, new User.Builder().setName(name).build());
        if(maintenanceReservation == null){
            throw new MaintenanceReservationNotFoundException("Maintenance reservation does not exist");
        }
        maintenanceReservationRepository.deleteById(maintenanceReservation.getId());    // ???
    }
     */

}
