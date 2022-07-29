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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalInvoiceService {
    private PersonalInvoiceRepository personalInvoiceReository;
    private UserRepository userRepository;
    private UnreadNumRepository unreadNumRepository;
    @Autowired
    public PersonalInvoiceService(PersonalInvoiceRepository personalInvoiceReository,
                                  UserRepository userRepository,
                                  UnreadNumRepository unreadNumRepository) {
        this.personalInvoiceReository = personalInvoiceReository;
        this.userRepository = userRepository;
        this.unreadNumRepository = unreadNumRepository;
    }

    public List<PersonalInvoice> listByUserAndType(String email, String type) throws PersonalInvoiceNotExistException {
        List<PersonalInvoice> invoiceList =  personalInvoiceReository
                .findByUserAndType(new User.Builder().setEmail(email).build(),type);
        if(invoiceList==null){
            return new ArrayList<>();
        }
        invoiceList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
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
                .build();
        personalInvoiceReository.save(personalInvoice);
        unreadNumRepository.increaseUnreadNumByOneById(user.get().getEmail(), type);
    }



}
