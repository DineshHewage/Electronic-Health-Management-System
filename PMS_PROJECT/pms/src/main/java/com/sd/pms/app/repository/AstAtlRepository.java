package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.AstAtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AstAtlRepository extends JpaRepository<AstAtl,Long> {
    List<AstAtl> findAllByPatientNicAndActive(String nic, Boolean aTrue);
}
