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
package eu.ueb.acem.domain.beans.neo4j.bleu;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.neo4j.violet.ClassNode;
import eu.ueb.acem.domain.beans.violet.Class;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalSession")
public class PedagogicalSessionNode extends PedagogicalUnitNode implements PedagogicalSession {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1573051536831342164L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalSequenceNode.class, type = "sessionForSequence", direction = OUTGOING)
	private PedagogicalSequence pedagogicalSequence;

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "sessionRequiringResourceFromCategory", direction = OUTGOING)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	@RelatedTo(elementClass = PedagogicalActivityNode.class, type = "activityForSession", direction = INCOMING)
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	@RelatedTo(elementClass = ClassNode.class, type = "refersToClass", direction = OUTGOING)
	private Class referredClass;

	public PedagogicalSessionNode() {
	}

	public PedagogicalSessionNode(String name) {
		this();
		setName(name);
	}

	public PedagogicalSessionNode(String name, String objective) {
		this(name);
		setObjective(objective);
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
	public Set<PedagogicalActivity> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}

	@Override
	public Class getReferredClass() {
		return referredClass;
	}

	@Override
	public void setReferredClass(Class referredClass) {
		this.referredClass = referredClass;
	}

	@Override
	public PedagogicalSession getPreviousPedagogicalSession() {
		return (PedagogicalSession)getPrevious();
	}

	@Override
	public void setPreviousPedagogicalSession(PedagogicalSession pedagogicalSession) {
		setPrevious(pedagogicalSession);
	}
	
	@Override
	public PedagogicalSession getNextPedagogicalSession() {
		return (PedagogicalSession)getNext();
	}

	@Override
	public void setNextPedagogicalSession(PedagogicalSession pedagogicalSession) {
		setNext(pedagogicalSession);
	}

}
