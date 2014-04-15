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

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation")
public abstract class OrganisationNode implements Organisation {

	private static final long serialVersionUID = -4961037643458063514L;

	@GraphId
	private Long id;

	@Indexed
	private String name;
	
	private String shortname;
	
	private String iconFileName;

	@RelatedTo(elementClass = RessourceNode.class, type = "possessesResource", direction = OUTGOING)
	@Fetch
	private Set<RessourceNode> possessedResources;

	@RelatedTo(elementClass = RessourceNode.class, type = "viewsResource", direction = OUTGOING)
	@Fetch
	private Set<RessourceNode> viewedResources;

	public OrganisationNode() {
		possessedResources = new HashSet<RessourceNode>();
		viewedResources = new HashSet<RessourceNode>();
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
	public Set<? extends Ressource> getPossessedResources() {
		return possessedResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPossessedResources(Set<? extends Ressource> possessedResources) {
		this.possessedResources = (Set<RessourceNode>) possessedResources;
	}
	
	@Override
	public Set<? extends Ressource> getViewedResources() {
		return viewedResources;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setViewedResources(Set<? extends Ressource> viewedResources) {
		this.viewedResources = (Set<RessourceNode>) viewedResources;
	}

	@Override
	public int compareTo(Organisation o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else
			if (!id.equals(other.id))
				return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		}
		else
			if (!name.equals(other.getName()))
				return false;
		return true;
	}
	
}
