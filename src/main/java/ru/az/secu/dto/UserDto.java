package ru.az.secu.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import ru.az.secu.model.Role;
import ru.az.secu.model.User;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String lastName;
    private String firstName;
    private String middleName;
    private String role;

    public static UserDto create(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRole(user.getRole().name());
        userDto.setId(user.getId());
        return userDto;
    }

    public static User create(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(userDto.getId());
        if (userDto.getRole() != null) {
            Role role = Role.valueOf(userDto.getRole());
            user.setRole(role);
        }
        return user;
    }

}
