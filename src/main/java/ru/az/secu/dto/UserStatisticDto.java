package ru.az.secu.dto;

import lombok.Data;

@Data
public class UserStatisticDto {

    private long count;
    private long countActive;
    private long countNotActive;


}
