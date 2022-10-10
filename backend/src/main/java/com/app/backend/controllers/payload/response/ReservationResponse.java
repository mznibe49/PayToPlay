package com.app.backend.controllers.payload.response;

import com.app.backend.models.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class ReservationResponse {

  private long id;
  private long availabilityId;
  private LocalDateTime start;
  private LocalDateTime end;
  private String email;
  private String eventTitle;
  private LocalDateTime createdTime;

  public static ReservationResponse of(Reservation reservation) {
    return ReservationResponse.builder()
        .id(reservation.getId())
        .createdTime(reservation.getCreatedTime())
        .availabilityId(reservation.getId())
        .eventTitle(reservation.getEventTitle())
        .start(reservation.getStart())
        .end(reservation.getEnd())
        .email(reservation.getReservationEmail())
        .build();
  }
}
