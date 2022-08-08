package ru.gritandrey.deposit.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BillRequestDto {
    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private LocalDate creationDate;

    private Boolean overdraftEnabled;
}
