package com.app.backend.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "availability")
public class Availability {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "start_date")
  private LocalDateTime start;

  @Column(name = "end_date")
  private LocalDateTime end;

  @CreationTimestamp private LocalDateTime createdTime;

  @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations = new ArrayList<>();

  @ManyToOne
  private User linkedUser;

  public Availability(LocalDateTime start, LocalDateTime end, User user) {
    this.start = start;
    this.end = end;
    this.linkedUser = user;
  }
}
