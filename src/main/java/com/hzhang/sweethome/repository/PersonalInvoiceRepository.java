package com.hzhang.sweethome.repository;
import com.hzhang.sweethome.model.PersonalInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonalInvoiceRepository extends JpaRepository<PersonalInvoice,Long>  {
    List<PersonalInvoice> findByEmailAndType(String email,String type);
    List<PersonalInvoice> findByEmailAndTypeAndDate(String email, String type, LocalDate date);
}
