package by.TerleckiyKrepets.project.repository;

import by.TerleckiyKrepets.project.entity.Finance;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends BaseRepository<Finance> {
    Finance findFinanceByUserId(Long id);
}
