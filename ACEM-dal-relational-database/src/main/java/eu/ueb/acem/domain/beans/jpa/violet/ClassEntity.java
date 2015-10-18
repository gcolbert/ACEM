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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.jpa.bleu.TeachingModeEntity;
import eu.ueb.acem.domain.beans.jpa.vert.PhysicalSpaceEntity;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.Course;

/**
 * The Spring Data JPA implementation of Class domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Entity(name = "Class")
public class ClassEntity extends TeachingUnitEntity implements Class {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -734932205095203141L;

	private String date;
	
	private String time;

	@ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
	private Course course;

	@OneToOne(targetEntity = PhysicalSpaceEntity.class, fetch = FetchType.LAZY)
	private PhysicalSpace location;

	@ManyToOne(targetEntity = TeachingModeEntity.class, fetch = FetchType.LAZY)
	private TeachingMode teachingMode;

	public ClassEntity() {
	}

	public ClassEntity(String name) {
		setName(name);
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
