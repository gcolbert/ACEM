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

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.Credit;

/**
 * The Spring Data JPA implementation of Course domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Course")
public class CourseEntity extends TeachingUnitEntity implements Course {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 5617849758695365829L;

	@ManyToOne(targetEntity = CreditEntity.class, fetch = FetchType.LAZY)
	private Credit credit;

	@OneToMany(targetEntity = ClassEntity.class, fetch = FetchType.LAZY)
	private Set<Class> classes;

	public CourseEntity() {
	}

	public CourseEntity(String name) {
		setName(name);
	}

	@Override
	public Credit getCredit() {
		return credit;
	}

	@Override
	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	@Override
	public Set<Class> getClasses() {
		return classes;
	}

	@Override
	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}
	
}
