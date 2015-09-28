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

package eu.ueb.acem.domain.beans.jpa.jaune;

import javax.persistence.Entity;

import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "ProfessionalTraining")
public class ProfessionalTrainingEntity extends ResourceEntity implements ProfessionalTraining {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3592006743976271352L;

	private String name;

	public ProfessionalTrainingEntity() {
	}

	public ProfessionalTrainingEntity(String name, String iconFileName) {
		this();
		setName(name);
		setIconFileName(iconFileName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
