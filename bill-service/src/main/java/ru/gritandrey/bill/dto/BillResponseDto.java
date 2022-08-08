package ru.gritandrey.bill.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
@Value
@Builder
public class BillResponseDto {
    Long id;

    Long accountId;

    BigDecimal amount;

    Boolean isDefault;

    LocalDate creationDate;

    Boolean overdraftEnabled;
}
