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

import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Pedagogical_activity")
public class ActivitePedagogiqueNode implements ActivitePedagogique {

	private static final long serialVersionUID = -5248471016348742765L;

	@GraphId
	private Long id;

	@Indexed(indexName = "indexOfPedagogicalActivities")
	private String name;

	@RelatedTo(elementClass = ScenarioNode.class, type = "activityForScenario", direction = OUTGOING)
	private Collection<Scenario> scenarios;

	@RelatedTo(elementClass = RessourceNode.class, type = "activityRequiringResource", direction = OUTGOING)
	private Collection<Ressource> resources;

	private Long positionInScenario;
	private String objective;
	private String description;
	private String duration;

	public ActivitePedagogiqueNode() {
		resources = new HashSet<Ressource>();
	}

	public ActivitePedagogiqueNode(String name) {
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
	public Collection<Scenario> getScenarios() {
		return scenarios;
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
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
	public Collection<Ressource> getResources() {
		return resources;
	}

	@Override
	public void setResources(Collection<Ressource> resources) {
		this.resources = resources;
	}

	@Override
	public int compareTo(ActivitePedagogique o) {
		return (int) (positionInScenario - o.getPositionInScenario());
	}

}
