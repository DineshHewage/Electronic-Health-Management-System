package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.LipidProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LipidProfileRepository extends JpaRepository<LipidProfile,Long> {
    List<LipidProfile> findAllByPatientNicAndActive(String id, Boolean aTrue);
}
