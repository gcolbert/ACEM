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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.Room;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Room")
public class RoomEntity extends PhysicalSpaceEntity implements Room {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8879898838465047165L;

	private String name;

	@ManyToOne(targetEntity = FloorEntity.class, fetch = FetchType.LAZY)
	private Floor floor;

	private String number;
	private Boolean hasWifiAccess;
	private Integer roomCapacity;

	public RoomEntity() {
	}

	public RoomEntity(String number, Integer roomCapacity, Boolean hasWifiAccess) {
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