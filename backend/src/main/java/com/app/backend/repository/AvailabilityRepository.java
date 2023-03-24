package com.app.backend.repository;

import com.app.backend.models.Availability;
import com.app.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

  List<Availability> findAllByOrderByStartAsc();

  List<Availability> findAllByStartAfterAndEndBefore(LocalDateTime start, LocalDateTime end);

  List<Availability> findAllByLinkedUserAndStartAfterAndEndBefore(User user, LocalDateTime start, LocalDateTime end);

}
