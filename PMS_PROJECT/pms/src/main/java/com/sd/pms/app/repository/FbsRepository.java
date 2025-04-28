package com.sd.pms.app.repository;

import com.sd.pms.app.entity.reports.Fbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FbsRepository extends JpaRepository<Fbs,Long> {
    List<Fbs> findAllByPatientNicAndActive(String nic , Boolean active);
}
