package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.BloodPressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressure,Long> {
    List<BloodPressure> findAllByPatientNicAndActive(String nic, Boolean aTrue);
}
