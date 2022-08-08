package ru.gritandrey.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gritandrey.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
