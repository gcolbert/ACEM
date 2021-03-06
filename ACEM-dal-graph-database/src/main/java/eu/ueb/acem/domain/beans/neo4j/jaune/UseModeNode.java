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
package eu.ueb.acem.domain.beans.neo4j.jaune;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.rouge.OrganisationNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * The Spring Data Neo4j implementation of UseMode domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("UseMode")
public class UseModeNode extends AbstractNode implements UseMode {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 465146117417875133L;

	@Indexed
	private String name;

	private String description;

	@RelatedTo(elementClass = ResourceNode.class, type = "resourceHasUseMode", direction = INCOMING)
	private Set<Resource> resources = new HashSet<Resource>(0);
	
	@RelatedTo(elementClass = OrganisationNode.class, type = "refersToOrganisation", direction = OUTGOING)
	private Organisation referredOrganisation;

	public UseModeNode() {
	}

	public UseModeNode(String name) {
		this.setName(name);
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
	public Set<Resource> getResources() {
		return resources;
	}

	@Override
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public Organisation getReferredOrganisation() {
		return referredOrganisation;
	}

	@Override
	public void setReferredOrganisation(Organisation organisation) {
		this.referredOrganisation = organisation;
	}

}
