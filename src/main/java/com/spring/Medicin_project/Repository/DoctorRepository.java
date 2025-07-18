package com.spring.Medicin_project.Repository;

import com.spring.Medicin_project.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
