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
package eu.ueb.acem.domain.beans.neo4j.gris;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.neo4j.rouge.OrganisationNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * The Spring Data Neo4j implementation of Person domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
@NodeEntity
@TypeAlias("Person")
public class PersonNode extends AbstractNode implements Person {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5697929702791942609L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = OrganisationNode.class, type = "worksForOrganisation", direction = OUTGOING)
	private Set<Organisation> worksForOrganisations = new HashSet<Organisation>(0);

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "hasFavoriteToolCategory", direction = OUTGOING)
	private Set<ResourceCategory> favoriteToolCategories = new HashSet<ResourceCategory>(0);

	@Indexed(unique = true)
	private String login;

	private String password;

	private String email;

	private String language;

	private Boolean administrator;

	private Boolean teacher;

	public PersonNode() {
		setLanguage("fr");
		setAdministrator(false);
		setTeacher(false);
	}

	public PersonNode(String name, String login, String password) {
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
