package ru.gritandrey.bankapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gritandrey.bankapp.controller.dto.AccountRequestDto;
import ru.gritandrey.bankapp.controller.dto.AccountResponseDto;
import ru.gritandrey.bankapp.entity.Account;
import ru.gritandrey.bankapp.exception.AccountNotFoundException;
import ru.gritandrey.bankapp.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Long createAccount(AccountRequestDto accountRequestDto) {
        final var account = Account.builder()
                .name(accountRequestDto.getName())
                .bill(accountRequestDto.getBill())
                .email(accountRequestDto.getEmail())
                .build();
        accountRepository.save(account);
        return account.getId();
    }

    public AccountResponseDto findById(Long id) {
        return accountRepository.findById(id).map(account -> AccountResponseDto.builder()
                        .accountId(account.getId())
                        .bill(account.getBill())
                        .name(account.getName())
                        .email(account.getEmail())
                        .build())
                .orElseThrow(()->new AccountNotFoundException("Account not found with id: " + id));
    }
}
