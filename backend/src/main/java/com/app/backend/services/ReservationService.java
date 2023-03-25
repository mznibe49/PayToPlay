package com.app.backend.services;

import com.app.backend.controllers.payload.request.CreateReservationRequest;
import com.app.backend.domain.TimeSlot;
import com.app.backend.exceptions.NoSuchResourceException;
import com.app.backend.exceptions.domain.*;
import com.app.backend.models.Availability;
import com.app.backend.models.Reservation;
import com.app.backend.models.User;
import com.app.backend.repository.AvailabilityRepository;
import com.app.backend.repository.ReservationRepository;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

  /*
      repositories are called with a constructor this way in this service
      instead of using Autowired is for testing purpose
      is there a way to use Autowired ? hmm ...
   */

    private ReservationRepository reservationRepository;

    private AvailabilityRepository availabilityRepository;

    private UserRepository userRepository;


    public ReservationService(
            ReservationRepository reservationRepository, AvailabilityRepository availabilityRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    public List<Reservation> loadAllReservations() {
        return reservationRepository.findAllByOrderByStartAsc();
    }

    @Transactional
    public Reservation create(CreateReservationRequest request) {

        // if user not found we are not allowed to create the reservation
        User userCreatingReservation = userRepository.findByEmail(request.getEmail()).orElseThrow(ForbiddenOperationException::new);

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
                        availability,
                        request.getStart(),
                        request.getEnd(),
                        request.getTitle(),
                        userCreatingReservation);

        availability.getReservations().add(reservation);

        return reservation;
    }

    public void delete(long id, String email) {
        // comparing with the email of the current user to avoid sec pbs
        Reservation reservation =
                this.reservationRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchReservationException(id));

        User userDeletingReservation = this.userRepository
                .findByEmail(email).orElseThrow(ForbiddenOperationException::new);

        // adding security to ensure that the person who is deleting is the person who created the reservation
        if (!userDeletingReservation.getId().equals(reservation.getLinkedUser().getId())) {
            throw new ForbiddenOperationException();
        }

        this.reservationRepository.delete(reservation);
    }
}
