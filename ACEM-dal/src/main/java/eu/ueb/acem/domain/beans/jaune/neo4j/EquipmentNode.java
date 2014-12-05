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

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;
import eu.ueb.acem.domain.beans.vert.neo4j.PhysicalSpaceNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Equipment")
public class EquipmentNode extends ResourceNode implements Equipment {

	private static final long serialVersionUID = 795694384595044050L;

	@Indexed
	private String name;
	
	private Integer quantity;

	private Boolean mobile;

	@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "isStoredIn", direction = OUTGOING)
	@Fetch
	private Set<PhysicalSpaceNode> storageLocations;
	
	public EquipmentNode() {
	}

	public EquipmentNode(String name, String iconFileName) {
		this();
		setName(name);
		setIconFileName(iconFileName);
	}
	
	@Override
	public Set<? extends PhysicalSpace> getStorageLocations() {
		return storageLocations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setStorageLocations(Set<? extends PhysicalSpace> storageLocations) {
		this.storageLocations = (Set<PhysicalSpaceNode>) storageLocations;
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
