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
package eu.ueb.acem.domain.beans.jpa.vert;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.Room;

/**
 * The Spring Data JPA implementation of Floor domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Entity(name = "Floor")
public class FloorEntity extends PhysicalSpaceEntity implements Floor {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6121113220695042170L;

	private String name;

	private Integer number;

	@ManyToOne(targetEntity = BuildingEntity.class, fetch = FetchType.LAZY)
	private Building building;

	@OneToMany(targetEntity = RoomEntity.class, fetch = FetchType.LAZY, mappedBy = "floor")
	private Set<Room> rooms = new HashSet<Room>(0);

	public FloorEntity() {
	}

	public FloorEntity(Integer number) { 
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