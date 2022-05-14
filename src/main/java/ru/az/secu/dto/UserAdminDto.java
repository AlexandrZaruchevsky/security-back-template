package ru.az.secu.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ru.az.secu.model.Role;
import ru.az.secu.model.User;

@Data
public class UserAdminDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String middleName;
    private String role;
    private Boolean active;

    public static UserAdminDto create(User user) {
        UserAdminDto userDto = new UserAdminDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRole(user.getRole().name());
        userDto.setId(user.getId());
        return userDto;
    }

    public static User create(UserAdminDto userAdminDto) {
        User user = new User();
        BeanUtils.copyProperties(userAdminDto, user);
        user.setId(userAdminDto.getId());
        if (userAdminDto.getRole() != null) {
            Role role = Role.valueOf(userAdminDto.getRole());
            user.setRole(role);
        } else user.setRole(Role.USER);
        return user;
    }

}
