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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceNode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

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

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(OrganisationNode.class);

	private String shortname;

	private String iconFileName;

	@RelatedTo(elementClass = ResourceNode.class, type = "possessesResource", direction = OUTGOING)
	private Set<Resource> possessedResources = new HashSet<Resource>(0);

	@RelatedTo(elementClass = ResourceNode.class, type = "accessesResource", direction = OUTGOING)
	private Set<Resource> viewedResources = new HashSet<Resource>(0);

	public OrganisationNode() {
	}

	public OrganisationNode(String name) {
		this.setName(name);
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
	public Set<Resource> getPossessedResources() {
		return possessedResources;
	}

	@Override
	public void setPossessedResources(Set<Resource> possessedResources) {
		this.possessedResources = possessedResources;
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
	public int compareTo(Organisation o) {
		return this.getName().compareTo(o.getName());
	}

}
