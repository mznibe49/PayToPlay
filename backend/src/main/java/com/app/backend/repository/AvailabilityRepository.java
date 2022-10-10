package com.app.backend.repository;

import com.app.backend.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

  List<Availability> findAllByOrderByStartAsc();

  List<Availability> findAllByStartAfterAndEndBefore(LocalDateTime start, LocalDateTime end);

}
