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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

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

/**
 * The Spring Data Neo4j implementation of PedagogicalActivity domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalActivity")
public class PedagogicalActivityNode extends PedagogicalUnitNode implements PedagogicalActivity {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5248471016348742765L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalSequenceNode.class, type = "activityForSequence", direction = OUTGOING)
	private PedagogicalSequence pedagogicalSequence;

	@RelatedTo(elementClass = PedagogicalSessionNode.class, type = "activityForSession", direction = OUTGOING)
	private PedagogicalSession pedagogicalSession;

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "activityRequiringResourceFromCategory", direction = OUTGOING)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	@RelatedTo(elementClass = PedagogicalActivityNode.class, type = "next", direction = INCOMING)
	private PedagogicalActivity previousPedagogicalActivity;

	@RelatedTo(elementClass = PedagogicalActivityNode.class, type = "next", direction = OUTGOING)
	private PedagogicalActivity nextPedagogicalActivity;

	private String instructions;

	public PedagogicalActivityNode() {
	}

	public PedagogicalActivityNode(String name) {
		this();
		setName(name);
	}

	public PedagogicalActivityNode(String name, String objective) {
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
		return previousPedagogicalActivity;
	}

	@Override
	public void setPreviousPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		this.previousPedagogicalActivity = pedagogicalActivity;
		if (pedagogicalActivity != null
				&& ((pedagogicalActivity.getNextPedagogicalActivity() == null) || ((pedagogicalActivity
						.getNextPedagogicalActivity() != null) && (!pedagogicalActivity.getNextPedagogicalActivity()
						.equals(this))))) {
			pedagogicalActivity.setNextPedagogicalActivity(this);
		}
	}

	@Override
	public PedagogicalActivity getNextPedagogicalActivity() {
		return nextPedagogicalActivity;
	}

	@Override
	public void setNextPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		this.nextPedagogicalActivity = pedagogicalActivity;
		if (pedagogicalActivity != null
				&& ((pedagogicalActivity.getPreviousPedagogicalActivity() == null) || ((pedagogicalActivity
						.getPreviousPedagogicalActivity() != null) && (!pedagogicalActivity
						.getPreviousPedagogicalActivity().equals(this))))) {
			pedagogicalActivity.setPreviousPedagogicalActivity(this);
		}
	}

}
