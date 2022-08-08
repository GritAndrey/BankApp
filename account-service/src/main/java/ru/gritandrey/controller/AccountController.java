package ru.gritandrey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gritandrey.dto.AccountCreateDto;
import ru.gritandrey.dto.AccountResponseDto;
import ru.gritandrey.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountResponseDto get(@PathVariable long accountId) {
        return accountService.getById(accountId);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long create(@RequestBody AccountCreateDto accountCreateDto) {
        return accountService.create(accountCreateDto);
    }

    @PutMapping(value = "/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponseDto update(@PathVariable long accountId, @RequestBody AccountCreateDto accountCreateDto) {
        return accountService.update(accountCreateDto, accountId);
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDto delete(@PathVariable Long accountId) {
        return accountService.delete(accountId);
    }
}
