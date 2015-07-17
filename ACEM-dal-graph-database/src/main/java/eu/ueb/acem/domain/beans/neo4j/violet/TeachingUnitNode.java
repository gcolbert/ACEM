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
package eu.ueb.acem.domain.beans.neo4j.violet;

import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.neo4j.gris.TeacherNode;
import eu.ueb.acem.domain.beans.neo4j.rouge.TeachingDepartmentNode;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@NodeEntity
@TypeAlias("TeachingUnit")
public abstract class TeachingUnitNode extends AbstractNode implements TeachingUnit {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -575685289167406487L;

	private Long idSource;

	private String name;

	private String description;

	private Long duration;
	
	private Long numberOfLearners;
	
	private Long ectsCredits;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "scenarioUsedForTeachingUnit", direction = INCOMING)
	private PedagogicalScenario pedagogicalScenario;
	
	@RelatedTo(elementClass = TeachingDepartmentNode.class, type = "teachesTeachingUnit", direction = INCOMING)
	private TeachingDepartment teachingDepartment;

	@RelatedTo(elementClass = TeacherNode.class, type = "teacherInvolvedInPedagogicalUnit", direction = INCOMING)
	private Set<Teacher> teachers = new HashSet<Teacher>(0);
	
	@Override
	public Long getIdSource() {
		return idSource;
	}

	@Override
	public void setIdSource(Long idSource) {
		this.idSource = idSource;
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
	public Long getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public Long getNumberOfLearners() {
		return numberOfLearners;
	}

	@Override
	public void setNumberOfLearners(Long numberOfLearners) {
		this.numberOfLearners = numberOfLearners;
	}

	@Override
	public Long getECTS() {
		return ectsCredits;
	}

	@Override
	public void setECTS(Long ectsCredits) {
		this.ectsCredits = ectsCredits;
	}

	@Override
	public PedagogicalScenario getPedagogicalScenario() {
		return pedagogicalScenario;
	}

	@Override
	public void setPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		this.pedagogicalScenario = pedagogicalScenario;
	}

	@Override
	public TeachingDepartment getTeachingDepartment() {
		return teachingDepartment;
	}

	@Override
	public void setTeachingDepartment(TeachingDepartment teachingDepartment) {
		this.teachingDepartment = teachingDepartment;
	}

	@Override
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	@Override
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Override
	public int compareTo(TeachingUnit o) {
		return this.getName().compareTo(o.getName());
	}

}
