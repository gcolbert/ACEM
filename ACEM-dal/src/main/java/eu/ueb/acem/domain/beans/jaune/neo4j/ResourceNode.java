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
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.neo4j.OrganisationNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Resource")
public abstract class ResourceNode extends AbstractNode implements Resource {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7922906613944705977L;

	private String iconFileName;
	
	private String description;
	
	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "categoryContains", direction = INCOMING)
	private Set<ResourceCategory> categories = new HashSet<ResourceCategory>(0);
	
	@RelatedTo(elementClass = UseModeNode.class, type = "resourceHasUseMode", direction = OUTGOING)
	private Set<UseMode> useModes = new HashSet<UseMode>(0);
	
	@RelatedTo(elementClass = OrganisationNode.class, type = "possessesResource", direction = INCOMING)
	private Organisation organisationPossessingResource;

	@RelatedTo(elementClass = OrganisationNode.class, type = "supportsResource", direction = INCOMING)
	private Organisation organisationSupportingResource;
	
	@RelatedTo(elementClass = OrganisationNode.class, type = "accessesResource", direction = INCOMING)
	private Set<Organisation> organisationsHavingAccessToResource = new HashSet<Organisation>(0);

	@RelatedTo(elementClass = DocumentationNode.class, type = "documents", direction = INCOMING)
	private Set<Documentation> documentations;

	public ResourceNode() {
	}

	public ResourceNode(String name) {
		this();
		setName(name);
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
	public Set<UseMode> getUseModes() {
		return useModes;
	}
	
	@Override
	public void setUseModes(Set<UseMode> useModes) {
		this.useModes = useModes;
	}

	@Override
	public Set<ResourceCategory> getCategories() {
		return categories;
	}

	@Override
	public void setCategories(Set<ResourceCategory> categories) {
		this.categories = categories;
	}

	@Override
	public Organisation getOrganisationPossessingResource() {
		return organisationPossessingResource;
	}
	
	@Override
	public void setOrganisationPossessingResource(Organisation organisation) {
		this.organisationPossessingResource = (OrganisationNode)organisation;
	}

	@Override
	public Organisation getOrganisationSupportingResource() {
		return organisationSupportingResource;
	}

	@Override
	public void setOrganisationSupportingResource(Organisation organisation) {
		this.organisationSupportingResource = (OrganisationNode)organisation;
	}

	@Override
	public Set<Organisation> getOrganisationsHavingAccessToResource() {
		return organisationsHavingAccessToResource;
	}

	@Override
	public void setOrganisationsHavingAccessToResource(Set<Organisation> organisations) {
		this.organisationsHavingAccessToResource = organisations;
	}

	@Override
	public Set<Documentation> getDocumentations() {
		return documentations;
	}

	@Override
	public void setDocumentations(Set<Documentation> documentations) {
		this.documentations = documentations;
	}

	@Override
	public int compareTo(Resource o) {
		return getName().compareTo(o.getName());
	}

}
