package kz.jumpkz.jumpapp.repository;

import kz.jumpkz.jumpapp.domain.StatisticHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StatisticHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticHistoryRepository extends JpaRepository<StatisticHistory, Long> {}
