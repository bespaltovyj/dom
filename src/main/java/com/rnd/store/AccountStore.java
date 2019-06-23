package com.rnd.store;

import com.rnd.model.data.Account;
import com.rnd.model.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountStore {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountDto createAccount(AccountDto dto) {
        Account account = new Account();
        account.setAmount(dto.getAmount());
        return toDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto updateAccount(AccountDto dto) {
        Optional<Account> account = accountRepository.findById(dto.getId());
        account.ifPresent(acc -> acc.setAmount(dto.getAmount()));
        return account.map(AccountStore::toDto).orElseThrow(RuntimeException::new);
    }

    public Optional<AccountDto> findById(UUID id) {
        return accountRepository.findById(id).map(AccountStore::toDto);
    }

    public List<AccountDto> findPageable(Pageable page) {
        return accountRepository.findAll(page).stream().map(AccountStore::toDto).collect(Collectors.toList());
    }

    private static AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setAmount(account.getAmount());
        return dto;
    }
}
