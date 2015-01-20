/**
 *     Copyright Grégoire COLBERT 2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class TeachingDepartmentViewBean implements OrganisationViewBean, Serializable,
		Comparable<TeachingDepartmentViewBean> {

	private static final long serialVersionUID = 6787135851384385849L;

	private TeachingDepartment teachingDepartment;

	private Long id;
	
	private String name;

	private String shortname;
	
	private String iconFileName;
	
	public TeachingDepartmentViewBean() {
	}

	public TeachingDepartmentViewBean(TeachingDepartment teachingDepartment) {
		this();
		setTeachingDepartment(teachingDepartment);
	}

	public TeachingDepartment getTeachingDepartment() {
		return teachingDepartment;
	}

	public void setTeachingDepartment(TeachingDepartment teachingDepartment) {
		this.teachingDepartment = teachingDepartment;
		setId(teachingDepartment.getId());
		setName(teachingDepartment.getName());
		setShortname(teachingDepartment.getShortname());
		setIconFileName(teachingDepartment.getIconFileName());
	}

	@Override
	public Organisation getDomainBean() {
		return getTeachingDepartment();
	}

	@Override
	public void setDomainBean(Organisation organisation) {
		setTeachingDepartment((TeachingDepartment) organisation);
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getShortname() {
		return shortname;
	}

	@Override
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}
	
	@Override
	public int compareTo(TeachingDepartmentViewBean o) {
		return name.compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		TeachingDepartmentViewBean other = (TeachingDepartmentViewBean) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		}
		else
			if (!getId().equals(other.getId()))
				return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		}
		else
			if (!getName().equals(other.getName()))
				return false;
		return true;
	}	
	
}
