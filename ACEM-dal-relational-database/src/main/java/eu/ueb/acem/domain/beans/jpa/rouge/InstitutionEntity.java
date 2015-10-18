/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.domain.beans.jpa.rouge;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * The Spring Data JPA implementation of Institution domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Institution")
@XmlRootElement(name = "institutions")
public class InstitutionEntity extends OrganisationEntity implements Institution {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3785464560813534408L;

	private String name;

	@ManyToMany(targetEntity = CommunityEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "institutions_communities")
	private Set<Community> communities = new HashSet<Community>(0);

	@ManyToMany(targetEntity = TeachingDepartmentEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "institutions_teachingDepartments")
	private Set<TeachingDepartment> teachingDepartments = new HashSet<TeachingDepartment>(0);

	@ManyToMany(targetEntity = AdministrativeDepartmentEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "institutions_administrativeDepartments")
	private Set<AdministrativeDepartment> administrativeDepartments = new HashSet<AdministrativeDepartment>(0);

	public InstitutionEntity() {
	}

	public InstitutionEntity(String name, String shortname, String iconFileName) {
		this();
		setName(name);
		setShortname(shortname);
		setIconFileName(iconFileName);
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
	public Set<Community> getCommunities() {
		return communities;
	}

	@Override
	public void setCommunities(Set<Community> communities) {
		this.communities = communities;
	}

	@Override
	public Set<TeachingDepartment> getTeachingDepartments() {
		return teachingDepartments;
	}

	@Override
	public void setTeachingDepartments(Set<TeachingDepartment> teachingDepartments) {
		this.teachingDepartments = teachingDepartments;
	}

	@Override
	public Set<AdministrativeDepartment> getAdministrativeDepartments() {
		return administrativeDepartments;
	}

	@Override
	public void setAdministrativeDepartments(Set<AdministrativeDepartment> administrativeDepartments) {
		this.administrativeDepartments = administrativeDepartments;
	}

}
