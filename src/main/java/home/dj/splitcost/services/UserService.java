package home.dj.splitcost.services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import home.dj.splitcost.constants.LogMessage;
import home.dj.splitcost.entities.Debt;
import home.dj.splitcost.entities.Role;
import home.dj.splitcost.entities.Role.RoleConstant;
import home.dj.splitcost.entities.User;
import home.dj.splitcost.repositories.RoleRepository;
import home.dj.splitcost.repositories.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final Log LOG = LogFactory.getLog(UserService.class);

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(final User user) {
		saveUser(user, Role.RoleConstant.DEFAULT);
	}

	public void saveUser(final User user, final RoleConstant roleConstant) {
		if (user != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			final Role role = roleRepository.findByRole(roleConstant.name());
			user.setRoles(Collections.singleton(role));
			userRepository.save(user);
			LOG.info(String.format(LogMessage.USER_ADDED, user.getEmail()));
		} else {
			LOG.error(LogMessage.USER_NULL);
		}
	}

	public Set<User> getAllUsers() {
		final Set<User> res = new HashSet<>();
		res.addAll(userRepository.findAll());
		return res;
	}

	public Set<User> getAllUsersExcept(final User user) {
		final Set<User> res = new HashSet<>();
		if (user != null) {
			res.addAll(userRepository.findAll());
			res.remove(user);
		} else {
			LOG.error(LogMessage.USER_NULL);
		}
		return res;
	}

	public Map<User, Double> getBalancesForUser(final User user) {
		Map<User, Double> balances = new HashMap<>();

		if (user != null) {
			final Collection<Debt> debts = user.getDebts();
			final Collection<Debt> claims = user.getCosts().stream().flatMap(cost -> cost.getDebts().stream())
					.collect(Collectors.toSet());

			if (debts != null && !debts.isEmpty()) {
				addDebtsToBalanceMap(debts, balances);
			}

			if (claims != null && !claims.isEmpty()) {
				addClaimsToBalanceMap(user, claims, balances);
			}

			autoSettleZeroBalances(user, balances);

			LOG.info(String.format(LogMessage.BALANCE_CALCULATED, user));

		} else {
			LOG.error(LogMessage.USER_NULL);
		}
		return balances;
	}

	private void autoSettleZeroBalances(final User user, final Map<User, Double> balances) {
		if (!balances.isEmpty()) {
			final Iterator<Map.Entry<User, Double>> mapIterator = balances.entrySet().iterator();
			while (mapIterator.hasNext()) {
				final Map.Entry<User, Double> entry = mapIterator.next();
				if (Double.valueOf(0D).equals(entry.getValue())) {
					mapIterator.remove();
				}
			}
		}
	}

	private void addDebtsToBalanceMap(final Collection<Debt> debts, final Map<User, Double> balances) {
		for (final Debt debt : debts) {
			if (debt.getCost() != null) {
				final User owner = debt.getCost().getOwner();
				final Double amount = debt.getAmount();
				if (owner != null && amount != null) {
					if (!balances.containsKey(owner)) {
						balances.put(owner, -amount);
					} else {
						final double prevAmt = balances.get(owner);
						balances.put(owner, prevAmt - amount);
					}
				} else {
					LOG.error(String.format(LogMessage.DEBT_DATA_ERROR, debt.getId()));
				}
			}
		}
	}

	private void addClaimsToBalanceMap(final User user, final Collection<Debt> claims,
			final Map<User, Double> balances) {
		for (final Debt debt : claims) {
			final User debtor = debt.getDebtor();
			final Double amount = debt.getAmount();
			if (debtor != null && amount != null) {
				if (!balances.containsKey(debtor)) {
					balances.put(debtor, amount);
				} else {
					final double prevAmt = balances.get(debtor);
					balances.put(debtor, prevAmt + amount);
				}
			} else {
				LOG.error(String.format(LogMessage.DEBT_DATA_ERROR, debt.getId()));
			}
		}
	}

	public Collection<Debt> getAllDebtsBetweenUsers(final User owner, final Long debtorId) {
		Collection<Debt> res = new HashSet<>();
		if (owner != null && debtorId != null) {
			res = owner.getCosts().stream().flatMap(cost -> cost.getDebts().stream())
					.filter(debt -> debtorId.equals(debt.getDebtor().getId())).collect(Collectors.toSet());

			final Collection<Debt> debts = owner.getDebts().stream()
					.filter(debt -> debtorId.equals(debt.getCost().getOwner().getId())).collect(Collectors.toSet());
			res.addAll(debts);
		}
		return res;
	}
}
