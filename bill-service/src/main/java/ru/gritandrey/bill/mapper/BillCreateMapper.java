package ru.gritandrey.bill.mapper;

import org.springframework.stereotype.Component;
import ru.gritandrey.bill.dto.BillRequestDto;
import ru.gritandrey.bill.entity.Bill;
@Component
public class BillCreateMapper implements Mapper<BillRequestDto, Bill> {
    @Override
    public Bill mapFrom(BillRequestDto object) {
        return Bill.builder()
                .overdraftEnabled(object.getOverdraftEnabled())
                .isDefault(object.getIsDefault())
                .accountId(object.getAccountId())
                .creationDate(object.getCreationDate())
                .amount(object.getAmount())
                .build();
    }
}
