package com.sd.pms.app.repository;

import com.sd.pms.app.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByActive(Boolean aTrue);

    Optional<Patient> findByNicAndActive(String nic , Boolean active);
    Optional<Patient> findByUserId(Long id);
}
