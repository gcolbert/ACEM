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
package eu.ueb.acem.domain.beans.jpa.violet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.Credit;
import eu.ueb.acem.domain.beans.violet.Degree;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Credit")
public class CreditEntity extends TeachingUnitEntity implements Credit {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6318566194747849738L;

	//@RelatedTo(elementClass = DegreeNode.class, type = "isPartOfDegree", direction = OUTGOING)
	@ManyToMany(targetEntity = DegreeEntity.class, fetch = FetchType.LAZY)
	private Set<Degree> degrees = new HashSet<Degree>(0);

	//@RelatedTo(elementClass = CourseNode.class, type = "isPartOfCredit", direction = INCOMING)
	@OneToMany(targetEntity = CourseEntity.class, fetch = FetchType.LAZY, mappedBy = "credit")
	private Set<Course> courses;

	public CreditEntity() {
	}

	public CreditEntity(String name) {
		setName(name);
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

}
