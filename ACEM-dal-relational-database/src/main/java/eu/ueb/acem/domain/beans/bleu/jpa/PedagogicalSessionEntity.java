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
import javax.persistence.OneToOne;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.jpa.ClassEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@Entity(name = "PedagogicalSession")
public class PedagogicalSessionEntity extends PedagogicalUnitEntity implements PedagogicalSession {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2515537793203483574L;

	@ManyToMany(targetEntity = PedagogicalSequenceEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalSequence> pedagogicalSequences = new HashSet<PedagogicalSequence>(0);

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	@OneToOne(targetEntity = PedagogicalActivityEntity.class, fetch = FetchType.LAZY)
	private PedagogicalActivity firstPedagogicalActivity;

	@OneToOne(targetEntity = ClassEntity.class)
	private Class referredClass;

	public PedagogicalSessionEntity() {
	}

	public PedagogicalSessionEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalSessionEntity(String name, String objective) {
		this(name);
		setObjective(objective);
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
	public Set<PedagogicalSequence> getPedagogicalSequences() {
		return pedagogicalSequences;
	}

	@Override
	public void setPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences) {
		this.pedagogicalSequences = pedagogicalSequences;
	}

	@Override
	public PedagogicalActivity getFirstPedagogicalActivity() {
		return firstPedagogicalActivity;
	}

	@Override
	public void setFirstPedagogicalActivity(PedagogicalActivity firstPedagogicalActivity) {
		this.firstPedagogicalActivity = firstPedagogicalActivity;
	}

	@Override
	public PedagogicalSession getNextPedagogicalSession() {
		return (PedagogicalSession)getNext();
	}

	@Override
	public void setNextPedagogicalSession(PedagogicalSession pedagogicalSession) {
		setNext(pedagogicalSession);
	}

	@Override
	public Class getReferredClass() {
		return referredClass;
	}

	@Override
	public void setReferredClass(Class referredClass) {
		this.referredClass = referredClass;
	}

}
