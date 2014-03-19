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

import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.vert.EspacePhysique;
import eu.ueb.acem.domain.beans.vert.neo4j.EspacePhysiqueNode;
import eu.ueb.acem.domain.beans.violet.Cours;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("TeachingClass")
public class SeanceDeCoursNode implements SeanceDeCours {

	private static final long serialVersionUID = -3903886807338724952L;

	@GraphId
	private Long id;
	
	@Indexed
	private String name;

	@RelatedTo(elementClass = CoursNode.class, type = "isPartOfCourse", direction = OUTGOING)
	@Fetch
	private Cours course;

	@RelatedTo(elementClass = EspacePhysiqueNode.class, type = "classHappensInLocation", direction = OUTGOING)
	@Fetch
	private EspacePhysiqueNode location;

	@RelatedTo(elementClass = ScenarioNode.class, type = "scenarioUsedForClass", direction = INCOMING)
	@Fetch
	private Set<ScenarioNode> scenarios;
	
	private String date;
	private String time;
	private String duration;
	private String mode;
	private Integer numberOfLearners;

	public SeanceDeCoursNode() {

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

	public Cours getCourse() {
		return course;
	}

	public EspacePhysique getLocation() {
		return location;
	}

}
