package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.Service;

public class AdministrativeDepartmentViewBean implements Serializable, Comparable<AdministrativeDepartmentViewBean> {

	private static final long serialVersionUID = -5647852694643666952L;

	private Service administrativeDepartment;
	
	private Long id;
	
	private String name;

	public AdministrativeDepartmentViewBean() {
	}

	public AdministrativeDepartmentViewBean(Service service) {
		setAdministrativeDepartment(service);
	}

	public Service getAdministrativeDepartment() {
		return administrativeDepartment;
	}

	public void setAdministrativeDepartment(Service administrativeDepartment) {
		this.administrativeDepartment = administrativeDepartment;
		setId(administrativeDepartment.getId());
		setName(administrativeDepartment.getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(AdministrativeDepartmentViewBean o) {
		return name.compareTo(o.getName());
	}

}
