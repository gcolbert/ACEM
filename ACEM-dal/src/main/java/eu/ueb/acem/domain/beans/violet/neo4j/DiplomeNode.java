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

import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.violet.Diplome;
import eu.ueb.acem.domain.beans.violet.UniteEnseignement;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Diploma")
public class DiplomeNode implements Diplome {

	private static final long serialVersionUID = 3007792198756655816L;

	@GraphId
	private Long id;

	@RelatedTo(elementClass = UniteEnseignementNode.class, type = "isPartOfDiploma", direction = INCOMING)
	@Fetch
	private Set<UniteEnseignementNode> credits;
	
	@Indexed
	private String name;

	public DiplomeNode() {
	}

	public DiplomeNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Set<? extends UniteEnseignementNode> getCredits() {
		return credits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCredits(Set<? extends UniteEnseignement> credits) {
		this.credits = (Set<UniteEnseignementNode>) credits;
	}
	
}
