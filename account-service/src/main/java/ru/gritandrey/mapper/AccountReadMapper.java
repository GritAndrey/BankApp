package ru.gritandrey.mapper;

import ru.gritandrey.dto.AccountResponseDto;
import ru.gritandrey.entity.Account;

public class AccountReadMapper implements Mapper<Account, AccountResponseDto> {
    @Override
    public AccountResponseDto mapFrom(Account object) {
        return AccountResponseDto.builder()
                .accountId(object.getAccountId())
                .name(object.getName())
                .email(object.getEmail())
                .phone(object.getPhone())
                .creationDate(object.getCreationDate())
                .build();
    }
}
