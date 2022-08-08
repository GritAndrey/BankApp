package ru.gritandrey.notification.service;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Getter
@Setter
public class DepositResponseDto {
    BigDecimal amount;
    String email;

}
