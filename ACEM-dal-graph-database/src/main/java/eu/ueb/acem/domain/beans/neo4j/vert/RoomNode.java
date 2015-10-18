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

import static org.neo4j.graphdb.Direction.OUTGOING;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.Room;

/**
 * The Spring Data Neo4j implementation of Room domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Room")
public class RoomNode extends PhysicalSpaceNode implements Room {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7525107899854074242L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = FloorNode.class, type = "isPartOfFloor", direction = OUTGOING)
	private Floor floor;

	private String number;
	private Boolean hasWifiAccess;
	private Integer roomCapacity;

	public RoomNode() {
	}

	public RoomNode(String number, Integer roomCapacity, Boolean hasWifiAccess) {
		this.number = number;
		this.roomCapacity = roomCapacity;
		this.hasWifiAccess = hasWifiAccess;
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
	public String getNumber() {
		return number;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public Floor getFloor() {
		return floor;
	}
	
	@Override
	public Integer getRoomCapacity() {
		return roomCapacity;
	}

	@Override
	public void setRoomCapacity(Integer roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	@Override
	public Boolean getHasWifiAccess() {
		return hasWifiAccess;
	}

	@Override
	public void setHasWifiAccess(Boolean hasWifiAccess) {
		this.hasWifiAccess = hasWifiAccess;
	}

}