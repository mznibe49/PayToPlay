package com.app.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
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
}
