package home.dj.splitcost.entities;

import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SplitCostItemBase extends DataEntityBase {

	double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SplitCostItemBase)) {
			return false;
		}
		SplitCostItemBase other = (SplitCostItemBase) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount);
	}

}
