package ru.gritandrey.notification.service;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponseDto {
    BigDecimal amount;
    String email;

}
