package ru.gritandrey.deposit.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.gritandrey.deposit.dto.AccountResponseDto;
import ru.gritandrey.deposit.dto.BillResponseDto;
import ru.gritandrey.deposit.dto.DepositResponseDto;
import ru.gritandrey.deposit.exception.DepositServiceException;
import ru.gritandrey.deposit.repository.DepositRepository;
import ru.gritandrey.deposit.rest.AccountServiceClient;
import ru.gritandrey.deposit.rest.BillServiceClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {
    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void depositWithBillId() {
        final var billResponseDto = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDto);
        final var accountResponseDto = createAccountResponseDto();
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(accountResponseDto);

        final var deposit = depositService.deposit(null, 1L, BigDecimal.valueOf(1000));
        assertThat(deposit.getEmail()).isEqualTo("test@test.com");
        assertThat(deposit.getAmount()).isEqualTo("1000");
    }

    @Test(expected = DepositServiceException.class)
    public void depositServiceTestException() {
        depositService.deposit(null, null, BigDecimal.valueOf(1000));
    }

    private AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(LocalDate.now());
        accountResponseDto.setEmail("test@test.com");
        accountResponseDto.setName("testName");
        accountResponseDto.setPhone("+16598432");
        return accountResponseDto;
    }
    private BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1000));
        billResponseDto.setId(1L);
        billResponseDto.setCreationDate(LocalDate.now());
        billResponseDto.setIsDefault(true);
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;
    }
}