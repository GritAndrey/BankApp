package ru.gritandrey.account.dto;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class AccountCreateDto {
    String name;
    String phone;
    String email;
    @Builder.Default
    List<Long> bills = new ArrayList<>();
}
