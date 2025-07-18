package com.spring.Medicin_project.Services;


import com.spring.Medicin_project.Enum.AppointmentStatus;
import com.spring.Medicin_project.Model.Appointment;
import com.spring.Medicin_project.Repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTasksService {
    private Logger log = LoggerFactory.getLogger(ScheduledTasksService.class);
    private  AppointmentRepository appointmentRepository;
    private  EmailService emailService;

    @Scheduled(cron = "0 0 * * * *")
    public void sendAppointmentReminders() {
        log.info("Running scheduled task to send appointment reminders...");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);
        List<Appointment> upcomingAppointments = appointmentRepository.findAllByStatusAndAppointmentTimeBetween(AppointmentStatus.SCHEDULED, now, in24Hours);
        upcomingAppointments.forEach(emailService::sendAppointmentReminderEmail);
    }
}