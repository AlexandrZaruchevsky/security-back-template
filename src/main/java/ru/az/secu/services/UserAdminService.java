package ru.az.secu.services;

import ru.az.secu.model.User;

public interface UserAdminService extends CrudService<User> {

    long count();

}
