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

import static org.neo4j.graphdb.Direction.OUTGOING;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.neo4j.bleu.TeachingModeNode;
import eu.ueb.acem.domain.beans.neo4j.vert.PhysicalSpaceNode;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.Course;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Class")
public class ClassNode extends TeachingUnitNode implements Class {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3903886807338724952L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = CourseNode.class, type = "classPartOfCourse", direction = OUTGOING)
	private Course course;

	@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "classHappensInLocation", direction = OUTGOING)
	private PhysicalSpace location;

	private String date;
	private String time;

	@RelatedTo(elementClass = TeachingModeNode.class, type = "classHasTeachingMode", direction = OUTGOING)
	private TeachingMode teachingMode;

	public ClassNode() {
	}

	public ClassNode(String name) {
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
	public TeachingMode getTeachingMode() {
		return teachingMode;
	}

	@Override
	public void setTeachingMode(TeachingMode teachingMode) {
		this.teachingMode = teachingMode;
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

}
