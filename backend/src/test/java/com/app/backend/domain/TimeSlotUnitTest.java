package com.app.backend.domain;

import com.app.backend.exceptions.domain.InvalidTimeSlotException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TimeSlotUnitTest {

  @Test
  void shouldNotCreateWithNullDates() {
    Assertions.assertThatThrownBy(() -> new TimeSlot(null, end()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Missing mandatory field start");

    Assertions.assertThatThrownBy(() -> new TimeSlot(start(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Missing mandatory field end");
  }

  @Test
  void shouldNotCreateWithStartAfterEnd() {
    Assertions.assertThatThrownBy(() -> new TimeSlot(end(), start()))
        .isInstanceOf(InvalidTimeSlotException.class)
        .hasMessage("Start must be before end");
  }

  @Test
  void shouldNotCreateWithStartAndEndFromDifferentDay() {
    Assertions.assertThatThrownBy(() -> new TimeSlot(start(), end().plusDays(1)))
            .isInstanceOf(InvalidTimeSlotException.class)
            .hasMessage("Start and End date must be in the same day");
  }

  @Test
  void shouldCreate() {
    TimeSlot timeSlot = new TimeSlot(start(), end());

    Assertions.assertThat(timeSlot.getStart()).isEqualTo(start());
    Assertions.assertThat(timeSlot.getEnd()).isEqualTo(end());
  }

  @Test
  void shouldCheckTruthyOverlapping() {
    // Start:   1996-01-01T12:00:00
    // End:     1996-01-01T13:30:00
    TimeSlot mainSlot =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 30));

    // Start:   1996-01-01T11:30:00
    // End:     1996-01-01T12:30:00
    TimeSlot timeSlot2 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 11, 0), LocalDateTime.of(1996, 1, 1, 12, 30));

    // Start:   1996-01-01T12:45:00
    // End:     1996-01-01T13:50:00
    TimeSlot timeSlot3 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 45), LocalDateTime.of(1996, 1, 1, 13, 50));

    // Start:   1996-01-01T12:00:00
    // End:     1996-01-01T13:00:00
    TimeSlot timeSlot4 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 0));

    Assertions.assertThat(mainSlot.isOverlappedBy(timeSlot2)).isTrue();
    Assertions.assertThat(mainSlot.isOverlappedBy(timeSlot3)).isTrue();
    Assertions.assertThat(mainSlot.isOverlappedBy(timeSlot4)).isTrue();
    Assertions.assertThat(mainSlot.isOverlappedBy(mainSlot)).isTrue();
  }

  @Test
  void shouldCheckFalsyOverlapping() {
    // Start:   1996-01-01T12:00:00
    // End:     1996-01-01T13:30:00
    TimeSlot mainSlot =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 30));

    // Start:   1996-01-01T11:00:00
    // End:     1996-01-01T12:00:00
    TimeSlot timeSlot1 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 11, 0), LocalDateTime.of(1996, 1, 1, 12, 0));

    // Start:   1996-01-01T13:30:00
    // End:     1996-01-01T15:00:00
    TimeSlot timeSlot2 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 13, 30), LocalDateTime.of(1996, 1, 1, 15, 0));

    Assertions.assertThat(mainSlot.isOverlappedBy(timeSlot1)).isFalse();
    Assertions.assertThat(mainSlot.isOverlappedBy(timeSlot2)).isFalse();
  }

  @Test
  void shouldCheckTruthyContains() {
    TimeSlot mainSlot =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 30));

    TimeSlot otherSlot1 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 0));

    TimeSlot otherSlot2 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 10), LocalDateTime.of(1996, 1, 1, 13, 10));

    Assertions.assertThat(mainSlot.contains(mainSlot)).isTrue();
    Assertions.assertThat(mainSlot.contains(otherSlot1)).isTrue();
    Assertions.assertThat(mainSlot.contains(otherSlot2)).isTrue();
  }

  @Test
  void shouldCheckFalsyContains() {
    TimeSlot mainSlot =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 0), LocalDateTime.of(1996, 1, 1, 13, 30));

    TimeSlot otherSlot1 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 11, 44), LocalDateTime.of(1996, 1, 1, 13, 0));

    TimeSlot otherSlot2 =
        new TimeSlot(LocalDateTime.of(1996, 1, 1, 12, 45), LocalDateTime.of(1996, 1, 1, 13, 56));

    Assertions.assertThat(mainSlot.contains(otherSlot1)).isFalse();
    Assertions.assertThat(mainSlot.contains(otherSlot2)).isFalse();
  }

  private LocalDateTime start() {
    return LocalDateTime.of(1996, 1, 1, 10, 0);
  }

  private LocalDateTime end() {
    return LocalDateTime.of(1996, 1, 1, 12, 0);
  }
}
