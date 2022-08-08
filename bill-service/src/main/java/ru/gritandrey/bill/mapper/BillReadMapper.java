package ru.gritandrey.bill.mapper;

import org.springframework.stereotype.Component;
import ru.gritandrey.bill.dto.BillResponseDto;
import ru.gritandrey.bill.entity.Bill;
@Component
public class BillReadMapper implements Mapper<Bill, BillResponseDto> {
    @Override
    public BillResponseDto mapFrom(Bill object) {
        return BillResponseDto.builder()
                .id(object.getId())
                .accountId(object.getAccountId())
                .amount(object.getAmount())
                .creationDate(object.getCreationDate())
                .isDefault(object.getIsDefault())
                .overdraftEnabled(object.getOverdraftEnabled())
                .build();
    }
}
