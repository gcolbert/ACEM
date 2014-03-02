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

import java.util.Collection;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.violet.Diplome;
import eu.ueb.acem.domain.beans.violet.UniteEnseignement;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Credit")
public class UniteEnseignementNode implements UniteEnseignement {

	private static final long serialVersionUID = 8179710397691380136L;

	@GraphId
	private Long id;

	//@Indexed(indexType = IndexType.SIMPLE)
	@Indexed
	private String name;

	private String duree;

	@RelatedTo(elementClass = DiplomeNode.class, type = "isPartOfDiploma", direction = OUTGOING)
	private Collection<Diplome> diplomas;

	public UniteEnseignementNode() {
	}

	public UniteEnseignementNode(String name) {
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

	public Collection<Diplome> getDiplomas() {
		return diplomas;
	}

	public void setDiplomas(Collection<Diplome> diplomas) {
		this.diplomas = diplomas;
	}

}
