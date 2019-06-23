package com.rnd.controller;

import com.rnd.model.dto.AccountDto;
import com.rnd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/account")
    public List<AccountDto> findAccounts(
            @RequestParam(name = "page", defaultValue = "10") int page,
            @RequestParam(name = "limit", defaultValue = "0") int limit
    ) {
        return accountService.findAccounts(page, limit);
    }

    @GetMapping(value = "/account/{id}")
    public AccountDto getAccount(@PathVariable("id") UUID id) {
        return accountService.findAccount(id);
    }

    @PostMapping(value = "/account")
    public AccountDto createAccount(@RequestBody AccountDto dto) {
        return accountService.createAccount(dto);
    }

    @PutMapping(value = "/account/{id}")
    public AccountDto updateAccount(@PathVariable("id") UUID id, @RequestBody AccountDto dto) {
        return accountService.updateAccount(id, dto);
    }
}
