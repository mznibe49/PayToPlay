package com.app.backend.controllers;

import com.app.backend.controllers.payload.request.AvailabilityRequest;
import com.app.backend.controllers.payload.request.DeleteReservationRequest;
import com.app.backend.controllers.payload.response.AvailabilityResponse;
import com.app.backend.models.Availability;
import com.app.backend.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/availabilities", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvailabilitiesController {

  @Autowired
  AvailabilityService availabilityService;

  @GetMapping
  public List<AvailabilityResponse> listAvailabilities() {
    return availabilityService.loadAllAvailabilities().stream()
        .map(AvailabilityResponse::of)
        .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Availability createAvailability(@Valid @RequestBody AvailabilityRequest newAvailability) {
    return this.availabilityService.createAvailability(newAvailability);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAvailability(@PathVariable int id, DeleteReservationRequest deleteReservationRequest) {
    this.availabilityService.deleteById(id, deleteReservationRequest.getEmail());
  }
}
