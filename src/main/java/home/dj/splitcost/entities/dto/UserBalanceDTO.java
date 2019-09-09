package home.dj.splitcost.entities.dto;

import java.util.Map;

import home.dj.splitcost.entities.User;

public class UserBalanceDTO implements DataDTO {
	
	private User user;
	private Map<User, Double> userBalanceMap;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Map<User, Double> getUserBalanceMap() {
		return userBalanceMap;
	}
	public void setUserBalanceMap(Map<User, Double> userBalanceMap) {
		this.userBalanceMap = userBalanceMap;
	}
}
