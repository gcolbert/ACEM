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
package eu.ueb.acem.domain.beans.violet.neo4j;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;
import eu.ueb.acem.domain.beans.vert.neo4j.PhysicalSpaceNode;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.TeachingClass;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("TeachingClass")
public class TeachingClassNode implements TeachingClass {

	private static final long serialVersionUID = -3903886807338724952L;

	@GraphId
	private Long id;
	
	@Indexed
	private String name;

	@RelatedTo(elementClass = CourseNode.class, type = "isPartOfCourse", direction = OUTGOING)
	@Fetch
	private Course course;

	@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "classHappensInLocation", direction = OUTGOING)
	@Fetch
	private PhysicalSpaceNode location;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "scenarioUsedForClass", direction = INCOMING)
	@Fetch
	private Set<PedagogicalScenarioNode> scenarios;
	
	private String date;
	private String time;
	private String duration;
	private String mode;
	private Integer numberOfLearners;

	public TeachingClassNode() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String heure) {
		this.time = heure;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duree) {
		this.duration = duree;
	}

	public String getTeachingMode() {
		return mode;
	}

	public void setTeachingMode(String modalite) {
		this.mode = modalite;
	}

	public Integer getNumberOfLearners() {
		return numberOfLearners;
	}

	public void setNumberOfLearners(Integer nbApprenants) {
		this.numberOfLearners = nbApprenants;
	}

	public Course getCourse() {
		return course;
	}

	public PhysicalSpace getLocation() {
		return location;
	}

}
