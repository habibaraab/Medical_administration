package com.spring.Medicin_project.Services;


import com.spring.Medicin_project.Exception.ResourceNotFoundException;
import com.spring.Medicin_project.Model.Availability;
import com.spring.Medicin_project.Model.Doctor;
import com.spring.Medicin_project.Repository.AvailabilityRepository;
import com.spring.Medicin_project.Repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private DoctorRepository doctorRepository;
    private AvailabilityRepository availabilityRepository;

    @Transactional
    public void addAvailability(Long doctorId, List<LocalDateTime> slots) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        List<Availability> availabilityList = slots.stream().map(slotTime -> {
            Availability availability = new Availability();
            availability.setDoctor(doctor);
            availability.setSlotTime(slotTime);
            availability.setBooked(false);
            return availability;
        }).collect(Collectors.toList());
        availabilityRepository.saveAll(availabilityList);
    }


}
