package home.dj.splitcost.entities.dto;

import java.util.ArrayList;
import java.util.List;

import home.dj.splitcost.entities.Debt;

public class UpdateDebtsDTO implements DataDTO {
	private List<Debt> debts;
	private double selfAllocated;

	public UpdateDebtsDTO() {

	}

	public UpdateDebtsDTO(List<Debt> debts) {
		this.debts = new ArrayList<>(debts);
	}

	public List<Debt> getDebts() {
		return debts;
	}

	public void setDebts(List<Debt> debts) {
		this.debts = debts;
	}

	public double getSelfAllocated() {
		return selfAllocated;
	}

	public void setSelfAllocated(double selfAllocated) {
		this.selfAllocated = selfAllocated;
	}
}
