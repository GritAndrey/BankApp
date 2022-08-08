package ru.gritandrey.account.mapper;

import org.springframework.stereotype.Component;
import ru.gritandrey.account.dto.AccountCreateDto;
import ru.gritandrey.account.entity.Account;

import java.time.LocalDate;
@Component
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
