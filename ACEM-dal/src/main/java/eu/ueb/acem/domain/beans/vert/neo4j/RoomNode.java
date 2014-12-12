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

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import eu.ueb.acem.domain.beans.vert.Room;

/**
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
	
	private String numero;
	private Boolean accesWifi;
	private Integer capaciteAccueil;

	public RoomNode() {
	}

	public RoomNode(String numero, Integer capaciteAccueil, Boolean accesWifi) {
		this.numero = numero;
		this.capaciteAccueil = capaciteAccueil;
		this.accesWifi = accesWifi;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getCapaciteAccueil() {
		return capaciteAccueil;
	}

	public void setCapaciteAccueil(Integer capaciteAccueil) {
		this.capaciteAccueil = capaciteAccueil;
	}

	public Boolean getAccesWifi() {
		return accesWifi;
	}

	public void setAccesWifi(Boolean accesWifi) {
		this.accesWifi = accesWifi;
	}

}