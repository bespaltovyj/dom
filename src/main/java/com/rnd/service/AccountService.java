package com.rnd.service;

import com.rnd.model.dto.AccountDto;
import com.rnd.store.AccountStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Sort DEFAULT_ID_SORTABLE = Sort.by("id");

    private final AccountStore accountStore;

    public List<AccountDto> findAccounts(int page, int limit) {
        return accountStore.findPageable(PageRequest.of(page, limit, DEFAULT_ID_SORTABLE));
    }

    public AccountDto findAccount(UUID id) {
        return accountStore.findById(id).orElseThrow(RuntimeException::new);
    }

    public AccountDto createAccount(AccountDto dto) {
        return accountStore.createAccount(dto);
    }

    public AccountDto updateAccount(UUID id, AccountDto dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw new RuntimeException();
        }
        return accountStore.updateAccount(dto);
    }
}
