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
package eu.ueb.acem.domain.beans.jpa.jaune;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jpa.vert.PhysicalSpaceEntity;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Equipment")
public class EquipmentEntity extends ResourceEntity implements Equipment {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7144722890433684205L;

	private String name;

	private Integer quantity;

	private Boolean mobile;

	//@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "isStoredIn", direction = OUTGOING)
	@ManyToMany(targetEntity = PhysicalSpaceEntity.class, fetch = FetchType.LAZY)
	private Set<PhysicalSpace> storageLocations = new HashSet<PhysicalSpace>(0);

	public EquipmentEntity() {
		super();
	}

	public EquipmentEntity(String name, String iconFileName) {
		this();
		setName(name);
		setIconFileName(iconFileName);
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
	public Set<PhysicalSpace> getStorageLocations() {
		return storageLocations;
	}
	
	@Override
	public void setStorageLocations(Set<PhysicalSpace> storageLocations) {
		this.storageLocations = storageLocations;
	}
	
	@Override
	public Integer getQuantity() {
		return quantity;
	}

	@Override
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public Boolean isMobile() {
		return mobile;
	}

	@Override
	public void setMobile(Boolean isMobile) {
		this.mobile = isMobile;
	}

}
