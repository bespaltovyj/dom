package com.rnd.store;

import com.rnd.model.data.Account;
import com.rnd.model.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountStore {

    private final AccountRepository accountRepository;

    public AccountDto createAccount(AccountDto dto) {
        Account account = new Account();
        return toDto(accountRepository.save(account));
    }

    public AccountDto updateAccount(AccountDto dto) {
        return accountRepository.findById(dto.getId())
                .map(AccountStore::toDto)
                .orElseThrow(RuntimeException::new);
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
        return dto;
    }
}
