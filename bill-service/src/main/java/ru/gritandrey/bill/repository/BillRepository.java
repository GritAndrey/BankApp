package ru.gritandrey.bill.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.bill.entity.Bill;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {

    List<Bill> findByAccountId(Long accountId);
}
