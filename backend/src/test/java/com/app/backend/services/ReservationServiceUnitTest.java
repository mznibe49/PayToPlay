package com.app.backend.services;

import com.app.backend.controllers.payload.request.CreateReservationRequest;
import com.app.backend.exceptions.domain.NoSuchAvailabilityException;
import com.app.backend.exceptions.domain.OverlappingReservationException;
import com.app.backend.exceptions.domain.ReservationNotFittingException;
import com.app.backend.models.Availability;
import com.app.backend.models.Reservation;
import com.app.backend.repository.AvailabilityRepository;
import com.app.backend.repository.ReservationRepository;
import com.app.backend.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationServiceUnitTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        reservationService = new ReservationService(reservationRepository, availabilityRepository, userRepository);
    }

    @Test
    void shouldThrowIfNotFoundId() {
        Mockito.when(availabilityRepository.findById(42L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> reservationService.create(requestWithStart()))
                .isInstanceOf(NoSuchAvailabilityException.class);
    }

    @Test
    void shouldThrowIfNotFittingInsideAvailability() {
        Mockito.when(availabilityRepository.findById(42L)).thenReturn(Optional.of(emptyAvailability()));

        Assertions.assertThatThrownBy(
                        () -> reservationService.create(requestWithStart(LocalDateTime.of(1997, 1, 1, 11, 0))))
                .isInstanceOf(ReservationNotFittingException.class);
    }

    @Test
    void shouldAddReservationIfEmptyAvailability() {
        Mockito.when(availabilityRepository.findById(42L)).thenReturn(Optional.of(emptyAvailability()));

        Reservation reservation = reservationService.create(requestWithStart());

        Assertions.assertThat(reservation.getLinkedUser().getEmail()).isEqualTo("dupont@giskard.com");
        Assertions.assertThat(reservation.getEventTitle()).isEqualTo("Un joli titre");
        Assertions.assertThat(reservation.getStart()).isEqualTo(LocalDateTime.of(1997, 1, 1, 12, 30));
        Assertions.assertThat(reservation.getEnd()).isEqualTo(LocalDateTime.of(1997, 1, 1, 13, 0));
    }

    @Test
    void shouldThrowIfReservationContainsOverlappingReservation() {
        Mockito.when(availabilityRepository.findById(42L)).thenReturn(Optional.of(availability()));

        Assertions.assertThatThrownBy(() -> reservationService.create(requestWithStart()))
                .isInstanceOf(OverlappingReservationException.class);
    }

    @Test
    void shouldCreateIfNotOverlappingExistingReservations() {
        Mockito.when(availabilityRepository.findById(42L)).thenReturn(Optional.of(availability()));

        Reservation reservation =
                reservationService.create(requestWithStart(LocalDateTime.of(1997, 1, 1, 13, 0)));

        Assertions.assertThat(reservation.getLinkedUser().getEmail()).isEqualTo("dupont@giskard.com");
        Assertions.assertThat(reservation.getEventTitle()).isEqualTo("Un joli titre");
        Assertions.assertThat(reservation.getStart()).isEqualTo(LocalDateTime.of(1997, 1, 1, 13, 0));
        Assertions.assertThat(reservation.getEnd()).isEqualTo(LocalDateTime.of(1997, 1, 1, 13, 30));
    }

    private CreateReservationRequest requestWithStart() {
        return CreateReservationRequest.builder()
                .availabilityId(42L)
                .start(LocalDateTime.of(1997, 1, 1, 12, 30))
                .end(LocalDateTime.of(1997, 1, 1, 13, 0))
                .email("dupont@giskard.com")
                .title("Un joli titre")
                .build();
    }

    private CreateReservationRequest requestWithStart(LocalDateTime start) {
        return CreateReservationRequest.builder()
                .availabilityId(42L)
                .start(start)
                .end(LocalDateTime.of(1997, 1, 1, 13, 30))
                .email("dupont@giskard.com")
                .title("Un joli titre")
                .build();
    }

    private Availability emptyAvailability() {
        return Availability.builder()
                .id(42L)
                .start(LocalDateTime.of(1997, 1, 1, 12, 30))
                .end(LocalDateTime.of(1997, 1, 1, 14, 30))
                .reservations(new ArrayList<>())
                .build();
    }

    private Availability availability() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(
                Reservation.builder()
                        .id(1L)
                        // .reservationEmail("some1")
                        .eventTitle("title1")
                        .createdTime(LocalDateTime.now())
                        .start(LocalDateTime.of(1997, 1, 1, 12, 30))
                        .end(LocalDateTime.of(1997, 1, 1, 13, 0))
                        .build());
        reservations.add(
                Reservation.builder()
                        .id(2L)
                        // .reservationEmail("some2")
                        .eventTitle("title2")
                        .createdTime(LocalDateTime.now())
                        .start(LocalDateTime.of(1997, 1, 1, 13, 30))
                        .end(LocalDateTime.of(1997, 1, 1, 14, 0))
                        .build());

        return Availability.builder()
                .id(42L)
                .start(LocalDateTime.of(1997, 1, 1, 12, 30))
                .end(LocalDateTime.of(1997, 1, 1, 14, 30))
                .reservations(reservations)
                .build();
    }
}
