package com.app.backend.controllers.payload.response;

import com.app.backend.models.Availability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class AvailabilityResponse {
  private final long id;
  private final LocalDateTime start;
  private final LocalDateTime end;
  private final List<ReservationResponse> reservations;

  public static AvailabilityResponse of(Availability availability) {
    return AvailabilityResponse.builder()
        .id(availability.getId())
        .start(availability.getStart())
        .end(availability.getEnd())
        .reservations(
            availability.getReservations().stream()
                .map(ReservationResponse::of)
                .collect(Collectors.toList()))
        .build();
  }
}
