package ru.az.secu.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.az.secu.dto.AuthDto;
import ru.az.secu.dto.LoginPasswordDto;
import ru.az.secu.dto.UserDto;
import ru.az.secu.dto.UserRegistrationDto;
import ru.az.secu.model.Role;
import ru.az.secu.model.User;
import ru.az.secu.security.JwtAuthenticationException;
import ru.az.secu.security.JwtUtil;
import ru.az.secu.services.NotFoundException;
import ru.az.secu.services.UserService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin("*")
public class AuthRestController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthRestController(
            JwtUtil jwtUtil,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("validate")
    public ResponseEntity<AuthDto> validateToken(
            @RequestParam String token
    ) throws JwtAuthenticationException {
        if(jwtUtil.validateToken(token.trim())){
            String username = jwtUtil.getUsername(token);
            User userFromDb = userService.findByUsername(username);
            AuthDto authDto = new AuthDto();
            authDto.setToken(token);
            authDto.setUserDto(UserDto.create(userFromDb));
            return ResponseEntity.ok(authDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("login")
    public ResponseEntity<AuthDto> getToken(
            @RequestBody LoginPasswordDto loginPasswordDto
    ) {
        User userFomDb = userService.findByUsername(loginPasswordDto.getUsername());
        if (userFomDb != null && passwordEncoder.matches(loginPasswordDto.getPassword(), userFomDb.getPassword())) {
            String token = jwtUtil.createToken(userFomDb.getUsername(), userFomDb.getRole().name());
            AuthDto authDto = new AuthDto();
            authDto.setToken(token);
            authDto.setUserDto(UserDto.create(userFomDb));
            return ResponseEntity.status(HttpStatus.OK).body(authDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("registration")
    public ResponseEntity<UserDto> registration(
            @RequestBody UserRegistrationDto userRegistrationDto
    ) {
        User user = UserRegistrationDto.create(userRegistrationDto);
        user.setActivationCode(UUID.randomUUID().toString());
//        user.setActivationCode(UUID.nameUUIDFromBytes(userRegistrationDto.getUsername().getBytes()).toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return ResponseEntity.ok(UserDto.create(userService.add(user)));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> errorHandler(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getLocalizedMessage());
    }

    @GetMapping("logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @GetMapping("activation/{code}")
    public void activation(
            @PathVariable String code
    ) throws NotFoundException {
        User user = userService.activation(code);
        System.out.println(user);
    }

}
