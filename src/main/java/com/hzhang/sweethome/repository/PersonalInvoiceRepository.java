package com.hzhang.sweethome.repository;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonalInvoiceRepository extends JpaRepository<PersonalInvoice,Long>  {
    List<PersonalInvoice> findByUserAndType(User user, String type);
    List<PersonalInvoice> findByUserAndTypeAndDate(User user, String type, LocalDate date);
}
