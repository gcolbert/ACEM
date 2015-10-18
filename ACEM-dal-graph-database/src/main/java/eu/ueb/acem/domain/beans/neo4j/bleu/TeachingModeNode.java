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
package eu.ueb.acem.domain.beans.neo4j.bleu;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;

/**
 * The Spring Data Neo4j implementation of TeachingMode domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-15
 * 
 */
@NodeEntity
@TypeAlias("TeachingMode")
public class TeachingModeNode extends AbstractNode implements TeachingMode {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 5398152884363952782L;

	private String name;

	private String description;

	public TeachingModeNode() {
	}

	public TeachingModeNode(String name, String description) {
		this();
		setName(name);
		setDescription(description);
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
