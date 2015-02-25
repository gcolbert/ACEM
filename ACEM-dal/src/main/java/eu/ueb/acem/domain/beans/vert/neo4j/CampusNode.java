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
package eu.ueb.acem.domain.beans.vert.neo4j;

import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.Campus;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Campus")
public class CampusNode extends PhysicalSpaceNode implements Campus {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6200928533285074013L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = BuildingNode.class, type = "isInCampus", direction = INCOMING)
	private Set<Building> buildings = new HashSet<Building>(0);

	private Double latitude;
	private Double longitude;

	public CampusNode() {
	}
	
	public CampusNode(String name) {
		this.name = name;
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
	public Set<Building> getBuildings() {
		return buildings;
	}

	@Override
	public void setBuildings(Set<Building> buildings) {
		this.buildings = buildings;
	}

	@Override
	public Double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public Double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
