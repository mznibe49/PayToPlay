package com.app.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
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
  @Size(max = 20)
  private String eventTitle;

  @CreationTimestamp private LocalDateTime createdTime;

  @ManyToOne
  private User linkedUser;

  public Reservation() {}

  public Reservation(
      Availability availability,
      LocalDateTime start,
      LocalDateTime end,
      String eventTitle,
      User linkedUser) {

    this.availability = availability;
    this.start = start;
    this.end = end;
    this.eventTitle = eventTitle;
    this.linkedUser = linkedUser;
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

  /* public String getReservationEmail() {
    return reservationEmail;
  }

  public void setReservationEmail(String reservationEmail) {
    this.reservationEmail = reservationEmail;
  } */

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

  public User getLinkedUser(){ return getLinkedUser(); }

  public void setLinkedUser(User user){ this.linkedUser = user; }
}
