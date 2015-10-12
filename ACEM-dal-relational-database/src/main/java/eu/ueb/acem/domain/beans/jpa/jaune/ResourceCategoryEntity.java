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
package eu.ueb.acem.domain.beans.jpa.jaune;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalActivityEntity;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalAnswerEntity;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalSessionEntity;

/**
 * The Spring Data JPA implementation of ResourceCategory domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "ResourceCategory")
@Table(name = "ResourceCategory")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ResourceCategoryEntity extends AbstractEntity implements ResourceCategory {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9065345649480029375L;

	private String name;

	private String iconFileName;

	@Lob
	private String description;

	@ManyToMany(targetEntity = PedagogicalAnswerEntity.class, fetch = FetchType.LAZY, mappedBy = "resourceCategories")
	@JsonIgnore
	private Set<PedagogicalAnswer> answers = new HashSet<PedagogicalAnswer>(0);

	@ManyToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinTable(name = "ResourceCategory_Resource")
	private Set<Resource> resources = new HashSet<Resource>(0);

	@ManyToMany(targetEntity = PedagogicalSessionEntity.class, fetch = FetchType.LAZY, mappedBy = "resourceCategories")
	@JsonIgnore
	private Set<PedagogicalSession> pedagogicalSessions = new HashSet<PedagogicalSession>(0);

	@ManyToMany(targetEntity = PedagogicalActivityEntity.class, fetch = FetchType.LAZY, mappedBy = "resourceCategories")
	@JsonIgnore
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	public ResourceCategoryEntity() {
	}

	public ResourceCategoryEntity(String name, String description, String iconFileName) {
		setName(name);
		setDescription(description);
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}		
	
	@Override
	public Set<PedagogicalAnswer> getAnswers() {
		return answers;
	}
	
	@Override
	public void setAnswers(Set<PedagogicalAnswer> answers) {
		this.answers = answers;
	}
	
	@Override
	public Set<Resource> getResources() {
		return resources;
	}

	@Override
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
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
	public Set<PedagogicalActivity> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}

	@Override
	public Set<PedagogicalSession> getPedagogicalSessions() {
		return pedagogicalSessions;
	}

	@Override
	public void setPedagogicalSessions(Set<PedagogicalSession> pedagogicalSessions) {
		this.pedagogicalSessions = pedagogicalSessions;
	}

	@Override
	public int compareTo(ResourceCategory o) {
		return getName().compareTo(o.getName());
	}

}
