package home.dj.splitcost.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Debt extends SplitCostItemBase {
	
	@Id
	@GeneratedValue
	@Column(name="debt_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "cost_id")
	private Cost cost;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User debtor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public User getDebtor() {
		return debtor;
	}

	public void setDebtor(User debtor) {
		this.debtor = debtor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((debtor == null) ? 0 : debtor.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Debt other = (Debt) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (debtor == null) {
			if (other.debtor != null)
				return false;
		} else if (!debtor.equals(other.debtor))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Debt [amount=" + amount + ", cost=" + cost + ", debtor=" + debtor + "]";
	}
	
	
}
