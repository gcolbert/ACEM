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
package eu.ueb.acem.domain.beans.bleu.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalActivity")
public class PedagogicalActivityNode extends AbstractNode implements PedagogicalActivity {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5248471016348742765L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "activityForScenario", direction = OUTGOING)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "activityRequiringResourceFromCategory", direction = OUTGOING)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	private Long positionInScenario;
	private String objective;
	private String instructions;
	private String duration;

	public PedagogicalActivityNode() {
	}

	public PedagogicalActivityNode(String name) {
		this();
		setName(name);
	}

	@Override
	public Set<PedagogicalScenario> getScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setScenarios(Set<PedagogicalScenario> scenarios) {
		this.pedagogicalScenarios = scenarios;
	}

	@Override
	public Long getPositionInScenario() {
		return positionInScenario;
	}

	@Override
	public void setPositionInScenario(Long positionInScenario) {
		this.positionInScenario = positionInScenario;
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
	public String getInstructions() {
		return instructions;
	}

	@Override
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String getDuration() {
		return duration;
	}

	@Override
	public void setDuration(String duration) {
		this.duration = duration;
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
	public int compareTo(PedagogicalActivity o) {
		return (int) (positionInScenario - o.getPositionInScenario());
	}

}
