package com.spring.Medicin_project.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateAvailabilityRequest {
    @NotEmpty(message = "Availability  cannot be empty")
    private List<LocalDateTime> slots;


}
