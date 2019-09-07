package home.dj.splitcost.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.Debt;

public interface DebtRepository extends JpaRepository<Debt, Long> {

	Set<Debt> findAllByCost(final Cost cost);
}
