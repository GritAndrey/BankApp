package ru.gritandrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gritandrey.dto.AccountCreateDto;
import ru.gritandrey.dto.AccountResponseDto;
import ru.gritandrey.entity.Account;
import ru.gritandrey.exception.AccountNotFoundException;
import ru.gritandrey.mapper.AccountCreateMapper;
import ru.gritandrey.mapper.AccountReadMapper;
import ru.gritandrey.mapper.Mapper;
import ru.gritandrey.repository.AccountRepository;

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
