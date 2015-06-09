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
package eu.ueb.acem.domain.beans.neo4j.rouge;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.UseModeNode;
import eu.ueb.acem.domain.beans.neo4j.vert.PhysicalSpaceNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation")
public abstract class OrganisationNode extends AbstractNode implements Organisation {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4961037643458063514L;

	private String shortname;

	private String iconFileName;

	private String contactMode;

	@RelatedTo(elementClass = ResourceNode.class, type = "possessesResource", direction = OUTGOING)
	private Set<Resource> possessedResources = new HashSet<Resource>(0);

	@RelatedTo(elementClass = ResourceNode.class, type = "supportsResource", direction = OUTGOING)
	private Set<Resource> supportedResources = new HashSet<Resource>(0);

	@RelatedTo(elementClass = ResourceNode.class, type = "accessesResource", direction = OUTGOING)
	private Set<Resource> viewedResources = new HashSet<Resource>(0);

	@RelatedTo(elementClass = UseModeNode.class, type = "referredOrganisations", direction = INCOMING)
	private Set<UseMode> referringUseModes = new HashSet<UseMode>(0);

	@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "occupies", direction = OUTGOING)
	private Set<PhysicalSpace> occupiedPhysicalSpaces = new HashSet<PhysicalSpace>(0);

	public OrganisationNode() {
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
	public String getContactMode() {
		return contactMode;
	}

	@Override
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	@Override
	public Set<Resource> getPossessedResources() {
		return possessedResources;
	}

	@Override
	public void setPossessedResources(Set<Resource> possessedResources) {
		this.possessedResources = possessedResources;
	}

	@Override
	public Set<Resource> getSupportedResources() {
		return supportedResources;
	}

	@Override
	public void setSupportedResources(Set<Resource> supportedResources) {
		this.supportedResources = supportedResources;
	}

	@Override
	public Set<Resource> getViewedResources() {
		return viewedResources;
	}

	@Override
	public void setViewedResources(Set<Resource> viewedResources) {
		this.viewedResources = viewedResources;
	}

	@Override
	public Set<UseMode> getUseModes() {
		return referringUseModes;
	}

	@Override
	public void setUseModes(Set<UseMode> referringUseModes) {
		this.referringUseModes = referringUseModes;
	}

	@Override
	public Set<PhysicalSpace> getOccupiedPhysicalSpaces() {
		return occupiedPhysicalSpaces;
	}

	@Override
	public void setOccupiedPhysicalSpaces(Set<PhysicalSpace> occupiedPhysicalSpaces) {
		this.occupiedPhysicalSpaces = occupiedPhysicalSpaces;
	}

	@Override
	public int compareTo(Organisation o) {
		return this.getName().compareTo(o.getName());
	}

}
