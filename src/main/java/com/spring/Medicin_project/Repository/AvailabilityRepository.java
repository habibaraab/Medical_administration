package com.spring.Medicin_project.Repository;

import com.spring.Medicin_project.Model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    Optional<Availability> findByDoctorIdAndSlotTimeAndIsBooked(Long doctorId, LocalDateTime slotTime, boolean isBooked);
}
