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
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.Room;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Floor")
public class FloorNode extends PhysicalSpaceNode implements Floor {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 444689555923558074L;

	@Indexed
	private String name;

	private Integer number;

	@RelatedTo(elementClass = BuildingNode.class, type = "isPartOfBuilding", direction = OUTGOING)
	private Building building;

	@RelatedTo(elementClass = RoomNode.class, type = "isPartOfFloor", direction = INCOMING)
	private Set<Room> rooms = new HashSet<Room>(0);

	public FloorNode() {
	}

	public FloorNode(Integer number) { 
		this.number = number;
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
	public Integer getNumber() {
		return number;
	}

	@Override
	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public Building getBuilding() {
		return building;
	}

	@Override
	public Set<Room> getRooms() {
		return rooms;
	}

	@Override
	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

}