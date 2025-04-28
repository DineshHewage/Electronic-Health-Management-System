package com.sd.pms.app.repository;

import com.sd.pms.app.entity.Occurrence;
import com.sd.pms.app.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    List<Recommendation> findAllByOccurrenceAndNextFire(Occurrence occurrence, LocalDate nextFire);
    List<Recommendation> findAllByNextFireAndActive(LocalDate nextFire , Boolean active);
    List<Recommendation> findAllByActive(Boolean active);
    List<Recommendation> findAllByPatientNicAndOccurrence(String nic , Occurrence occurrence);
}
