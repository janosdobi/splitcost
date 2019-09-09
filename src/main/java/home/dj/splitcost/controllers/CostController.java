package home.dj.splitcost.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import home.dj.splitcost.constants.UserMessage;
import home.dj.splitcost.constants.ViewName;
import home.dj.splitcost.entities.dto.CostWithDebtorsDTO;
import home.dj.splitcost.entities.dto.UpdateDebtsDTO;
import home.dj.splitcost.entities.dto.UserWrapper;
import home.dj.splitcost.services.CostService;
import home.dj.splitcost.services.DebtService;
import home.dj.splitcost.services.UserService;

@Controller
public class CostController {

	@Autowired
	UserService userService;

	@Autowired
	CostService costService;

	@Autowired
	DebtService debtService;

	@GetMapping(ViewName.MAPPING + ViewName.NEW_COST)
	public String addCost(final Principal principal, @ModelAttribute final CostWithDebtorsDTO costWithDebtorsDTO,
			final Model model) {
		final UserWrapper user = userService.findUserByEmail(principal.getName());
		model.addAttribute("allUsers", userService.getAllUsersExcept(user));
		return ViewName.NEW_COST;
	}

	@PostMapping(ViewName.MAPPING + ViewName.NEW_COST)
	public String registerCostWithDebtors(final Model model, @Valid final CostWithDebtorsDTO costWithDebtorsDTO,
			final BindingResult bindingResult) {
		String res = null;
		if (!bindingResult.hasErrors()) {
			costService.save(costWithDebtorsDTO.getCost());
			model.addAttribute(UserMessage.SUCCESS_MSG, UserMessage.COST_SAVED);
			debtService.convertCost(costWithDebtorsDTO);
		}

		if (!costWithDebtorsDTO.isSplitEqually()) {
			res = ViewName.REDIRECT + ViewName.UPDATE_ALLOCATION;
		} else {
			res = ViewName.NEW_COST;
		}

		return res;
	}

	@GetMapping(ViewName.MAPPING + ViewName.UPDATE_ALLOCATION)
	public String updateAllocation(final Model model, final Principal principal) {
		model.addAttribute(new UpdateDebtsDTO(debtService.getLatestDebtsCreatedByUser(principal.getName())));
		return ViewName.UPDATE_ALLOCATION;
	}

	@PostMapping(ViewName.MAPPING + ViewName.UPDATE_ALLOCATION)
	public String postNewAllocation(final UpdateDebtsDTO updateDebtsDTO, final Model model) {
		String res = ViewName.UPDATE_ALLOCATION;
		if (debtService.updateDebtAllocation(updateDebtsDTO)) {
			res = ViewName.REDIRECT + ViewName.DASHBOARD;
		} else {
			model.addAttribute(UserMessage.ERROR_MSG, UserMessage.ALLOCATION_ERROR);
		}
		return res;
	}
}
