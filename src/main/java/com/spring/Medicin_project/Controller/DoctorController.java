package com.spring.Medicin_project.Controller;

import com.spring.Medicin_project.DTO.CreateAvailabilityRequest;
import com.spring.Medicin_project.Services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    @PostMapping("/{doctorId}/availability")
    public ResponseEntity<Void> addDoctorAvailability(@PathVariable Long doctorId, @Valid @RequestBody CreateAvailabilityRequest request) {
        doctorService.addAvailability(doctorId, request.getSlots());
        return ResponseEntity.ok().build();
    }
}