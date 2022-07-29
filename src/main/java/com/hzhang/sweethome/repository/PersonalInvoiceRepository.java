package com.hzhang.sweethome.repository;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonalInvoiceRepository extends JpaRepository<PersonalInvoice,Long>  {
    List<PersonalInvoice> findByUserAndType(User user, String type);
    List<PersonalInvoice> findByUserAndTypeAndDate(User user, String type, LocalDate date);
}
