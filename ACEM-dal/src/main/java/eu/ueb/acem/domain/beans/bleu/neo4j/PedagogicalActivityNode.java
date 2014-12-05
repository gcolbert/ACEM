/**
 *     Copyright Grégoire COLBERT 2013
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

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalActivity")
public class PedagogicalActivityNode implements PedagogicalActivity {

	private static final long serialVersionUID = -5248471016348742765L;

	@GraphId
	private Long id;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "activityForScenario", direction = OUTGOING)
	@Fetch
	private Set<PedagogicalScenarioNode> scenarios;

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "activityRequiringResourceFromCategory", direction = OUTGOING)
	@Fetch
	private Set<ResourceCategoryNode> resourceCategories;

	private Long positionInScenario;
	private String objective;
	private String instructions;
	private String duration;

	public PedagogicalActivityNode() {
	}

	public PedagogicalActivityNode(String name) {
		this();
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Set<? extends PedagogicalScenario> getScenarios() {
		return scenarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setScenarios(Set<? extends PedagogicalScenario> scenarios) {
		this.scenarios = (Set<PedagogicalScenarioNode>) scenarios;
	}
	
	@Override
	@Transactional
	public void addScenario(PedagogicalScenario scenario) {
		if (scenario != null) {
			if (!scenarios.contains(scenario)) {
				scenarios.add((PedagogicalScenarioNode)scenario);
			}
			if (!scenario.getPedagogicalActivities().contains(this)) {
				scenario.addPedagogicalActivity(this);
			}
		}
	}

	@Override
	@Transactional
	public void removeScenario(PedagogicalScenario scenario) {
		scenarios.remove(scenario);
		scenario.removePedagogicalActivity(this);
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
	public Set<? extends ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setResourceCategories(Set<? extends ResourceCategory> resourceCategories) {
		this.resourceCategories = (Set<ResourceCategoryNode>)resourceCategories;
	}

	@Override
	public int compareTo(PedagogicalActivity o) {
		return (int) (positionInScenario - o.getPositionInScenario());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedagogicalActivityNode other = (PedagogicalActivityNode) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		}
		else
			if (!getId().equals(other.getId()))
				return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		}
		else
			if (!getName().equals(other.getName()))
				return false;
		return true;
	}	
}
