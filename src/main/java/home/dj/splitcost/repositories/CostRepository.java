package home.dj.splitcost.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.User;

public interface CostRepository extends JpaRepository<Cost, Long> {

	Set<Cost> findAllByOwner(final User owner);
}
