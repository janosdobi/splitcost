package home.dj.splitcost.entities;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cost {
	
	@Id
	@GeneratedValue
	@Column(name="cost_id")
	private long id;
	
	private String name;
	
	private double amount;
	
	private LocalDateTime createTS;
	
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getCreateTS() {
		return createTS;
	}

	public void setCreateTS(LocalDateTime createTS) {
		this.createTS = createTS;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((createTS == null) ? 0 : createTS.hashCode());
		result = prime * result + ((debts == null) ? 0 : debts.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		Cost other = (Cost) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (createTS == null) {
			if (other.createTS != null)
				return false;
		} else if (!createTS.equals(other.createTS))
			return false;
		if (debts == null) {
			if (other.debts != null)
				return false;
		} else if (!debts.equals(other.debts))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cost [name=" + name + ", amount=" + amount + ", owner=" + owner + "]";
	}
}
