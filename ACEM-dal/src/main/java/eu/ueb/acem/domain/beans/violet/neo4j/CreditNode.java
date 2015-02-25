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

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.Credit;
import eu.ueb.acem.domain.beans.violet.Degree;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Credit")
public class CreditNode extends AbstractNode implements Credit {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8179710397691380136L;

	@Indexed
	private String name;

	private String duration;

	@RelatedTo(elementClass = DegreeNode.class, type = "isPartOfDegree", direction = OUTGOING)
	private Set<Degree> degrees = new HashSet<Degree>(0);

	@RelatedTo(elementClass = CourseNode.class, type = "isPartOfCredit", direction = INCOMING)
	private Set<Course> courses;

	public CreditNode() {
	}

	public CreditNode(String name) {
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
	public String getDuration() {
		return duration;
	}

	@Override
	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public Set<Degree> getDegrees() {
		return degrees;
	}

	@Override
	public void setDegrees(Set<Degree> degrees) {
		this.degrees = degrees;
	}

	@Override
	public Set<Course> getCourses() {
		return courses;
	}

	@Override
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int compareTo(Credit o) {
		return this.getName().compareTo(o.getName());
	}

}
