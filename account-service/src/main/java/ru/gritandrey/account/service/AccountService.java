package ru.gritandrey.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gritandrey.account.dto.AccountCreateDto;
import ru.gritandrey.account.dto.AccountResponseDto;
import ru.gritandrey.account.entity.Account;
import ru.gritandrey.account.exception.AccountNotFoundException;
import ru.gritandrey.account.mapper.AccountCreateMapper;
import ru.gritandrey.account.mapper.AccountReadMapper;
import ru.gritandrey.account.mapper.Mapper;
import ru.gritandrey.account.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountReadMapper accountReadMapper;
    private final AccountCreateMapper accountCreateMapper;


    public AccountResponseDto getById(Long id) {
        return getById(id, accountReadMapper);
    }

    public <T> T getById(Long id, Mapper<Account, T> mapper) {
        return accountRepository.findById(id)
                .map(mapper::mapFrom)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    public Long create(AccountCreateDto accountCreateDto) {
        return accountRepository.save(accountCreateMapper.mapFrom(accountCreateDto)).getAccountId();
    }

    public AccountResponseDto update(AccountCreateDto accountCreateDto, Long id) {
        final var account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        account.setBills(accountCreateDto.getBills());
        account.setEmail(accountCreateDto.getEmail());
        account.setPhone(accountCreateDto.getPhone());
        account.setName(accountCreateDto.getName());
        return accountReadMapper.mapFrom(account);
    }

    public AccountResponseDto delete(Long id) {
        final var account = getById(id);
        accountRepository.deleteById(id);
        return account;
    }
}
