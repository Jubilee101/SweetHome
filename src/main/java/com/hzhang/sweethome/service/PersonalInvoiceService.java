package com.hzhang.sweethome.service;
import com.hzhang.sweethome.exception.PersonalInvoiceNotExistException;
import com.hzhang.sweethome.exception.UserNotExistException;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.PersonalInvoiceRepository;
import com.hzhang.sweethome.repository.UnreadNumRepository;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalInvoiceService {
    private PersonalInvoiceRepository personalInvoiceReository;
    private UserRepository userRepository;
    //private UnreadNumRepository unreadNumRepository;
    private UnreadNumService unreadNumService;
    @Autowired
    public PersonalInvoiceService(PersonalInvoiceRepository personalInvoiceReository,
                                  UserRepository userRepository,
                                  UnreadNumService unreadNumService) {
        this.personalInvoiceReository = personalInvoiceReository;
        this.userRepository = userRepository;
        this.unreadNumService = unreadNumService;
    }

    public List<PersonalInvoice> listByUserAndType(String email, String type) throws PersonalInvoiceNotExistException {
        List<PersonalInvoice> invoiceList =  personalInvoiceReository
                .findByUserAndType(new User.Builder().setEmail(email).build(),type);
        if(invoiceList==null){
            return new ArrayList<>();
        }
        invoiceList.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate())) {
                return -1 * o1.getTime().compareTo(o2.getTime());
            }
            return -1 * o1.getDate().compareTo(o2.getDate());
        });
        return invoiceList;
    }

    public void add(String type, String text, String name, String room){
        Optional<User> user = userRepository.findByRoomAndName(room, name);
        if (!user.isPresent()) {
            throw new UserNotExistException("Can not find user!");
        }
        PersonalInvoice personalInvoice = new PersonalInvoice
                .Builder()
                .setDate(LocalDate.now())
                .setText(text)
                .setType(type)
                .setUser(user.get())
                .setTime(LocalTime.now())
                .build();
        personalInvoiceReository.save(personalInvoice);
        unreadNumService.increaseUnreadNumByOne(user.get().getEmail(), type);
    }

    public void add(String type, String text, User user){
        PersonalInvoice personalInvoice = new PersonalInvoice
                .Builder()
                .setDate(LocalDate.now())
                .setText(text)
                .setType(type)
                .setUser(user)
                .setTime(LocalTime.now())
                .build();
        personalInvoiceReository.save(personalInvoice);
        unreadNumService.increaseUnreadNumByOne(user.getEmail(), type);
    }

    public List<PersonalInvoice> findByUserAndTypeAndDate(User user, String type, LocalDate date){
        return personalInvoiceReository.findByUserAndTypeAndDate(user, type, date);
    }

}
