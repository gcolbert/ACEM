/**
 *     Copyright Grégoire COLBERT 2013
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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.web.viewbeans.ViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class InstitutionViewBean implements ViewBean, Serializable, Comparable<InstitutionViewBean> {

	private static final long serialVersionUID = 6170498010377898612L;

	private Etablissement institution;

	private List<CommunityViewBean> communityViewBeans;

	private List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;

	private List<TeachingDepartmentViewBean> teachingDepartmentViewBeans;

	private Long id;
	
	private String name;

	private String shortname;

	public InstitutionViewBean() {
		communityViewBeans = new ArrayList<CommunityViewBean>();
		administrativeDepartmentViewBeans = new ArrayList<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new ArrayList<TeachingDepartmentViewBean>();
	}

	public InstitutionViewBean(Etablissement institution) {
		this();
		setInstitution(institution);
	}

	@PostConstruct
	public void initInstitutionViewBean() {
		for (Communaute community : institution.getCommunities()) {
			communityViewBeans.add(new CommunityViewBean(community));
		}
		for (Service administrativeDepartment : institution.getAdministrativeDepartments()) {
			administrativeDepartmentViewBeans.add(new AdministrativeDepartmentViewBean(administrativeDepartment));
		}
		for (Composante teachingDepartment : institution.getTeachingDepartments()) {
			teachingDepartmentViewBeans.add(new TeachingDepartmentViewBean(teachingDepartment));
		}
	}

	public Etablissement getInstitution() {
		return institution;
	}

	public void setInstitution(Etablissement institution) {
		this.institution = institution;
		setId(institution.getId());
		setName(institution.getName());
		setShortname(institution.getShortname());
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public List<CommunityViewBean> getCommunityViewBeans() {
		return communityViewBeans;
	}

	public void setCommunityViewBeans(List<CommunityViewBean> communityViewBeans) {
		this.communityViewBeans = communityViewBeans;
	}

	public List<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeans() {
		return administrativeDepartmentViewBeans;
	}

	public void setAdministrativeDepartmentViewBeans(
			List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans) {
		this.administrativeDepartmentViewBeans = administrativeDepartmentViewBeans;
	}

	public List<TeachingDepartmentViewBean> getTeachingDepartmentViewBeans() {
		return teachingDepartmentViewBeans;
	}

	public void setTeachingDepartmentViewBeans(List<TeachingDepartmentViewBean> teachingDepartmentViewBeans) {
		this.teachingDepartmentViewBeans = teachingDepartmentViewBeans;
	}

	@Override
	public int compareTo(InstitutionViewBean o) {
		return name.compareTo(o.getName());
	}

}
