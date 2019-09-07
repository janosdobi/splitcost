package home.dj.splitcost.entities.dto;

import java.util.Map;

public class UserBalanceDTO implements DataDTO {

	private String userName;
	private Map<String, Double> userBalanceMap;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Double> getUserBalanceMap() {
		return userBalanceMap;
	}

	public void setUserBalanceMap(Map<String, Double> userBalanceMap) {
		this.userBalanceMap = userBalanceMap;
	}
}
