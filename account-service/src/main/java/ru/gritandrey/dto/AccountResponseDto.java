package ru.gritandrey.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class AccountResponseDto {
    Long accountId;
    String name;
    String phone;
    String email;
    LocalDate creationDate;
    @Builder.Default
    List<Long> bills = new ArrayList<>();
}
