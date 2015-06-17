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
package eu.ueb.acem.domain.beans.bleu.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.jpa.TeacherEntity;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.jpa.ClassEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "PedagogicalScenario")
@Table(name = "PedagogicalScenario")
public class PedagogicalScenarioEntity extends AbstractEntity implements PedagogicalScenario {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -512829316498046439L;

	private Long creationDate;

	private Long modificationDate;

	private String name;

	@Lob
	private String objective;

	@Lob
	private String evaluationModes;

	private Boolean published;

	@ManyToMany(targetEntity = ClassEntity.class, fetch = FetchType.LAZY)
	private Set<Class> classes = new HashSet<Class>(0);

	@ManyToMany(targetEntity = PedagogicalActivityEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	@ManyToMany(targetEntity = TeacherEntity.class, fetch = FetchType.LAZY)
	private Set<Teacher> authors = new HashSet<Teacher>(0);

	@ManyToMany(targetEntity = PedagogicalKeywordEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalKeyword> pedagogicalKeywords = new HashSet<PedagogicalKeyword>(0);

	public PedagogicalScenarioEntity() {
		published = false;
	}

	public PedagogicalScenarioEntity(String name, String objective) {
		this();
		this.name = name;
		this.objective = objective;
	}

	@Override
	public Long getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Long date) {
		this.creationDate = date;
	}

	@Override
	public Long getModificationDate() {
		return modificationDate;
	}

	@Override
	public void setModificationDate(Long date) {
		this.modificationDate = date;
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
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
	}

	@Override
	public String getEvaluationModes() {
		return evaluationModes;
	}

	@Override
	public void setEvaluationModes(String evaluationModes) {
		this.evaluationModes = evaluationModes;
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
	public Set<Teacher> getAuthors() {
		return authors;
	}

	@Override
	public void setAuthors(Set<Teacher> authors) {
		this.authors = authors;
	}

	@Override
	public Set<PedagogicalKeyword> getPedagogicalKeywords() {
		return pedagogicalKeywords;
	}

	@Override
	public void setPedagogicalKeywords(Set<PedagogicalKeyword> pedagogicalKeywords) {
		this.pedagogicalKeywords = pedagogicalKeywords;
	}

	@Override
	public Boolean isPublished() {
		return published;
	}

	@Override
	public void setPublished(Boolean published) {
		this.published = published;
	}

	@Override
	public Set<Class> getClasses() {
		return classes;
	}

	@Override
	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}
	
	@Override
	public int compareTo(PedagogicalScenario o) {
		int returnValue;
		if ((getModificationDate() != null) && (o.getModificationDate() != null)) {
			returnValue = getModificationDate().compareTo(o.getModificationDate());
		}
		else {
			if (getModificationDate() != null) {
				returnValue = getModificationDate().compareTo(o.getCreationDate());
			}
			else {
				if (o.getModificationDate() != null) {
					returnValue = getCreationDate().compareTo(o.getModificationDate());
				}
				else {
					returnValue = getCreationDate().compareTo(o.getCreationDate());
				}
			}
		}
		return returnValue;
	}

}
