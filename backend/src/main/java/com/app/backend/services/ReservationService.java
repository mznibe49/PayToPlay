package com.app.backend.services;

import com.app.backend.controllers.payload.request.CreateReservationRequest;
import com.app.backend.domain.TimeSlot;
import com.app.backend.exceptions.domain.*;
import com.app.backend.models.Availability;
import com.app.backend.models.Reservation;
import com.app.backend.repository.AvailabilityRepository;
import com.app.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final AvailabilityRepository availabilityRepository;

  @Autowired
  public ReservationService(
      ReservationRepository reservationRepository, AvailabilityRepository availabilityRepository) {
    this.reservationRepository = reservationRepository;
    this.availabilityRepository = availabilityRepository;
  }

  public List<Reservation> loadAllReservations() {
    return reservationRepository.findAllByOrderByStartAsc();
  }

  @Transactional
  public Reservation create(CreateReservationRequest request) {
    Availability availability =
        this.availabilityRepository
            .findById(request.getAvailabilityId())
            .orElseThrow(() -> new NoSuchAvailabilityException(request.getAvailabilityId()));

    TimeSlot availabilityTimeSlot = new TimeSlot(availability.getStart(), availability.getEnd());
    TimeSlot newReservationTimeSlot = new TimeSlot(request.getStart(), request.getEnd());

    if (!availabilityTimeSlot.contains(newReservationTimeSlot)) {
      throw new ReservationNotFittingException();
    }

    availability
        .getReservations()
        .forEach(
            r -> {
              TimeSlot reservationTimeSlot = new TimeSlot(r.getStart(), r.getEnd());

              if (reservationTimeSlot.isOverlappedBy(newReservationTimeSlot)) {
                throw new OverlappingReservationException();
              }
            });

    Reservation reservation =
        new Reservation(
            null,
            availability,
            request.getStart(),
            request.getEnd(),
            request.getEmail(),
            request.getTitle(),
            null);

    availability.getReservations().add(reservation);

    return reservation;
  }

  public void delete(long id, String email) {
    // comparing with the email of the current user to avoid sec pbs
    Reservation reservation =
        this.reservationRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchReservationException(id));

    if (!reservation.getReservationEmail().equals(email)) {
      throw new ForbiddenOperationException();
    }

    this.reservationRepository.delete(reservation);
  }
}
