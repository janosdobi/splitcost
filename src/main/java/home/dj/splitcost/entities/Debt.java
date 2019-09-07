package home.dj.splitcost.entities;

import java.util.Objects;

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
	@Column(name = "debt_id")
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
	public String toString() {
		return "Debt [amount=" + amount + ", cost=" + cost + ", debtor=" + debtor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, cost, debtor, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Debt)) {
			return false;
		}
		Debt other = (Debt) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(cost, other.cost) && Objects.equals(debtor, other.debtor) && id == other.id;
	}

}
