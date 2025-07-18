package com.spring.Medicin_project.Services;


import com.spring.Medicin_project.Model.Appointment;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private String fromEmail;

    public void sendBookingConfirmationEmail(Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(appointment.getPatient().getUser().getEmail());
        message.setSubject("Confirmation of your appointment booking");
        message.setText(String.format("Dear %s,\n\nYour appointment with Dr. %s has been booked for %s.",
                appointment.getPatient().getName(), appointment.getDoctor().getName(), appointment.getAppointmentTime().toString()));
        mailSender.send(message);
    }

    public void sendAppointmentReminderEmail(Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(appointment.getPatient().getUser().getEmail());
        message.setSubject("Reminder: Your appointment is soon");
        message.setText(String.format("Dear %s,\n\nThis is a reminder for your appointment with Dr. %s scheduled for %s.",
                appointment.getPatient().getName(), appointment.getDoctor().getName(), appointment.getAppointmentTime().toString()));
        mailSender.send(message);
    }
}