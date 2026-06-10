package com.example.jobportal.auth;

import com.example.jobportal.constants.ApplicationConstants;
import com.example.jobportal.dto.LoginRequestDTO;
import com.example.jobportal.dto.LoginResponseDTO;
import com.example.jobportal.dto.RegisterRequestDTO;
import com.example.jobportal.dto.UserDto;
import com.example.jobportal.entity.JobPortalUser;
import com.example.jobportal.entity.Role;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.repository.JobPortalUserRepository;
import com.example.jobportal.repository.RoleRepository;
import com.example.jobportal.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final JobPortalUserRepository jobPortalUserRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker  compromisedPasswordChecker;

    @PostMapping(value = "/login/public", version = "1.0")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            var resultAuthentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password())
            );
            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);
            var userDTO = new UserDto();
            var loggedInUser = (JobPortalUser) resultAuthentication.getPrincipal();
            userDTO.setRole(loggedInUser.getRole().getName());
            userDTO.setUserId(loggedInUser.getId());
            BeanUtils.copyProperties(loggedInUser, userDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), userDTO, jwtToken));
        } catch (BadCredentialsException e) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        } catch (AuthenticationException e) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication Failed");
        } catch (Exception ex) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred");
        }
    }

    @PostMapping(value = "/register/public", version = "1.0")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequestDto) {
        CompromisedPasswordDecision decision = compromisedPasswordChecker
                .check(registerRequestDto.password());
        if (decision.isCompromised()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("password", "Choose a strong password"));
        }
        Optional<JobPortalUser> existingUser =  jobPortalUserRepository.readUserByEmailOrMobileNumber(
                registerRequestDto.email(),  registerRequestDto.mobileNumber());
        if (existingUser.isPresent()) {
            Map<String, String> errors = new HashMap<>();
            JobPortalUser jobPortalUser = existingUser.get();
            if (jobPortalUser.getEmail().equalsIgnoreCase(registerRequestDto.email())) {
                errors.put("email", "Email is already registered");
            }
            if (jobPortalUser.getMobileNumber().equals(registerRequestDto.mobileNumber())) {
                errors.put("mobileNumber", "Mobile number is already registered");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        JobPortalUser jobPortalUser = new JobPortalUser();
        BeanUtils.copyProperties(registerRequestDto, jobPortalUser);
        jobPortalUser.setPasswordHash(passwordEncoder.encode(registerRequestDto.password()));
        Role role = roleRepository.findRoleByName(ApplicationConstants.ROLE_JOB_SEEKER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " +
                        ApplicationConstants.ROLE_JOB_SEEKER));
        jobPortalUser.setRole(role);
        jobPortalUserRepository.save(jobPortalUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    private ResponseEntity<LoginResponseDTO> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDTO(message, null, null));
    }
}
