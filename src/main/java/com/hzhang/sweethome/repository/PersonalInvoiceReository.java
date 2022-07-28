package com.hzhang.sweethome.repository;
import com.hzhang.sweethome.model.PersonalInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
public interface PersonalInvoiceReository extends JpaRepository<PersonalInvoice,Long>  {
    List<String> findByEmailandType(String email,String type);
}
