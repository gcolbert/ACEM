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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-20
 * 
 */
@Entity(name = "PedagogicalAnswer")
@Table(name = "PedagogicalAnswer")
public class PedagogicalAnswerEntity extends AbstractEntity implements PedagogicalAnswer {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8984075611788924122L;

	private String name;

	private String description;

	@ManyToMany(targetEntity = PedagogicalNeedEntity.class, fetch = FetchType.LAZY, mappedBy = "answers")
	private Set<PedagogicalNeed> pedagogicalNeeds = new HashSet<PedagogicalNeed>(0);

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	public PedagogicalAnswerEntity() {
	}

	public PedagogicalAnswerEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalAnswerEntity(String name, String description) {
		this(name);
		setDescription(description);
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Set<PedagogicalNeed> getNeeds() {
		return pedagogicalNeeds;
	}

	@Override
	public void setNeeds(Set<PedagogicalNeed> needs) {
		this.pedagogicalNeeds = needs;
	}
	
	@Override
	public Set<ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}

	@Override
	public void setResourceCategories(Set<ResourceCategory> resourceCategories) {
		this.resourceCategories = resourceCategories;
	}

	@Override
	public int compareTo(PedagogicalAnswer o) {
		return this.compareTo(o);
	}

}
