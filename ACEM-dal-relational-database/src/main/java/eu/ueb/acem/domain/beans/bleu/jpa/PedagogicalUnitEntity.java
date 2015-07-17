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
package eu.ueb.acem.domain.beans.bleu.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.bleu.PedagogicalUnit;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@Entity(name = "PedagogicalUnit")
public abstract class PedagogicalUnitEntity extends AbstractEntity implements PedagogicalUnit {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7618106069534184673L;

	private String name;

	@Lob
	private String objective;

	private Long start;

	private Long duration;

	private Long creationDate;

	private Long modificationDate;

	@OneToOne(targetEntity = TeachingModeEntity.class, fetch = FetchType.LAZY)
	private TeachingMode teachingMode;

	@ManyToMany(targetEntity = PedagogicalKeywordEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalKeyword> pedagogicalKeywords = new HashSet<PedagogicalKeyword>(0);

	@ManyToMany(targetEntity = PedagogicalUnitEntity.class, fetch = FetchType.LAZY, mappedBy = "parentsPrerequisites")
	private Set<PedagogicalUnit> childrenPrerequisites = new HashSet<PedagogicalUnit>(0);

	@ManyToMany(targetEntity = PedagogicalUnitEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name="pedagogicalUnits_prerequisites")
	private Set<PedagogicalUnit> parentsPrerequisites = new HashSet<PedagogicalUnit>(0);

	@OneToOne(targetEntity = PedagogicalUnitEntity.class, fetch = FetchType.LAZY)
	private PedagogicalUnit nextPedagogicalUnit;
	
	public PedagogicalUnitEntity() {
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
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
	}

	@Override
	public Long getStart() {
		return start;
	}

	@Override
	public void setStart(Long start) {
		this.start = start;
	}

	@Override
	public Long getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public Long getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Long date) {
		this.creationDate = date;
	}

	@Override
	public Long getModificationDate() {
		return modificationDate;
	}

	@Override
	public void setModificationDate(Long date) {
		this.modificationDate = date;
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
	public Set<PedagogicalKeyword> getPedagogicalKeywords() {
		return pedagogicalKeywords;
	}

	@Override
	public void setPedagogicalKeywords(Set<PedagogicalKeyword> pedagogicalKeywords) {
		this.pedagogicalKeywords = pedagogicalKeywords;
	}

	protected PedagogicalUnit getNext() {
		return nextPedagogicalUnit;
	}

	protected void setNext(PedagogicalUnit pedagogicalUnit) {
		this.nextPedagogicalUnit = pedagogicalUnit;
	}

	@Override
	public Set<PedagogicalUnit> getPrequisites() {
		return parentsPrerequisites;
	}

	@Override
	public void setPrerequisites(Set<PedagogicalUnit> parentsPrerequisites) {
		this.parentsPrerequisites = parentsPrerequisites;
	}

	@Override
	public Set<PedagogicalUnit> getDependentPedagogicalUnits() {
		return childrenPrerequisites;
	}

	@Override
	public void setDependentPedagogicalUnits(Set<PedagogicalUnit> childrenPrerequisites) {
		this.childrenPrerequisites = childrenPrerequisites;
	}

	@Override
	public int compareTo(PedagogicalUnit o) {
		int returnValue;
		if ((getModificationDate() != null) && (o.getModificationDate() != null)) {
			returnValue = getModificationDate().compareTo(o.getModificationDate());
		}
		else {
			if (getModificationDate() != null) {
				returnValue = getModificationDate().compareTo(o.getCreationDate());
			}
			else {
				if (o.getModificationDate() != null) {
					returnValue = getCreationDate().compareTo(o.getModificationDate());
				}
				else {
					returnValue = getCreationDate().compareTo(o.getCreationDate());
				}
			}
		}
		return returnValue;
	}

}
