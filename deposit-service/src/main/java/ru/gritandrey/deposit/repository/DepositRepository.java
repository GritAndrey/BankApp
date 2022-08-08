package ru.gritandrey.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.deposit.entity.Deposit;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
}
