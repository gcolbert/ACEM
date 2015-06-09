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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Resource;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Documentation")
public class DocumentationEntity extends ResourceEntity implements Documentation {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2622796336456313913L;

	private String name;

	@ManyToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "resources_documentations")
	private Set<Resource> resources = new HashSet<Resource>(0);

	public DocumentationEntity() {
	}

	public DocumentationEntity(String name, String iconFileName) {
		this();
		setName(name);
		setIconFileName(iconFileName);
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
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
