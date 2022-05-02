package ru.az.secu.api;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.az.secu.dto.LoginPasswordDto;
import ru.az.secu.dto.UserDto;
import ru.az.secu.model.User;
import ru.az.secu.security.JwtUser;
import ru.az.secu.services.MyException;
import ru.az.secu.services.NotFoundException;
import ru.az.secu.services.UserService;

@RestController
@RequestMapping("api/users")
@CrossOrigin("*")
@PreAuthorize("hasAuthority('user:write')")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getById(
            @RequestParam Long id,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
    ) throws MyException {
        User authUser = ((JwtUser) usernamePasswordAuthenticationToken.getPrincipal()).getUser();
        if (!authUser.getId().equals(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(UserDto.create(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> update(
            @RequestBody UserDto userDto,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
    ) throws MyException {
        User authUser = ((JwtUser) usernamePasswordAuthenticationToken.getPrincipal()).getUser();
        if (!authUser.getId().equals(userDto.getId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        UserDto userDto1 = UserDto.create(userService.update(UserDto.create(userDto)));
        return ResponseEntity.ok(userDto1);
    }

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody LoginPasswordDto loginPasswordDto,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
    ) throws MyException {
        User authUser = ((JwtUser) usernamePasswordAuthenticationToken.getPrincipal()).getUser();
        userService.changePassword(authUser, loginPasswordDto.getPassword());
        return ResponseEntity.ok("Password changed");
    }



}
