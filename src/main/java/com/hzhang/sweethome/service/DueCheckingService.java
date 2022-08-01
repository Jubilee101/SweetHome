package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.InvoiceType;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.PersonalInvoiceRepository;
import com.hzhang.sweethome.repository.UnreadNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DueCheckingService {
    private PersonalInvoiceRepository personalInvoiceRepository;
    private UnreadNumRepository unreadNumRepository;

    @Autowired
    public DueCheckingService(PersonalInvoiceRepository personalInvoiceRepository,
                              UnreadNumRepository unreadNumRepository) {
        this.personalInvoiceRepository = personalInvoiceRepository;
        this.unreadNumRepository = unreadNumRepository;
    }

    public void checkDue(String email) {
        if (LocalDate.now().getDayOfMonth() != 29) {
            return;
        }
        List<PersonalInvoice> paymentInvoices = personalInvoiceRepository
                .findByUserAndTypeAndDate(new User.Builder().setEmail(email).build(), InvoiceType.PAYMENT.name(), LocalDate.now());
        if (!paymentInvoices.isEmpty()) {
            return;
        }
        PersonalInvoice paymentInvoice = new PersonalInvoice.Builder()
                .setDate(LocalDate.now())
                .setText("Your payment due is today!")
                .setType(InvoiceType.PAYMENT.name())
                .setUser(new User.Builder().setEmail(email).build())
                .setTime(LocalTime.now())
                .build();
        personalInvoiceRepository.save(paymentInvoice);
        unreadNumRepository.increaseUnreadNumByOneById(email, InvoiceType.PAYMENT.name());
    }
}
