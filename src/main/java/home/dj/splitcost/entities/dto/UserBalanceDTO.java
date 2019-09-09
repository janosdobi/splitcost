package home.dj.splitcost.entities.dto;

import java.util.Map;

public class UserBalanceDTO implements DataDTO {

	private UserWrapper user;
	private Map<UserWrapper, Double> userBalanceMap;

	public UserWrapper getUser() {
		return user;
	}

	public void setUser(UserWrapper user) {
		this.user = user;
	}

	public Map<UserWrapper, Double> getUserBalanceMap() {
		return userBalanceMap;
	}

	public void setUserBalanceMap(Map<UserWrapper, Double> userBalanceMap) {
		this.userBalanceMap = userBalanceMap;
	}
}
