package ru.gritandrey.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gritandrey.bankapp.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
