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
package eu.ueb.acem.domain.beans.jaune.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-04-09
 * 
 */
@NodeEntity
@TypeAlias("ResourceCategory")
public class ResourceCategoryNode implements ResourceCategory {

	private static final long serialVersionUID = -9101136355869813825L;

	@GraphId
	private Long id;
	
	@Indexed
	private String name;

	@RelatedTo(elementClass = ReponseNode.class, type = "answeredUsingResourceCategory", direction = INCOMING)
	@Fetch
	private Set<ReponseNode> answers;
	
	@RelatedTo(elementClass = RessourceNode.class, type = "categoryContains", direction = OUTGOING)
	@Fetch
	private Set<RessourceNode> resources;
	
	public ResourceCategoryNode() {
	}

	public ResourceCategoryNode(String name) {
		setName(name);
	}

	@Override
	public Long getId() {
		return id;
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
	public Set<? extends Reponse> getAnswers() {
		return answers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setAnswers(Set<? extends Reponse> answers) {
		this.answers = (Set<ReponseNode>) answers;
	}
	
	@Override
	public Set<? extends Ressource> getResources() {
		return resources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setResources(Set<? extends Ressource> resources) {
		this.resources = (Set<RessourceNode>) resources;
	}

	@Override
	public void addResource(Ressource resource) {
		if (! resources.contains(resource)) {
			resources.add((RessourceNode) resource);
		}
		if (! resource.getCategories().contains(this)) {
			resource.addCategory(this);
		}
	}

	@Override
	public void removeResource(Ressource resource) {
		if (resources.contains(resource)) {
			resources.remove(resource);
		}
		if (resource.getCategories().contains(this)) {
			resource.removeCategory(this);
		}
	}

	@Override
	public int compareTo(ResourceCategory o) {
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
		ResourceCategoryNode other = (ResourceCategoryNode) obj;
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
