/**
 *     Copyright Grégoire COLBERT 2013
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free equipment: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free equipment Foundation, either version 3 of the License, or
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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class EquipmentViewBean implements ResourceViewBean, Serializable, Comparable<EquipmentViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private Equipement equipment;

	private Long id;

	private String name;

	private Boolean favoriteResource;
	
	public EquipmentViewBean() {
	}

	public EquipmentViewBean(Equipement equipment) {
		this();
		setEquipment(equipment);
	}

	@Override
	public Equipement getDomainBean() {
		return equipment;
	}
	
	@Override
	public void setDomainBean(Ressource resource) {
		setEquipment((Equipement) resource);
	}

	public Equipement getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipement equipment) {
		this.equipment = equipment;
		setId(equipment.getId());
		setName(equipment.getName());
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Boolean getFavoriteResource() {
		return favoriteResource;
	}
	
	public void setFavoriteResource(Boolean favoriteResource) {
		this.favoriteResource = favoriteResource;
	}
	
	@Override
	public int compareTo(EquipmentViewBean o) {
		return name.compareTo(o.getName());
	}

}
