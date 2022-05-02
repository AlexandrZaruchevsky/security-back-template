package ru.az.secu.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.az.secu.model.Role;
import ru.az.secu.model.User;
import ru.az.secu.services.UserService;
import ru.az.secu.services.UtilService;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    @Value("${init.username}")
    private String username;
    @Value("${init.password}")
    private String password;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UtilServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addAdmin() {
        if (userService.count() > 0) return;
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ADMIN);
        user.setActive(true);
        user.setActivationCode(UUID.nameUUIDFromBytes("admin".getBytes()).toString());
        userService.add(user);
    }

}
