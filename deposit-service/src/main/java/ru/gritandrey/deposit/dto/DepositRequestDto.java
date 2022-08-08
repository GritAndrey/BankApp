package ru.gritandrey.deposit.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DepositRequestDto {

    private Long accountId;

    private BigDecimal amount;

    private Long billId;

    private String email;
}
