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
package eu.ueb.acem.domain.beans.rouge.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation")
public abstract class OrganisationNode implements Organisation {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4961037643458063514L;

	@GraphId
	private Long id;

	@Indexed
	private String name;

	private String shortname;

	private String iconFileName;

	@RelatedTo(elementClass = ResourceNode.class, type = "possessesResource", direction = OUTGOING)
	@Fetch
	private Set<ResourceNode> possessedResources;

	@RelatedTo(elementClass = ResourceNode.class, type = "accessesResource", direction = OUTGOING)
	@Fetch
	private Set<ResourceNode> viewedResources;

	public OrganisationNode() {
		/*-
		possessedResources = new HashSet<RessourceNode>();
		viewedResources = new HashSet<RessourceNode>();
		 */
	}

	public OrganisationNode(String name) {
		this.setName(name);
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
	public String getShortname() {
		return shortname;
	}

	@Override
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public Set<? extends Resource> getPossessedResources() {
		return possessedResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPossessedResources(Set<? extends Resource> possessedResources) {
		this.possessedResources = (Set<ResourceNode>) possessedResources;
	}

	@Override
	public Set<? extends Resource> getViewedResources() {
		return viewedResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setViewedResources(Set<? extends Resource> viewedResources) {
		this.viewedResources = (Set<ResourceNode>) viewedResources;
	}

	@Override
	public void addViewedResource(Resource resource) {
		if (!viewedResources.contains(resource)) {
			viewedResources.add((ResourceNode) resource);
		}
		if (!resource.getOrganisationsHavingAccessToResource().contains(this)) {
			resource.addOrganisationHavingAccessToResource(this);
		}
	}

	@Override
	public void removeViewedResource(Resource resource) {
		if (viewedResources.contains(resource)) {
			viewedResources.remove(resource);
		}
		if (resource.getOrganisationsHavingAccessToResource().contains(this)) {
			resource.removeOrganisationHavingAccessToResource(this);
		}
	}

	@Override
	public int compareTo(Organisation o) {
		return this.getName().compareTo(o.getName());
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
		OrganisationNode other = (OrganisationNode) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		}
		else if (!getId().equals(other.getId()))
			return false;
//		if (getName() == null) {
//			if (other.getName() != null)
//				return false;
//		}
//		else if (!getName().equals(other.getName()))
//			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrganisationNode#" + hashCode() + "[id=[" + getId() + "], name=[" + getName() + "], shortname=["
				+ shortname + "], iconFileName=[" + iconFileName + "]]";
	}
}
