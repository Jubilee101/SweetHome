package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.Public_invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Public_invoicesRepository extends JpaRepository<Public_invoices, Long> {

}
