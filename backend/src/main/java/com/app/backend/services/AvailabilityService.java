package com.app.backend.services;

import com.app.backend.controllers.payload.request.AvailabilityRequest;
import com.app.backend.domain.TimeSlot;
import com.app.backend.exceptions.domain.ForbiddenOperationException;
import com.app.backend.exceptions.domain.NoSuchAvailabilityException;
import com.app.backend.exceptions.domain.OverlappingAvailabilityException;
import com.app.backend.models.Availability;
import com.app.backend.models.User;
import com.app.backend.repository.AvailabilityRepository;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private final UserRepository userRepository;

    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityService(UserRepository userRepository, AvailabilityRepository availabilityRepository) {
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Availability> loadAllAvailabilities() {
        return availabilityRepository.findAllByOrderByStartAsc();
    }

    public Availability createAvailability(AvailabilityRequest newAvailability) {

        TimeSlot newAvailabilityTimeSlot = new TimeSlot(newAvailability.getStart(), newAvailability.getEnd());

        LocalDateTime startOfDay = newAvailability.getStart().minusHours(newAvailability.getStart().getHour());
        LocalDateTime endOfDay = newAvailability.getEnd().plusHours(24L - newAvailability.getEnd().getHour());

        Optional<User> linkedUser = userRepository.findUserById(newAvailability.getUserId());

        if (linkedUser.isPresent()) {
            // get all day availabilities + verify overlapping with them
            List<Availability> todayAvailabilities = availabilityRepository
                    .findAllByLinkedUserAndStartAfterAndEndBefore(linkedUser.get(), startOfDay, endOfDay);

            todayAvailabilities
                    .forEach(
                            a -> {
                                TimeSlot availabilityTimeSlot = new TimeSlot(a.getStart(), a.getEnd());

                                if (availabilityTimeSlot.isOverlappedBy(newAvailabilityTimeSlot)) {
                                    throw new OverlappingAvailabilityException();
                                }
                            });

            Availability availability = new Availability(newAvailability.getStart(), newAvailability.getEnd(), linkedUser.get());
            return availabilityRepository.save(availability);
        }
       // trying to add availability without linked user
       throw new ForbiddenOperationException();
    }


    public void deleteById(long id) {
        boolean exists = this.availabilityRepository.existsById(id);
        if (!exists) {
            throw new NoSuchAvailabilityException(id);
        }

        this.availabilityRepository.deleteById(id);
    }
}
