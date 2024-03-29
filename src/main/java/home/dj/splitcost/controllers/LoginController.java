package home.dj.splitcost.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import home.dj.splitcost.constants.ViewName;
import home.dj.splitcost.entities.User;
import home.dj.splitcost.entities.dto.UserWrapper;
import home.dj.splitcost.services.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@GetMapping(value = { ViewName.MAPPING, ViewName.MAPPING + ViewName.INDEX })
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewName.INDEX);
		return modelAndView;
	}

	@GetMapping(value = ViewName.MAPPING + ViewName.REGISTER)
	public String registration(@ModelAttribute final User user) {
		return ViewName.REGISTER;
	}

	@PostMapping(value = ViewName.MAPPING + ViewName.REGISTER)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		UserWrapper userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName(ViewName.REGISTER);
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName(ViewName.REGISTER);

		}
		return modelAndView;
	}

	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		final ModelAndView modelAndView = new ModelAndView();
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserWrapper user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",
				"Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
}
