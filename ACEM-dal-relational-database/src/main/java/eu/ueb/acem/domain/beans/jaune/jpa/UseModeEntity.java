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
package eu.ueb.acem.domain.beans.jaune.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.jpa.OrganisationEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "UseMode")
@Table(name = "UseMode")
public class UseModeEntity extends AbstractEntity implements UseMode {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1562548027752522785L;

	private String name;

	private String description;

	//@RelatedTo(elementClass = ResourceNode.class, type = "resourceHasUseMode", direction = INCOMING)
	@ManyToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY)
	private Set<Resource> resources = new HashSet<Resource>(0);

	//@RelatedTo(elementClass = OrganisationNode.class, type = "refersToOrganisation", direction = OUTGOING)
	@ManyToOne(targetEntity = OrganisationEntity.class, fetch = FetchType.LAZY)
	private Organisation referredOrganisation;

	public UseModeEntity() {
	}

	public UseModeEntity(String name) {
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
