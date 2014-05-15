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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.domain.beans.rouge.neo4j.ServiceNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalAnswer")
public class ReponseNode implements Reponse {

	private static final long serialVersionUID = 3066979121350858816L;

	@GraphId
	private Long id;

	@Indexed
	private String name;

	@RelatedTo(elementClass = BesoinNode.class, type = "needAnsweredBy", direction = INCOMING)
	@Fetch
	private Set<BesoinNode> needs;

	/*
	@RelatedTo(elementClass = RessourceNode.class, type = "answeredUsingResource", direction = OUTGOING)
	@Fetch
	private Set<RessourceNode> resources;
	*/

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "answeredUsingResourceCategory", direction = OUTGOING)
	@Fetch
	private Set<ResourceCategoryNode> resourceCategories;

	@RelatedTo(elementClass = ServiceNode.class, type = "answeredByAdministrativeDepartment", direction = OUTGOING)
	@Fetch
	private Set<ServiceNode> administrativeDepartments;
	
	public ReponseNode() {
	}

	public ReponseNode(String name) {
		this();
		setName(name);
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Set<? extends Besoin> getNeeds() {
		return needs;
	}

	/*
	@Override
	public Set<? extends Ressource> getResources() {
		return resources;
	}
	*/

	@Override
	public void addNeed(Besoin need) {
		needs.add((BesoinNode)need);
	}

	@Override
	public void removeNeed(Besoin need) {
		needs.remove(need);
	}

	@Override
	public Set<? extends ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}
	
	@Override
	public void addResourceCategory(ResourceCategory resourceCategory) {
		if (!resourceCategories.contains(resourceCategory)) {
			resourceCategories.add((ResourceCategoryNode)resourceCategory);
		}
		if (!resourceCategory.getAnswers().contains(this)) {
			resourceCategory.addAnswer(this);
		}
	}

	@Override
	public void removeResourceCategory(ResourceCategory resourceCategory) {
		if (resourceCategories.contains(resourceCategory)) {
			resourceCategories.remove(resourceCategory);
		}
		if (resourceCategory.getAnswers().contains(this)) {
			resourceCategory.removeAnswer(this);
		}
	}

	@Override
	public Set<? extends Service> getAdministrativeDepartments() {
		return administrativeDepartments;
	}
	
	@Override
	public void addAdministrativeDepartment(Service administrativeDepartment) {
		if (!administrativeDepartments.contains(administrativeDepartment)) {
			administrativeDepartments.add((ServiceNode)administrativeDepartment);
		}
		if (!administrativeDepartment.getPedagogicalAnswers().contains(this)) {
			administrativeDepartment.addPedagogicalAnswer(this);
		}
	}

	@Override
	public void removeAdministrativeDepartment(Service administrativeDepartment) {
		if (administrativeDepartments.contains(administrativeDepartment)) {
			administrativeDepartments.remove(administrativeDepartment);
		}
		if (administrativeDepartment.getPedagogicalAnswers().contains(this)) {
			administrativeDepartment.removePedagogicalAnswer(this);
		}
	}
	
	@Override
	public Set<Scenario> getScenariosRelatedToAnswer() {
		Set<Scenario> scenarios = new HashSet<Scenario>();
		for (ResourceCategory resourceCategory : resourceCategories) {
			for (ActivitePedagogique pedagogicalActivity : resourceCategory.getPedagogicalActivities()) {
				scenarios.addAll(pedagogicalActivity.getScenarios());
			}
		}
		return scenarios;
	}

	@Override
	public int compareTo(Reponse o) {
		return getName().compareTo(o.getName());
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
		ReponseNode other = (ReponseNode) obj;
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
