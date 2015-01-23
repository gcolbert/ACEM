package eu.ueb.acem.web.viewbeans;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractViewBean implements Pickable {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		// logger.debug("hashCode id={}, name={}, hashCode={}", getId(), getName(), result);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractViewBean other = (AbstractViewBean) obj;
		// logger.debug("equals this.id={}, other.id={}",getId(),other.getId());
		if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}