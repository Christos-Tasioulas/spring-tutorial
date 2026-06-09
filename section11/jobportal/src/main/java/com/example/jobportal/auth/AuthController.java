package com.example.jobportal.auth;

import com.example.jobportal.dto.LoginRequestDTO;
import com.example.jobportal.dto.LoginResponseDTO;
import com.example.jobportal.dto.UserDto;
import com.example.jobportal.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/login/public", version = "1.0")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            var resultAuthentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password())
            );
            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);
            var userDTO = new UserDto();
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

    private ResponseEntity<LoginResponseDTO> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDTO(message, null, null));
    }
}
