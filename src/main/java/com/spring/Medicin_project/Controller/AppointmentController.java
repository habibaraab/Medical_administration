package com.spring.Medicin_project.Controller;


import com.spring.Medicin_project.DTO.AppointmentDTO;
import com.spring.Medicin_project.DTO.CreateAppointmentRequest;
import com.spring.Medicin_project.Services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private  AppointmentService appointmentService;
    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(request));
    }
}