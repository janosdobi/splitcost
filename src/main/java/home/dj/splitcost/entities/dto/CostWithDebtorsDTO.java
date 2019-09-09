package home.dj.splitcost.entities.dto;

import java.util.Collection;

import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.User;

public class CostWithDebtorsDTO implements DataDTO {

	private Collection<User> selectedUsers;
	
	private Cost cost;
	
	private boolean splitEqually;
	
	public CostWithDebtorsDTO() {
		this.splitEqually = true;
	}

	public Collection<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(Collection<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public boolean isSplitEqually() {
		return splitEqually;
	}

	public void setSplitEqually(boolean splitEqually) {
		this.splitEqually = splitEqually;
	}
}
