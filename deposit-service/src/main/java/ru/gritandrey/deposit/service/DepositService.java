package ru.gritandrey.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.gritandrey.deposit.dto.BillRequestDto;
import ru.gritandrey.deposit.dto.BillResponseDto;
import ru.gritandrey.deposit.dto.DepositResponseDto;
import ru.gritandrey.deposit.entity.Deposit;
import ru.gritandrey.deposit.exception.DepositServiceException;
import ru.gritandrey.deposit.repository.DepositRepository;
import ru.gritandrey.deposit.rest.AccountServiceClient;
import ru.gritandrey.deposit.rest.BillServiceClient;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DepositService {
    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";
    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public DepositResponseDto deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("accountId and billId is null");
        }
        if (billId != null) {
            final var billResponseDto = billServiceClient.getBillById(billId);
            final var billRequestDto = createBillRequest(amount, billResponseDto);
            billServiceClient.update(billId, billRequestDto);

            final var email = accountServiceClient.getAccountById(billResponseDto.getAccountId()).getEmail();
            saveDeposit(billId, amount, email);
            return createResponse(amount, email);
        }
        BillResponseDto defaultBill = getDefaultBill(accountId);
        final var billRequestDto = createBillRequest(amount, defaultBill);
        billServiceClient.update(defaultBill.getId(), billRequestDto);
        final var email = accountServiceClient.getAccountById(accountId).getEmail();
        saveDeposit(billId, amount, email);
        return createResponse(amount, email);
    }

    private void saveDeposit(Long billId, BigDecimal amount, String email) {
        depositRepository.save(Deposit.builder()
                .amount(amount)
                .billId(billId)
                .creationDate(LocalDate.now())
                .email(email)
                .build());
    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find default bill for account: " + accountId));
    }

    private DepositResponseDto createResponse(BigDecimal amount, String email) {
        DepositResponseDto depositResponseDto = new DepositResponseDto(amount, email);
        final var objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can`t send message to RabbitMQ");
        }
        return depositResponseDto;
    }

    private BillRequestDto createBillRequest(BigDecimal amount, BillResponseDto billResponseDto) {
        BillRequestDto billRequestDto = new BillRequestDto();
        billRequestDto.setAccountId(billResponseDto.getAccountId());
        billRequestDto.setCreationDate(billResponseDto.getCreationDate());
        billRequestDto.setOverdraftEnabled(billResponseDto.getOverdraftEnabled());
        billRequestDto.setIsDefault(billResponseDto.getIsDefault());
        billRequestDto.setAmount(billResponseDto.getAmount().add(amount));
        return billRequestDto;
    }
}
