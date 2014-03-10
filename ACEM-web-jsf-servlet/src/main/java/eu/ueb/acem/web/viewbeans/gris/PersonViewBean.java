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
package eu.ueb.acem.web.viewbeans.gris;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class PersonViewBean implements Pickable, Serializable, Comparable<PersonViewBean> {

	private static final long serialVersionUID = 4401967530594259861L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PersonViewBean.class);
	
	private Personne domainBean;
	
	private List<OrganisationViewBean> organisationViewBeans;
	
	private Long id;
	
	private String name;
	
	private String login;
	
	private String language;
	
	private Boolean administrator;
	
	public PersonViewBean() {
		this.organisationViewBeans = new ArrayList<OrganisationViewBean>();
	}
	
	public PersonViewBean(Personne person) {
		this();
		setDomainBean(person);
	}

	public Personne getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(Personne person) {
		this.domainBean = person;
		setId(person.getId());
		setName(person.getName());
		setLogin(person.getLogin());
		setLanguage(person.getLanguage());
		setAdministrator(person.isAdministrator());
		organisationViewBeans.clear();
		for (Organisation organisation : person.getWorksForOrganisations()) {
			if (organisation instanceof Communaute) {
				organisationViewBeans.add(new CommunityViewBean((Communaute)organisation));
			}
			else if (organisation instanceof Etablissement) {
				organisationViewBeans.add(new InstitutionViewBean((Etablissement)organisation));
			}
			else if (organisation instanceof Service) {
				organisationViewBeans.add(new AdministrativeDepartmentViewBean((Service)organisation));
			}
			else if (organisation instanceof Composante) {
				organisationViewBeans.add(new TeachingDepartmentViewBean((Composante)organisation));
			}
		}
	}

	public List<OrganisationViewBean> getOrganisationViewBeans() {
		return organisationViewBeans;
	}

	@Override
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	@Override
	public int compareTo(PersonViewBean o) {
		return getDomainBean().compareTo(o.getDomainBean());
	}

	
}
