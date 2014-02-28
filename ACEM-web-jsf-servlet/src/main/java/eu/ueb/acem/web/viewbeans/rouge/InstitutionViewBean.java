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

import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class InstitutionViewBean implements OrganisationViewBean, Pickable, Serializable, Comparable<InstitutionViewBean> {

	private static final Logger logger = LoggerFactory.getLogger(InstitutionViewBean.class);
	
	private static final long serialVersionUID = 6170498010377898612L;

	private Etablissement institution;

	private Long id;
	
	private String name;

	private String shortname;

	public InstitutionViewBean() {
	}

	public InstitutionViewBean(Etablissement institution) {
		this();
		setInstitution(institution);
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
	public Organisation getDomainBean() {
		return getInstitution();
	}

	@Override
	public void setDomainBean(Organisation organisation) {
		setInstitution((Etablissement) organisation);
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

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public int compareTo(InstitutionViewBean o) {
		return name.compareTo(o.getName());
	}

}
