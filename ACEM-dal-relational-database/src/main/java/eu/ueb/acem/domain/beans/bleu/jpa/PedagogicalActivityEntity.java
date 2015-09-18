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
import javax.persistence.ManyToOne;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "PedagogicalActivity")
public class PedagogicalActivityEntity extends PedagogicalUnitEntity implements PedagogicalActivity {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3649534092473417932L;

	@ManyToOne(targetEntity = PedagogicalSessionEntity.class, fetch = FetchType.LAZY)
	private PedagogicalSession pedagogicalSession;

	@ManyToOne(targetEntity = PedagogicalSequenceEntity.class, fetch = FetchType.LAZY)
	private PedagogicalSequence pedagogicalSequence;

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ResourceCategory_PedagogicalActivity")
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	@Lob
	private String instructions;

	public PedagogicalActivityEntity() {
	}

	public PedagogicalActivityEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalActivityEntity(String name, String objective) {
		this(name);
		setObjective(objective);
	}

	@Override
	public String getInstructions() {
		return instructions;
	}

	@Override
	public void setInstructions(String instructions) {
		this.instructions = instructions;
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
	public PedagogicalSequence getPedagogicalSequence() {
		return pedagogicalSequence;
	}

	@Override
	public void setPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		this.pedagogicalSequence = pedagogicalSequence;
	}

	@Override
	public PedagogicalSession getPedagogicalSession() {
		return pedagogicalSession;
	}

	@Override
	public void setPedagogicalSession(PedagogicalSession pedagogicalSession) {
		this.pedagogicalSession = pedagogicalSession;
	}

	@Override
	public PedagogicalActivity getPreviousPedagogicalActivity() {
		return (PedagogicalActivity)getPrevious();
	}

	@Override
	public void setPreviousPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		setPrevious(pedagogicalActivity);
	}

	@Override
	public PedagogicalActivity getNextPedagogicalActivity() {
		return (PedagogicalActivity)getNext();
	}

	@Override
	public void setNextPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		setNext(pedagogicalActivity);
	}

}
