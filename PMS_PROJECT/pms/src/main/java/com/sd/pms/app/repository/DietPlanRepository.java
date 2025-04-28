package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan,Long> {
    List<DietPlan> findAllByPatientNicAndActive(String nic, Boolean aTrue);
}
