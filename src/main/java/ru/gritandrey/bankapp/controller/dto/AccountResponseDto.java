package ru.gritandrey.bankapp.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountResponseDto {
    Long accountId;
    String name;
    String email;
    Integer bill;
}
