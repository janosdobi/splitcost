package home.dj.splitcost.entities;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cost extends SplitCostItemBase {

	@Id
	@GeneratedValue
	@Column(name = "cost_id")
	private long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;

	@OneToMany
	@JoinColumn(name = "cost_id")
	private Collection<Debt> debts;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<Debt> getDebts() {
		return debts;
	}

	public void setDebts(Collection<Debt> debts) {
		this.debts = debts;
	}

	@Override
	public String toString() {
		return "Cost [name=" + name + ", amount=" + amount + ", owner=" + owner + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, debts, id, name, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Cost)) {
			return false;
		}
		Cost other = (Cost) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(debts, other.debts) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(owner, other.owner);
	}
}
