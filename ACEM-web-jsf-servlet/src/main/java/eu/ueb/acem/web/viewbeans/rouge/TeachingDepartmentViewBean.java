package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.Composante;

public class TeachingDepartmentViewBean implements Serializable, Comparable<TeachingDepartmentViewBean> {

	private static final long serialVersionUID = 6787135851384385849L;

	private Composante teachingDepartment;
	
	private Long id;
	
	private String name;

	public TeachingDepartmentViewBean() {
	}

	public TeachingDepartmentViewBean(Composante teachingDepartment) {
		setTeachingDepartment(teachingDepartment);
	}

	public Composante getTeachingDepartment() {
		return teachingDepartment;
	}

	public void setTeachingDepartment(Composante teachingDepartment) {
		this.teachingDepartment = teachingDepartment;
		setId(teachingDepartment.getId());
		setName(teachingDepartment.getName());
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
	public int compareTo(TeachingDepartmentViewBean o) {
		return name.compareTo(o.getName());
	}

}
