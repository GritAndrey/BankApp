package ru.gritandrey.bankapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gritandrey.bankapp.controller.dto.AccountRequestDto;
import ru.gritandrey.bankapp.controller.dto.AccountResponseDto;
import ru.gritandrey.bankapp.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.createAccount(accountRequestDto);
    }

    @GetMapping()
    public List<AccountResponseDto> getAll() {
        return accountService.getAll();
    }

    @GetMapping(value = "{id}")
    public AccountResponseDto getAccount(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AccountResponseDto deleteAccount(@PathVariable Long id) {
        return accountService.delete(id);
    }

}
