package ru.az.secu.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.az.secu.services.UserService;

@Service
public class JwtUserService implements UserDetailsService {

    private final UserService userService;

    public JwtUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUser(userService.findByUsername(username));
    }

}
