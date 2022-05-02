package ru.az.secu.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.az.secu.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAll();

    Optional<User> findByActivationCode(String code);

}
