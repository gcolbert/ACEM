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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.Credit;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Course")
@Table(name = "Course")
public class CourseEntity extends AbstractEntity implements Course {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 5617849758695365829L;

	private String name;

	private String duration;

	@ManyToOne(targetEntity = CreditEntity.class, fetch = FetchType.LAZY)
	private Credit credit;

	public CourseEntity() {
	}

	public CourseEntity(String name) {
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
	public Credit getCredit() {
		return credit;
	}

	@Override
	public int compareTo(Course o) {
		return this.getName().compareTo(o.getName());
	}
	
}
