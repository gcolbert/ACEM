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
package eu.ueb.acem.domain.beans.neo4j.vert;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.Campus;
import eu.ueb.acem.domain.beans.vert.Floor;

/**
 * The Spring Data Neo4j implementation of Building domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Building")
public class BuildingNode extends PhysicalSpaceNode implements Building {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7469182359332815932L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = CampusNode.class, type = "isInCampus", direction = OUTGOING)
	private Campus campus;

	@RelatedTo(elementClass = FloorNode.class, type = "isPartOfBuilding", direction = INCOMING)
	private Set<Floor> floors = new HashSet<Floor>(0);

	private Double latitude;
	private Double longitude;
	
	public BuildingNode() {
	}

	public BuildingNode(String name) {
		this.name = name;
	}

	public BuildingNode(String name, Double latitude, Double longitude) {
		this(name);
		this.latitude = latitude;
		this.longitude = longitude;
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
	public Campus getCampus() {
		return campus;
	}

	@Override
	public Set<Floor> getFloors() {
		return floors;
	}

	@Override
	public void setFloors(Set<Floor> floors) {
		this.floors = floors;
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
