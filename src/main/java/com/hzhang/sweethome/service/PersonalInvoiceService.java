package com.hzhang.sweethome.service;
import com.hzhang.sweethome.exception.PersonalInvoiceNotExistException;
import com.hzhang.sweethome.exception.UserNotExistException;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.PersonalInvoiceReository;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PersonalInvoiceService {
    private PersonalInvoiceReository personalInvoiceReository;
    private UserRepository userRepository;
    @Autowired
    public PersonalInvoiceService(PersonalInvoiceReository personalInvoiceReository,
                                  UserRepository userRepository) {
        this.personalInvoiceReository = personalInvoiceReository;
        this.userRepository = userRepository;
    }

    public List<PersonalInvoice> listByUserandType(String email, String type) throws PersonalInvoiceNotExistException {
        List<PersonalInvoice> invoiceList =  personalInvoiceReository.findByEmailandType(email,type);
        if(invoiceList==null){
            throw new PersonalInvoiceNotExistException("Invoice does not exist");
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
    }



}
