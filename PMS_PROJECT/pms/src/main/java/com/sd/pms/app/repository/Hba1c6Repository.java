package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.Hba1c6;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Hba1c6Repository extends JpaRepository<Hba1c6,Long> {
    List<Hba1c6> findAllByPatientNicAndActive(String id, Boolean aTrue);
}
