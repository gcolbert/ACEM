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
package eu.ueb.acem.domain.beans.jpa.bleu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.bleu.PedagogicalUnit;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;

/**
 * The Spring Data JPA implementation of PedagogicalKeyword domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Entity(name = "PedagogicalKeyword")
@Table(name = "PedagogicalKeyword")
public class PedagogicalKeywordEntity extends AbstractEntity implements PedagogicalKeyword {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7499700591569767007L;

	private String name;

	@ManyToMany(targetEntity = PedagogicalUnitEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalUnit> pedagogicalUnits = new HashSet<PedagogicalUnit>(0);

	public PedagogicalKeywordEntity() {
	}

	public PedagogicalKeywordEntity(String name) {
		this();
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
	public Set<PedagogicalUnit> getPedagogicalUnits() {
		return pedagogicalUnits;
	}

	@Override
	public void setPedagogicalUnits(Set<PedagogicalUnit> pedagogicalUnits) {
		this.pedagogicalUnits = pedagogicalUnits;
	}

	@Override
	public int compareTo(PedagogicalKeyword o) {
		return this.compareTo(o);
	}

}
