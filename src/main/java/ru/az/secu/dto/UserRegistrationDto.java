package ru.az.secu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import ru.az.secu.model.User;

@Data
public class UserRegistrationDto {

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    public static User create(UserRegistrationDto userRegistrationDto){
        User user = new User();
        BeanUtils.copyProperties(userRegistrationDto, user);
        return user;
    }

}
