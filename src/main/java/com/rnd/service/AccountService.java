package com.rnd.service;

import com.rnd.constants.ErrorMessageKey;
import com.rnd.exception.AccountAmountIsInsufficiently;
import com.rnd.exception.AccountAmountTooMuch;
import com.rnd.exception.IllegalArgumentServiceException;
import com.rnd.exception.NotFoundException;
import com.rnd.model.dto.AccountDto;
import com.rnd.model.dto.ExchangeAmountsRequestDto;
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
    public void increaseAmount(UUID id, Long step) {
        checkNotNull(id, ErrorMessageKey.DONOR_MUST_BE_PRESENT);
        checkStep(step);
        AccountDto account = findAccount(id);
        if (Long.MAX_VALUE - account.getAmount() < step) {
            throw new AccountAmountTooMuch(id);
        }
        account.setAmount(account.getAmount() + step);
        accountStore.updateAccount(account);
    }

    @Transactional
    public void decreaseAmount(UUID id, Long step) {
        checkNotNull(id, ErrorMessageKey.RECIPIENT_MUST_BE_PRESENT);
        checkStep(step);
        AccountDto account = findAccount(id);
        if (account.getAmount() - step < 0) {
            throw new AccountAmountIsInsufficiently(id);
        }
        account.setAmount(account.getAmount() - step);
        accountStore.updateAccount(account);
    }

    @Transactional
    public void exchangeAmount(ExchangeAmountsRequestDto requestDto) {
        decreaseAmount(requestDto.getFrom(), requestDto.getStep());
        increaseAmount(requestDto.getTo(), requestDto.getStep());
    }

    public AccountDto findAccount(UUID id) {
        return accountStore.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessageKey.ACCOUNT_NOT_FOUND, id));
    }

    public AccountDto createAccount(AccountDto dto) {
        validatorService.validate(dto);
        return accountStore.createAccount(dto);
    }

    public AccountDto updateAccount(UUID id, AccountDto dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw new IllegalArgumentException();
        }
        validatorService.validate(dto);
        return accountStore.updateAccount(dto);
    }

    private static void checkStep(Long step) {
        if (Objects.isNull(step) || step < 0) {
            throw new IllegalArgumentServiceException(ErrorMessageKey.STEP_MUST_POSITIVE);
        }
    }

    private static void checkNotNull(Object object, String errorKey) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentServiceException(errorKey);
        }
    }
}
