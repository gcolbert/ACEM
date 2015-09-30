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

import eu.ueb.acem.domain.beans.violet.Credit;
import eu.ueb.acem.domain.beans.violet.Degree;

/**
 * The Spring Data JPA implementation of Degree domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Entity(name = "Degree")
public class DegreeEntity extends TeachingUnitEntity implements Degree {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3867978682705242939L;

	@ManyToMany(targetEntity = CreditEntity.class, fetch = FetchType.LAZY, mappedBy = "degrees")
	private Set<Credit> credits = new HashSet<Credit>(0);

	public DegreeEntity() {
	}

	public DegreeEntity(String name) {
		setName(name);
	}

	@Override
	public Set<Credit> getCredits() {
		return credits;
	}

	@Override
	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

}
