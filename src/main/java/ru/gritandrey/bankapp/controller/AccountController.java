package ru.gritandrey.bankapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gritandrey.bankapp.controller.dto.AccountRequestDto;
import ru.gritandrey.bankapp.controller.dto.AccountResponseDto;
import ru.gritandrey.bankapp.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.createAccount(accountRequestDto);
    }

    @GetMapping(value = "/accounts/{id}")
    public AccountResponseDto getAccount(@PathVariable Long id) {
        return accountService.findById(id);
    }
}
