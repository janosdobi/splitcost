package home.dj.splitcost.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.dj.splitcost.constants.LogMessage;
import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.Debt;
import home.dj.splitcost.entities.User;
import home.dj.splitcost.entities.dto.CostWithDebtorsDTO;
import home.dj.splitcost.entities.dto.UpdateDebtsDTO;
import home.dj.splitcost.repositories.DebtRepository;

@Service
public class DebtService {

	private static final Log LOG = LogFactory.getLog(DebtService.class);
	
	@Autowired
	DebtRepository debtRepository;

	@Autowired
	UserService userService;

	@Autowired
	CostService costService;

	public void convertCost(final CostWithDebtorsDTO costWithDebtorsDTO) {
		if (costWithDebtorsDTO != null) {
			final Collection<User> debtors = costWithDebtorsDTO.getSelectedUsers();
			final Cost cost = costWithDebtorsDTO.getCost();
			if (cost != null && !debtors.isEmpty()) {
				final int partsToDivide = incrementDebtorCountWithPayingUser(debtors.size());
				final double debtAmount = calculateDebtAmount(cost.getAmount(), partsToDivide);
				createDebts(debtors, debtAmount, cost, costWithDebtorsDTO.isSplitEqually());
				LOG.info(String.format(LogMessage.COST_CONVERTED, cost));
			} else {
				LOG.error(LogMessage.COST_NULL);
			}
		} else {
			LOG.error(LogMessage.DTO_NULL);
		}
	}

	private double calculateDebtAmount(final double costAmount, final int partsToDivide) {
		double res = 0D;
		if (partsToDivide != 0) {
			res = costAmount / partsToDivide;
		}
		return res;
	}

	private int incrementDebtorCountWithPayingUser(final int debtorCount) {
		return debtorCount + 1;
	}

	private void createDebts(final Collection<User> debtors, final double debtAmount, final Cost cost,
			final boolean equalSplit) {
		for (final User user : debtors) {
			final Debt debt = new Debt();
			if (equalSplit) {
				debt.setAmount(debtAmount);
			}
			debt.setCost(cost);
			debt.setDebtor(user);
			debtRepository.save(debt);
			LOG.info(String.format(LogMessage.DEBT_CREATED, debt));
		}
	}

	public void settleDebt(final User actualUser, final User userToSettleWith) {
		final Collection<Debt> debts = userService.getAllDebtsBetweenUsers(actualUser, userToSettleWith.getId());
		debtRepository.deleteAll(debts);
		LOG.info(String.format(LogMessage.BALANCE_SETTLED, actualUser, userToSettleWith));
	}

	public boolean updateDebtAllocation(final UpdateDebtsDTO updateDebtsDTO) {
		boolean res = false;
		if (updateDebtsDTO != null && !updateDebtsDTO.getDebts().isEmpty() && costAmountCheck(updateDebtsDTO)) {
			for (final Debt debt : updateDebtsDTO.getDebts()) {
				debtRepository.save(debt);
				LOG.info(String.format(LogMessage.DEBT_UPDATED, debt));
				res = true;
			}
		}
		return res;
	}

	private boolean costAmountCheck(final UpdateDebtsDTO updateDebtsDTO) {
		boolean res = false;
		final List<Debt> debts = updateDebtsDTO.getDebts();
		if (debts.get(0) != null) {
			final double costAmt = debts.get(0).getCost() == null ? 0D : debts.get(0).getCost().getAmount();
			double calculator = updateDebtsDTO.getSelfAllocated();
			for (final Debt debt : debts) {
				calculator += debt.getAmount();
			}
			if (Double.valueOf(0D).equals(costAmt) || Double.valueOf(0D).equals(calculator)) {
				res = false;
			} else if (Double.valueOf(costAmt).equals(calculator)) {
				res = true;
			}
		}
		return res;
	}

	public List<Debt> getLatestDebtsCreatedByUser(final String email) {
		final List<Debt> res = new ArrayList<>();
		final Cost latestCost = costService.getLatestCostCreatedByUser(email);
		res.addAll(debtRepository.findAllByCost(latestCost));
		return res;
	}
}
