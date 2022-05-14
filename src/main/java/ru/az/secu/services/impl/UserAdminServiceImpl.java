package ru.az.secu.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.az.secu.model.User;
import ru.az.secu.repositories.UserRepo;
import ru.az.secu.services.MyException;
import ru.az.secu.services.NotFoundException;
import ru.az.secu.services.UserAdminService;
import ru.az.secu.services.UserService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Service
@PreAuthorize("hasAuthority('admin:write')")
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserAdminServiceImpl(UserRepo userRepo, UserService userService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        return userRepo.save(user);
    }

    @Override
    public User update(User user) throws MyException {
        User userFromDb = findById(user.getId());
        BeanUtils.copyProperties(user,userFromDb,"id", "created", "username", "password");
        return userService.update(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Long id) throws MyException {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public long count() {
        return userRepo.count();
    }

    @Override
    public List<User> findAll() throws MyException {
        return userRepo.findAll();
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public User changeActive(Long id) throws MyException {
        User userFromDb = findById(id);
        userFromDb.setActive(!userFromDb.getActive());
        return userRepo.save(userFromDb);
    }


}
