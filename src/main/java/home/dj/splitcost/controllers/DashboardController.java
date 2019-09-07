package home.dj.splitcost.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import home.dj.splitcost.services.DebtService;
import home.dj.splitcost.services.UserService;

@Controller
public class DashboardController {

	@Autowired
	UserService userService;

	@Autowired
	DebtService debtService;
	
	@GetMapping("/dashboard")
	public String loginSuccessful(final Principal p) {
		return "rest_dashboard";
	}
}
