package com.spring.Medicin_project.Repository;

import com.spring.Medicin_project.Enum.AppointmentStatus;
import com.spring.Medicin_project.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findAllByStatusAndAppointmentTimeBetween(AppointmentStatus status, LocalDateTime start, LocalDateTime end);
}