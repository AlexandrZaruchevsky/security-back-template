package ru.az.secu.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.az.secu.model.User;
import ru.az.secu.repositories.UserRepo;
import ru.az.secu.services.BanOperationException;
import ru.az.secu.services.MyException;
import ru.az.secu.services.NotFoundException;
import ru.az.secu.services.UserService;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('admin:write')")
    public User adminUpdate(User user) throws MyException {
        User userFromDb = findById(user.getId());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setMiddleName(user.getMiddleName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setRole(user.getRole());
        return userRepo.save(userFromDb);
    }

    @Override
    public User changePassword(User user, String password) throws MyException {
        User userFromDb = findById(user.getId());
        userFromDb.setPassword(passwordEncoder.encode(password));
        return userRepo.save(userFromDb);
    }

    @Override
    public User activation(String code) throws NotFoundException {
        User userFromDb = userRepo.findByActivationCode(code.trim())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        userFromDb.setActivationCode(null);
        userFromDb.setActive(true);
        return userRepo.save(userFromDb);
    }

    @Override
    public User add(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) throws MyException {
        User userFromDb = findById(user.getId());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setMiddleName(user.getMiddleName());
        userFromDb.setEmail(user.getEmail());
        return userRepo.save(userFromDb);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) throws MyException {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public long count() {
        return userRepo.count();
    }

    @Override
    public List<User> findAll() throws MyException {
        throw new BanOperationException(HttpStatus.BAD_REQUEST, "Operation banned");
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return null;
    }
}
