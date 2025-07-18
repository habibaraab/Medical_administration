package com.spring.Medicin_project.Repository;

import com.spring.Medicin_project.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
