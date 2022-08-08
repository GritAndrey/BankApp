package ru.gritandrey.bill.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.bill.entity.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {
}
