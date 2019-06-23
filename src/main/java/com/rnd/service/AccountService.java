package com.rnd.service;

import com.rnd.exception.AccountAmountIsInsufficiently;
import com.rnd.exception.AccountAmountTooMuch;
import com.rnd.model.dto.AccountDto;
import com.rnd.store.AccountStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Sort DEFAULT_ID_SORTABLE = Sort.by("id");

    private final AccountStore accountStore;
    private final ValidatorService validatorService;

    public List<AccountDto> findAccounts(int page, int limit) {
        return accountStore.findPageable(PageRequest.of(page, limit, DEFAULT_ID_SORTABLE));
    }

    @Transactional
    public void increaseAmount(UUID id, long step) {
        AccountDto account = findAccount(id);
        if (step < 0) {
            throw new RuntimeException();
        }
        if (Long.MAX_VALUE - account.getAmount() < step) {
            throw new AccountAmountTooMuch();
        }
        account.setAmount(account.getAmount() + step);
        accountStore.updateAccount(account);
    }

    @Transactional
    public void decreaseAmount(UUID id, long step) {
        AccountDto account = findAccount(id);
        if (step < 0) {
            throw new RuntimeException();
        }
        if (account.getAmount() - step < 0) {
            throw new AccountAmountIsInsufficiently();
        }
        account.setAmount(account.getAmount() - step);
        accountStore.updateAccount(account);
    }

    @Transactional
    public void exchangeAmount(UUID from, UUID to, long step) {
        decreaseAmount(from, step);
        increaseAmount(to, step);
    }

    public AccountDto findAccount(UUID id) {
        return accountStore.findById(id).orElseThrow(RuntimeException::new);
    }

    public AccountDto createAccount(AccountDto dto) {
        validatorService.validate(dto);
        return accountStore.createAccount(dto);
    }

    public AccountDto updateAccount(UUID id, AccountDto dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw new RuntimeException();
        }
        validatorService.validate(dto);
        return accountStore.updateAccount(dto);
    }
}
