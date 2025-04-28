package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.WBmi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WBmiRepository extends JpaRepository<WBmi , Long> {
    List<WBmi> findAllByPatientNicAndActive(String nic, Boolean aTrue);
}
