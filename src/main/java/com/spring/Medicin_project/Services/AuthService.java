package com.spring.Medicin_project.Services;

import com.spring.Medicin_project.Config.JwtService;
import com.spring.Medicin_project.DTO.AuthRequest;
import com.spring.Medicin_project.DTO.AuthResponse;
import com.spring.Medicin_project.DTO.RegisterPatientRequest;
import com.spring.Medicin_project.Enum.Role;
import com.spring.Medicin_project.Model.Patient;
import com.spring.Medicin_project.Model.User;
import com.spring.Medicin_project.Repository.PatientRepository;
import com.spring.Medicin_project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse registerPatient(RegisterPatientRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_PATIENT);
        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setUser(savedUser);
        patientRepository.save(patient);

        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}