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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.neo4j.ActivitePedagogiqueNode;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;
import eu.ueb.acem.domain.beans.jaune.ModaliteUtilisation;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Resource")
public abstract class RessourceNode implements Ressource {

	private static final long serialVersionUID = -7922906613944705977L;

	@GraphId
	private Long id;

	private String name;

	private String iconFileName;
	
	private String description;
	
	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "categoryContains", direction = INCOMING)
	@Fetch
	private Set<ResourceCategoryNode> categories;
	
	@RelatedTo(elementClass = ModaliteUtilisationNode.class, type = "resourceHasUseMode", direction = OUTGOING)
	@Fetch
	private Set<ModaliteUtilisationNode> useModes;
	
	@RelatedTo(elementClass = ActivitePedagogiqueNode.class, type="activityRequiringResource", direction = INCOMING)
	@Fetch
	private Set<ActivitePedagogiqueNode> pedagogicalActivities;

	@RelatedTo(elementClass = EnseignantNode.class, type="hasFavoriteResource", direction = INCOMING)
	@Fetch
	private Set<EnseignantNode> teachersHavingThisAsFavoriteResource;
	
	public RessourceNode() {
		categories = new HashSet<ResourceCategoryNode>();
	}

	public RessourceNode(String name) {
		this();
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
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
	public Set<? extends ModaliteUtilisation> getUseModes() {
		return useModes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setUseModes(Set<? extends ModaliteUtilisation> useModes) {
		this.useModes = (Set<ModaliteUtilisationNode>) useModes;
	}

	@Override
	public Set<? extends ActivitePedagogique> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPedagogicalActivities(Set<? extends ActivitePedagogique> pedagogicalActivities) {
		this.pedagogicalActivities = (Set<ActivitePedagogiqueNode>)pedagogicalActivities;
	}

	@Override
	public Set<? extends ResourceCategory> getCategories() {
		return categories;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCategories(Set<? extends ResourceCategory> categories) {
		this.categories = (Set<ResourceCategoryNode>) categories;
	}

	@Override
	public void addCategory(ResourceCategory category) {
		if (! categories.contains(category)) {
			categories.add((ResourceCategoryNode) category);
		}
		if (! category.getResources().contains(this)) {
			category.addResource(this);
		}
	}

	@Override
	public void removeCategory(ResourceCategory category) {
		if (categories.contains(category)) {
			categories.remove(category);
		}
		if (category.getResources().contains(this)) {
			category.removeResource(this);
		}
	}
	
	/*
	@Override
	public Boolean getFavoriteResource(Enseignant teacher) {
		return teachersHavingThisAsFavoriteResource.contains(teacher);
	}
	
	@Override
	public void setFavoriteResource(Enseignant teacher, Boolean favoriteResource) {
		addTeacherHavingThisAsFavoriteResource(teacher);
	}

	@Override
	public Set<? extends Enseignant> getTeachersHavingThisAsFavoriteResource() {
		return teachersHavingThisAsFavoriteResource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTeachersHavingThisAsFavoriteResource(Set<? extends Enseignant> teachers) {
		this.teachersHavingThisAsFavoriteResource = (Set<EnseignantNode>)teachers;
	}

	@Override
	public void addTeacherHavingThisAsFavoriteResource(Enseignant teacher) {
		if (! teachersHavingThisAsFavoriteResource.contains(teacher)) {
			teachersHavingThisAsFavoriteResource.add((EnseignantNode) teacher);
		}
		if (! teacher.getFavoriteResources().contains(this)) {
			teacher.addFavoriteResource(this);
		}
	}

	@Override
	public void removeTeacherHavingThisAsFavoriteResource(Enseignant teacher) {
		if (teachersHavingThisAsFavoriteResource.contains(teacher)) {
			teachersHavingThisAsFavoriteResource.remove(teacher);
		}
		if (teacher.getFavoriteResources().contains(this)) {
			teacher.removeFavoriteResource(this);
		}
	}
	*/
	
	@Override
	public int compareTo(Ressource o) {
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
		RessourceNode other = (RessourceNode) obj;
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
