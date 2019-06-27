package com.rnd.service;

import com.rnd.AccountApplication;
import com.rnd.exception.AccountAmountIsInsufficiently;
import com.rnd.exception.AccountAmountTooMuch;
import com.rnd.exception.IllegalArgumentServiceException;
import com.rnd.model.dto.AccountDto;
import com.rnd.model.dto.ExchangeAmountsRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    private ExecutorService taskExecutor = Executors.newFixedThreadPool(4);
    private UUID accountId;
    private UUID secondAccountId;

    @Before
    public void createNewAccount() {
        AccountDto dto = new AccountDto();
        dto.setAmount(100L);
        accountId = accountService.createAccount(dto).getId();

        AccountDto dto2 = new AccountDto();
        dto2.setAmount(0L);
        secondAccountId = accountService.createAccount(dto2).getId();
    }

    @Test(expected = ExecutionException.class)
    public void testModifyInThreads1() throws InterruptedException, ExecutionException {
        Callable<Object> task = Executors.callable(() -> accountService.increaseAmount(accountId, 100L));
        List<Future<Object>> results = taskExecutor.invokeAll(Arrays.asList(task, task, task, task, task, task, task, task, task));
        for (Future<Object> result : results) {
            result.get();
        }
    }

    @Test(expected = ExecutionException.class)
    public void testModifyInThreads2() throws InterruptedException, ExecutionException {
        Callable<Object> task = Executors.callable(() -> accountService.increaseAmount(accountId, 100L));
        Callable<Object> task2 = Executors.callable(() -> accountService.decreaseAmount(accountId, 10L));
        List<Future<Object>> results = taskExecutor.invokeAll(Arrays.asList(task2, task, task, task2, task, task2, task, task, task2));
        for (Future<Object> result : results) {
            result.get();
        }
    }

    @Test(expected = ExecutionException.class)
    public void testModifyInThreads3() throws InterruptedException, ExecutionException {
        ExchangeAmountsRequestDto requestDto = new ExchangeAmountsRequestDto(accountId, secondAccountId, 1L);
        Callable<Object> task = Executors.callable(() -> accountService.exchangeAmount(requestDto));
        List<Future<Object>> results = taskExecutor.invokeAll(Arrays.asList(task, task, task, task, task, task, task, task, task));
        for (Future<Object> result : results) {
            result.get();
        }
    }


    @Test
    public void testIncrease1() {
        accountService.increaseAmount(accountId, 100L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(200L, resultAmount);
    }

    @Test
    public void testIncrease2() {
        accountService.increaseAmount(accountId, 100L);
        accountService.increaseAmount(accountId, 50L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(250L, resultAmount);
    }

    @Test
    public void testIncrease3() {
        accountService.increaseAmount(accountId, 0L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(100L, resultAmount);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testIncrease4() {
        accountService.increaseAmount(null, 10L);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testIncrease5() {
        accountService.increaseAmount(accountId, null);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testIncrease6() {
        accountService.increaseAmount(accountId, -10L);
    }

    @Test(expected = AccountAmountTooMuch.class)
    public void testIncrease7() {
        accountService.increaseAmount(accountId, Long.MAX_VALUE);
    }

    @Test
    public void testDecrease1() {
        accountService.decreaseAmount(accountId, 100L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(0L, resultAmount);
    }

    @Test
    public void testDecrease2() {
        accountService.decreaseAmount(accountId, 0L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(100L, resultAmount);
    }

    @Test
    public void testDecrease3() {
        accountService.decreaseAmount(accountId, 20L);
        accountService.decreaseAmount(accountId, 30L);
        long resultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(50L, resultAmount);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testDecrease4() {
        accountService.decreaseAmount(null, 10L);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testDecrease5() {
        accountService.decreaseAmount(accountId, null);
    }

    @Test(expected = IllegalArgumentServiceException.class)
    public void testDecrease6() {
        accountService.decreaseAmount(accountId, -10L);
    }

    @Test(expected = AccountAmountIsInsufficiently.class)
    public void testDecrease7() {
        accountService.decreaseAmount(accountId, 5000L);
    }

    @Test
    public void testExchange1() {
        accountService.exchangeAmount(new ExchangeAmountsRequestDto(accountId, secondAccountId, 100L));
        long fromResultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(0L, fromResultAmount);
        long toResultAmount = accountService.findAccount(secondAccountId).getAmount();
        assertEquals(100L, toResultAmount);
    }

    @Test
    public void testExchange2() {
        accountService.exchangeAmount(new ExchangeAmountsRequestDto(accountId, secondAccountId, 0L));
        long fromResultAmount = accountService.findAccount(accountId).getAmount();
        assertEquals(100L, fromResultAmount);
        long toResultAmount = accountService.findAccount(secondAccountId).getAmount();
        assertEquals(0L, toResultAmount);
    }

    @Test(expected = AccountAmountIsInsufficiently.class)
    public void testExchange3() {
        accountService.exchangeAmount(new ExchangeAmountsRequestDto(accountId, secondAccountId, 500L));
    }

    @Test(expected = AccountAmountTooMuch.class)
    public void testExchange4() {
        AccountDto dto = new AccountDto();
        dto.setAmount(Long.MAX_VALUE);
        AccountDto createdDto = accountService.createAccount(dto);
        accountService.exchangeAmount(new ExchangeAmountsRequestDto(accountId, createdDto.getId(), 50L));
    }
}