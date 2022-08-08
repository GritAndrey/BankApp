package ru.gritandrey.deposit.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponseDto {
    private Long accountId;
    private String name;
    private String phone;
    private String email;
    private LocalDate creationDate;
    private List<Long> bills = new ArrayList<>();
}
