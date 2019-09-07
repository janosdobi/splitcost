package home.dj.splitcost.controllers.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import home.dj.splitcost.entities.User;
import home.dj.splitcost.entities.dto.UserBalanceDTO;
import home.dj.splitcost.services.UserService;

@RestController
public class MyRestController {
	
	@Autowired
	private UserService userService;

	@GetMapping("rest_dashboard")
	public UserBalanceDTO restIndex(final Principal principal) {
		final UserBalanceDTO userBalanceDTO = setupDTO(principal);
		return userBalanceDTO;
	}
	
	private UserBalanceDTO setupDTO(final Principal principal) {
		final User user = userService.findUserByEmail(principal.getName());
		final UserBalanceDTO userBalanceDTO = new UserBalanceDTO();
		userBalanceDTO.setUserName(user.toString());
		userBalanceDTO.setUserBalanceMap(userService.getBalancesForUser(user));
		return userBalanceDTO;
	}
}
