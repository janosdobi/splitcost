package home.dj.splitcost.entities.dto;

import java.util.Collection;
import java.util.Set;

import home.dj.splitcost.entities.Cost;
import home.dj.splitcost.entities.Debt;
import home.dj.splitcost.entities.Role;
import home.dj.splitcost.entities.User;

/**
 * Wrapper for user class, for not having to pass around the password.
 */
public final class UserWrapper {

	private final User user;

	public UserWrapper(User user) {
		super();
		this.user = user;
	}

	public long getId() {
		return user.getId();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public String getLastName() {
		return user.getLastName();
	}

	public boolean isActive() {
		return user.isActive();
	}

	public Set<Role> getRoles() {
		return user.getRoles();
	}

	public Collection<Debt> getDebts() {
		return user.getDebts();
	}

	public Collection<Cost> getCosts() {
		return user.getCosts();
	}

	@Override
	public String toString() {
		return user.getFirstName() + " " + user.getLastName();
	}

	@Override
	public int hashCode() {
		return user.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
			
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final User other = (User) obj;

		if (user.getId() != other.getId()) {
			return false;
		}
		return true;
	}

}
