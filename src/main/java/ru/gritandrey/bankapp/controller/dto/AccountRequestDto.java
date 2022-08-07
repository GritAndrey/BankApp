package ru.gritandrey.bankapp.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountRequestDto {
    String name;
    String email;
    Integer bill;
}
