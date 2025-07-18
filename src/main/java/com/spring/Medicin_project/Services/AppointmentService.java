package com.spring.Medicin_project.Services;

import com.spring.Medicin_project.DTO.AppointmentDTO;
import com.spring.Medicin_project.DTO.CreateAppointmentRequest;
import com.spring.Medicin_project.Enum.AppointmentStatus;
import com.spring.Medicin_project.Exception.ResourceNotFoundException;
import com.spring.Medicin_project.Model.Appointment;
import com.spring.Medicin_project.Model.Availability;
import com.spring.Medicin_project.Model.Doctor;
import com.spring.Medicin_project.Model.Patient;
import com.spring.Medicin_project.Repository.AppointmentRepository;
import com.spring.Medicin_project.Repository.AvailabilityRepository;
import com.spring.Medicin_project.Repository.DoctorRepository;
import com.spring.Medicin_project.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private   Logger log = LoggerFactory.getLogger(AppointmentService.class);
    private  AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private AvailabilityRepository availabilityRepository;
    private  EmailService emailService;

    @Transactional
    public AppointmentDTO bookAppointment(CreateAppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Availability slot = availabilityRepository.findByDoctorIdAndSlotTimeAndIsBooked(doctor.getId(), request.getAppointmentTime(), false).orElseThrow(() -> new ResourceNotFoundException("Slot not available"));

        slot.setBooked(true);
        availabilityRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        try {
            emailService.sendBookingConfirmationEmail(savedAppointment);
        } catch (Exception e) {
            log.error("Failed to send confirmation email for appointment id: {}", savedAppointment.getId(), e);
        }
        return convertToDTO(savedAppointment);
    }

    // ... Other service methods like cancelAppointment and getPatientHistory
    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getName());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getName());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }
}