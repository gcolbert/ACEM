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

import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.vert.EspacePhysique;
import eu.ueb.acem.domain.beans.vert.neo4j.EspacePhysiqueNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Equipment")
public class EquipementNode extends RessourceNode implements Equipement {

	private static final long serialVersionUID = 795694384595044050L;

	@Indexed
	private String name;
	
	private Integer quantity;

	private Boolean mobile;

	@RelatedTo(elementClass = EspacePhysiqueNode.class, type = "isStoredIn", direction = OUTGOING)
	@Fetch
	private Set<EspacePhysiqueNode> storageLocations;
	
	public EquipementNode() {
	}

	public EquipementNode(String name) {
		setName(name);
	}
	
	@Override
	public Set<? extends EspacePhysique> getStorageLocations() {
		return storageLocations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setStorageLocations(Set<? extends EspacePhysique> storageLocations) {
		this.storageLocations = (Set<EspacePhysiqueNode>) storageLocations;
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
