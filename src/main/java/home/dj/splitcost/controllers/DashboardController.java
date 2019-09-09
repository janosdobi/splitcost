package home.dj.splitcost.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import home.dj.splitcost.constants.ViewName;
import home.dj.splitcost.entities.User;
import home.dj.splitcost.entities.dto.UserBalanceDTO;
import home.dj.splitcost.entities.dto.UserWrapper;
import home.dj.splitcost.services.DebtService;
import home.dj.splitcost.services.UserService;

@Controller
public class DashboardController {

	@Autowired
	UserService userService;

	@Autowired
	DebtService debtService;

	@GetMapping(ViewName.MAPPING + ViewName.DASHBOARD)
	public ModelAndView loginSuccessful(final Principal principal, @ModelAttribute final User user) {
		final ModelAndView mav = new ModelAndView();
		final UserBalanceDTO userBalanceDTO = setupDTO(principal);
		mav.addObject(userBalanceDTO);
		mav.setViewName(ViewName.DASHBOARD);
		return mav;
	}

	@PostMapping(ViewName.MAPPING + ViewName.DASHBOARD)
	public String settleDebt(final Principal principal, final Model model, final UserWrapper user) {
		final UserBalanceDTO userBalanceDTO = setupDTO(principal);
		model.addAttribute(userBalanceDTO);
		debtService.settleDebt(userBalanceDTO.getUser(), user);
		return ViewName.REDIRECT + ViewName.DASHBOARD;
	}

	private UserBalanceDTO setupDTO(final Principal principal) {
		final UserWrapper user = userService.findUserByEmail(principal.getName());
		final UserBalanceDTO userBalanceDTO = new UserBalanceDTO();
		userBalanceDTO.setUser(user);
		userBalanceDTO.setUserBalanceMap(userService.getBalancesForUser(user));
		return userBalanceDTO;
	}
}
