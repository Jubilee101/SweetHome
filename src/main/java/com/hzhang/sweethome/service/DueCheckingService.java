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
    private PersonalInvoiceService personalInvoiceService;

    @Autowired
    public DueCheckingService(PersonalInvoiceService personalInvoiceService) {
        this.personalInvoiceService = personalInvoiceService;
    }

    public void checkDue(String email) {
        if (LocalDate.now().getDayOfMonth() != 29) {
            return;
        }
        List<PersonalInvoice> paymentInvoices = personalInvoiceService
                .findByUserAndTypeAndDate(new User.Builder().setEmail(email).build(),
                        InvoiceType.PAYMENT.name(), LocalDate.now());
        if (!paymentInvoices.isEmpty()) {
            return;
        }
        String text = "Your payment due is today!";
        User user = new User.Builder().setEmail(email).build();
        personalInvoiceService.add(InvoiceType.PAYMENT.name(), text, user);
    }
}
