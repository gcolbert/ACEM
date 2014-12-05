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
package eu.ueb.acem.domain.beans.violet.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.violet.Diploma;
import eu.ueb.acem.domain.beans.violet.Credit;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Credit")
public class CreditNode implements Credit {

	private static final long serialVersionUID = 8179710397691380136L;

	@GraphId
	private Long id;

	@Indexed
	private String name;

	private String duree;

	@RelatedTo(elementClass = DiplomaNode.class, type = "isPartOfDiploma", direction = OUTGOING)
	private Set<DiplomaNode> diplomas;

	public CreditNode() {
	}

	public CreditNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duree;
	}

	public void setDuration(String duration) {
		this.duree = duration;
	}

	@Override
	public Set<? extends Diploma> getDiplomas() {
		return diplomas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDiplomas(Set<? extends Diploma> diplomas) {
		this.diplomas = (Set<DiplomaNode>)diplomas;
	}

}
