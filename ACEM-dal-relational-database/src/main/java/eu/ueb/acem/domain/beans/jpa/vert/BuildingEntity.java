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
import eu.ueb.acem.domain.beans.vert.Campus;
import eu.ueb.acem.domain.beans.vert.Floor;

/**
 * The Spring Data JPA implementation of Building domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Entity(name = "Building")
public class BuildingEntity extends PhysicalSpaceEntity implements Building {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -18027346679777057L;

	private String name;

	@ManyToOne(targetEntity = CampusEntity.class, fetch = FetchType.LAZY)
	private Campus campus;

	@OneToMany(targetEntity = FloorEntity.class, fetch = FetchType.LAZY, mappedBy = "building")
	private Set<Floor> floors = new HashSet<Floor>(0);

	private Double latitude;
	private Double longitude;
	
	public BuildingEntity() {
	}

	public BuildingEntity(String name) {
		this.name = name;
	}

	public BuildingEntity(String name, Double latitude, Double longitude) {
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
