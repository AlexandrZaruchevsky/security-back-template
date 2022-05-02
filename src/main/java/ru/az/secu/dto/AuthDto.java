package ru.az.secu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthDto {

    private String token;
    @JsonProperty("user")
    private UserDto userDto;

}
