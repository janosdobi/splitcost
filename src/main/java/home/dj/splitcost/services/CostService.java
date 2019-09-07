package home.dj.splitcost.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import home.dj.splitcost.constants.LogMessage;
import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.User;
import home.dj.splitcost.repositories.CostRepository;
import home.dj.splitcost.repositories.UserRepository;

@Service("costService")
public class CostService {

	private CostRepository costRepository;
	private UserRepository userRepository;

	private static final Log LOG = LogFactory.getLog(CostService.class);

	@Autowired
	public CostService(CostRepository costRepository, UserRepository userRepository) {
		this.costRepository = costRepository;
		this.userRepository = userRepository;
	}

	public boolean save(final Cost cost) {
		boolean res = false;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User user = userRepository.findByEmail(auth.getName());
		if (cost != null) {
			cost.setCreateTS(LocalDateTime.now());
			cost.setOwner(user);
			costRepository.save(cost);
			LOG.info(String.format(LogMessage.COST_SAVED, cost));
		} else {
			LOG.error(LogMessage.COST_NULL);
		}
		return res;
	}

	public Cost getLatestCostCreatedByUser(final String email) {
		Cost res = null;
		final User user = userRepository.findByEmail(email);
		if (user != null) {
			final Set<Cost> allCostsFromUser = costRepository.findAllByOwner(user);

			final Optional<Cost> optCost = allCostsFromUser.stream().max(Comparator.comparing(Cost::getCreateTS));

			if (optCost.isPresent()) {
				res = optCost.get();
			}
		} else {
			LOG.error(LogMessage.USER_NULL);
		}
		return res;
	}
}
