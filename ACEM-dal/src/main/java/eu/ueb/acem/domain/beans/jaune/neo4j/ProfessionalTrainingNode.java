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

package eu.ueb.acem.domain.beans.jaune.neo4j;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;

/**
 * @author Grégoire Colbert
 * @since 2014-02-06
 * 
 */
@NodeEntity
@TypeAlias("ProfessionalTraining")
public class ProfessionalTrainingNode extends ResourceNode implements ProfessionalTraining {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1012212357185877701L;

	@Indexed
	private String name;

	public ProfessionalTrainingNode() {
	}

	public ProfessionalTrainingNode(String name, String iconFileName) {
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