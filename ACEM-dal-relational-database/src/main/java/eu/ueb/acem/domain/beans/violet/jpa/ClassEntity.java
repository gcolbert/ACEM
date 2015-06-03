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
package eu.ueb.acem.domain.beans.violet.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.jpa.PedagogicalScenarioEntity;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;
import eu.ueb.acem.domain.beans.vert.jpa.PhysicalSpaceEntity;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.Course;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Class")
@Table(name = "Class")
public class ClassEntity extends AbstractEntity implements Class {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -734932205095203141L;

	private String name;

	@ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
	private Course course;

	//@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "classHappensInLocation", direction = OUTGOING)
	@OneToOne(targetEntity = PhysicalSpaceEntity.class, fetch = FetchType.LAZY)
	private PhysicalSpace location;

	//@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "scenarioUsedForClass", direction = INCOMING)
	@ManyToMany(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	private String date;
	private String time;
	private String duration;
	private String teachingMode;
	private Integer numberOfLearners;

	public ClassEntity() {
	}

	public ClassEntity(String name) {
		this.name = name;
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
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public void setTime(String heure) {
		this.time = heure;
	}

	@Override
	public String getDuration() {
		return duration;
	}

	@Override
	public void setDuration(String duree) {
		this.duration = duree;
	}

	@Override
	public String getTeachingMode() {
		return teachingMode;
	}

	@Override
	public void setTeachingMode(String teachingMode) {
		this.teachingMode = teachingMode;
	}

	@Override
	public Integer getNumberOfLearners() {
		return numberOfLearners;
	}

	@Override
	public void setNumberOfLearners(Integer nbApprenants) {
		this.numberOfLearners = nbApprenants;
	}

	@Override
	public Course getCourse() {
		return course;
	}

	@Override
	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public PhysicalSpace getLocation() {
		return location;
	}

	@Override
	public void setLocation(PhysicalSpace location) {
		this.location = location;
	}

	@Override
	public Set<PedagogicalScenario> getPedagogicalScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setPedagogicalScenarios(Set<PedagogicalScenario> scenarios) {
		this.pedagogicalScenarios = scenarios;
	}

	@Override
	public int compareTo(Class o) {
		return this.getName().compareTo(o.getName());
	}

}
