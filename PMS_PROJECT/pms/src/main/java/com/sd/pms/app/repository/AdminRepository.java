package com.sd.pms.app.repository;

import com.sd.pms.app.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    List<Admin> findAllByActive(Boolean aTrue);
}
