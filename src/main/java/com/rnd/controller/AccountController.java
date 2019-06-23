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

    @PostMapping(value = "/account/{id}/increase")
    public void increaseAmount(@PathVariable("id") UUID id, @RequestParam("step") long step) {
        accountService.increaseAmount(id, step);
    }

    @PostMapping(value = "/account/{id}/decrease")
    public void decreaseAmount(@PathVariable("id") UUID id, @RequestParam("step") long step) {
        accountService.decreaseAmount(id, step);
    }

    @PostMapping(value = "/account/exchange")
    public void exchangeAmountsBetweenAccounts(
            @RequestParam("from") UUID from,
            @RequestParam("to") UUID to,
            @RequestParam("step") long step
    ) {
        accountService.exchangeAmount(from, to, step);
    }

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
