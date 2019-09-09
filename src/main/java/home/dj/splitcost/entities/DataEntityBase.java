package home.dj.splitcost.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataEntityBase implements DataEntity {

	LocalDateTime createTS;
	
	DataEntityBase() {
		createTS = LocalDateTime.now();
	}

	public LocalDateTime getCreateTS() {
		return createTS;
	}

	public void setCreateTS(LocalDateTime createdAt) {
		this.createTS = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createTS);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DataEntityBase)) {
			return false;
		}
		DataEntityBase other = (DataEntityBase) obj;
		return Objects.equals(createTS, other.createTS);
	}
}
