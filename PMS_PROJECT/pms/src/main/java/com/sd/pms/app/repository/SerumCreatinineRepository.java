package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.SerumCreatinine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerumCreatinineRepository extends JpaRepository<SerumCreatinine,Long> {
    List<SerumCreatinine> findAllByPatientNicAndActive(String nic, Boolean aTrue);
}
