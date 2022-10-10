package com.app.backend.models;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "reservation")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Availability availability;

  @Column(name = "start_date")
  private LocalDateTime start;

  @Column(name = "end_date")
  private LocalDateTime end;

  @NotBlank
  @Size(max = 50)
  @Email
  private String reservationEmail; // email of a person making the reservation

  @NotBlank
  @Size(max = 20)
  private String eventTitle;

  @CreationTimestamp private LocalDateTime createdTime;

  public Reservation() {}

  public Reservation(
      Long id,
      Availability availability,
      LocalDateTime start,
      LocalDateTime end,
      String reservationEmail,
      String eventTitle,
      LocalDateTime createdTime) {
    this.id = id;
    this.availability = availability;
    this.start = start;
    this.end = end;
    this.reservationEmail = reservationEmail;
    this.eventTitle = eventTitle;
    this.createdTime = createdTime;
  }

  public Reservation(LocalDateTime start, LocalDateTime end, String email, String title) {
    this.start = start;
    this.end = end;
    this.eventTitle = title;
    this.reservationEmail = email;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public Availability getAvailability() {
    return availability;
  }

  public void setAvailability(Availability availability) {
    this.availability = availability;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public String getReservationEmail() {
    return reservationEmail;
  }

  public void setReservationEmail(String reservationEmail) {
    this.reservationEmail = reservationEmail;
  }

  public String getEventTitle() {
    return eventTitle;
  }

  public void setEventTitle(String eventTitle) {
    this.eventTitle = eventTitle;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
