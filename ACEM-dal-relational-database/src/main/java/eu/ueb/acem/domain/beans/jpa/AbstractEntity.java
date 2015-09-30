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
package eu.ueb.acem.domain.beans.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An abstract class that defines the most basic methods of all database
 * entities: the "id" property, the overload of functions "hashCode", "equals"
 * and "toString".
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2518867451836843292L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		AbstractEntity other = (AbstractEntity) obj;
//		if (other.getId()!=null && !getId().equals(other.getId())) {
//			return false;
//		}
//		return true;
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		AbstractEntity other = (AbstractEntity) obj;

		if (getId() != null ? !getId().equals(other.getId())
				: other.getId() != null) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		//return ToStringBuilder.reflectionToString(this);
		return new ToStringBuilder(this).append("id",id).toString();
	}

}
