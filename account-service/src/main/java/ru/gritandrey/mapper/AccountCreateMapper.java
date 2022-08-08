package ru.gritandrey.mapper;

import ru.gritandrey.dto.AccountCreateDto;
import ru.gritandrey.entity.Account;

import java.time.LocalDate;

public class AccountCreateMapper implements Mapper<AccountCreateDto, Account> {
    @Override
    public Account mapFrom(AccountCreateDto object) {
        return Account.builder()
                .name(object.getName())
                .email(object.getEmail())
                .phone(object.getPhone())
                .creationDate(LocalDate.now())
                .bills(object.getBills())
                .build();
    }
}
