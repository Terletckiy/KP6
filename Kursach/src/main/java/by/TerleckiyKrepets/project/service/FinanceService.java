package by.TerleckiyKrepets.project.service;

import by.TerleckiyKrepets.project.entity.Finance;
import by.TerleckiyKrepets.project.entity.FinanceHistory;
import by.TerleckiyKrepets.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FinanceService extends BaseService<Finance>{
    void updateFinance(Finance finance);
    void transaction(FinanceHistory financeHistory, Long id);
    double userSalary(Finance finance);
    Optional<Finance> findUserFinance(Long id);
    Optional<User> findUserFinanceByUsername(String username);
    Page<FinanceHistory> findAllFinanceHistory(Pageable pageable);
    Page<FinanceHistory> findUserFinanceHistoryById(Long id, Pageable pageable);
    Page<User> findAllUserFinance(Pageable pageable);
}
