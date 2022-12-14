package ru.gritandrey.account.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.account.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
