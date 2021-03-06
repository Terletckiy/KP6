package by.TerleckiyKrepets.project.service;

import by.TerleckiyKrepets.project.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<Account>{
    Optional<Account> findAccountByUsername(String username);
    List<String> changePassword(Account account);
    void updateAccount(Account account);
}
