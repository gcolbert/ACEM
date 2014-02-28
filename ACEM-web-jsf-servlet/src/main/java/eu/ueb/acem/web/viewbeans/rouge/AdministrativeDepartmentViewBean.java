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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.ScenariosServiceImpl;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class AdministrativeDepartmentViewBean implements OrganisationViewBean, Pickable, Serializable,
		Comparable<AdministrativeDepartmentViewBean> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenariosServiceImpl.class);
	
	private static final long serialVersionUID = -5647852694643666952L;

	private Service administrativeDepartment;

	private Long id;
	
	private String name;

	public AdministrativeDepartmentViewBean() {
	}

	public AdministrativeDepartmentViewBean(Service service) {
		this();
		setAdministrativeDepartment(service);
	}

	@Override
	public Organisation getDomainBean() {
		return getAdministrativeDepartment();
	}

	@Override
	public void setDomainBean(Organisation organisation) {
		setAdministrativeDepartment((Service) organisation);
	}

	public Service getAdministrativeDepartment() {
		return administrativeDepartment;
	}

	public void setAdministrativeDepartment(Service administrativeDepartment) {
		this.administrativeDepartment = administrativeDepartment;
		setId(administrativeDepartment.getId());
		setName(administrativeDepartment.getName());
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
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
