package ru.gritandrey.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.deposit.entity.Deposit;

import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Long> {

    List<Deposit> findDepositByEmail(String email);
}
