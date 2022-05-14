package ru.az.secu.services;

import ru.az.secu.dto.LoginPasswordDto;
import ru.az.secu.model.User;

public interface UserService extends CrudService<User>{

    User findByUsername(String username);

    User adminUpdate(User user) throws MyException;

    User changePassword(User user, String password) throws MyException;

    User activation(String code) throws NotFoundException;



}
