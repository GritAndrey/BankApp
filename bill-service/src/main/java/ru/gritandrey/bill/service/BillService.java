package ru.gritandrey.bill.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gritandrey.bill.dto.BillRequestDto;
import ru.gritandrey.bill.dto.BillResponseDto;
import ru.gritandrey.bill.entity.Bill;
import ru.gritandrey.bill.exception.BillNotFoundException;
import ru.gritandrey.bill.mapper.BillCreateMapper;
import ru.gritandrey.bill.mapper.BillReadMapper;
import ru.gritandrey.bill.repository.BillRepository;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final BillCreateMapper billCreateMapper;
    private final BillReadMapper billReadMapper;

    public BillResponseDto get(Long id) {
        return billRepository.findById(id).map(billReadMapper::mapFrom)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with id: " + id));
    }

    public Long create(BillRequestDto billRequestDto) {
        final var bill = Bill.builder()
                .accountId(billRequestDto.getAccountId())
                .amount(billRequestDto.getAmount())
                .isDefault(billRequestDto.getIsDefault())
                .creationDate(LocalDate.now())
                .overdraftEnabled(billRequestDto.getOverdraftEnabled())
                .build();
        return billRepository.save(bill).getId();
    }

    public BillResponseDto update(Long billId, BillRequestDto billRequestDto) {
        final var bill = Bill.builder()
                .id(billId)
                .accountId(billRequestDto.getAccountId())
                .amount(billRequestDto.getAmount())
                .isDefault(billRequestDto.getIsDefault())
                .overdraftEnabled(billRequestDto.getOverdraftEnabled())
                .build();
        return billReadMapper.mapFrom(billRepository.save(bill));
    }

    public BillResponseDto delete(Long billId) {
        BillResponseDto deletedBill = get(billId);
        billRepository.deleteById(billId);
        return deletedBill;
    }

    public List<BillResponseDto> getByAccountId(Long accountId) {
        return billRepository.findByAccountId(accountId).stream().map(billReadMapper::mapFrom).collect(toList());
    }
}
