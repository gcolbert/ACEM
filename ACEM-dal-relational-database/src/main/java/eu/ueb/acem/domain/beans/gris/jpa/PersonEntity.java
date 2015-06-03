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
package eu.ueb.acem.domain.beans.gris.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.jpa.OrganisationEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Person")
public class PersonEntity extends AbstractEntity implements Person {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 4825136240529196558L;

	private String name;

	@ManyToMany(targetEntity = OrganisationEntity.class, fetch = FetchType.LAZY)
	private Set<Organisation> worksForOrganisations = new HashSet<Organisation>(0);

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY)
	private Set<ResourceCategory> favoriteToolCategories = new HashSet<ResourceCategory>(0);

	private String login;

	private String password;
	
	private String email;
	
	private String language;

	private Boolean administrator;
	
	private Boolean teacher;

	public PersonEntity() {
		setLanguage("fr");
		setAdministrator(false);
		setTeacher(false);
	}

	public PersonEntity(String name, String login, String password) {
		this();
		setName(name);
		setLogin(login);
		setPassword(password);
	}

	@Override
	public Set<Organisation> getWorksForOrganisations() {
		return worksForOrganisations;
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
	public String getLogin() {
		return login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public Boolean isAdministrator() {
		return administrator;
	}

	@Override
	public void setAdministrator(Boolean isAdministrator) {
		this.administrator = isAdministrator;
	}

	@Override
	public Boolean isTeacher() {
		return teacher;
	}

	@Override
	public void setTeacher(Boolean isTeacher) {
		this.teacher = isTeacher;
	}

	@Override
	public Set<ResourceCategory> getFavoriteToolCategories() {
		return favoriteToolCategories;
	}

	@Override
	public void setFavoriteToolCategories(Set<ResourceCategory> favoriteToolCategories) {
		this.favoriteToolCategories = favoriteToolCategories;
	}

	@Override
	public int compareTo(Person person) {
		return getName().compareToIgnoreCase(person.getName());
	}

}
