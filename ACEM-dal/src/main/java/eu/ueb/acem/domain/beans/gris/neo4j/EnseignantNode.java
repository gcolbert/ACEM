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
package eu.ueb.acem.domain.beans.gris.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Collection;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;
import eu.ueb.acem.domain.beans.violet.neo4j.SeanceDeCoursNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Teacher")
public class EnseignantNode extends PersonneNode implements Enseignant {

	private static final long serialVersionUID = -3193454107919543890L;

	@Indexed(indexName = "indexOfTeachers")
	private String name;

	@RelatedTo(elementClass = RessourceNode.class, type = "hasFavorite", direction = OUTGOING)
	private Collection<Ressource> ressourcesFavorites;

	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "leadsClass", direction = OUTGOING)
	private Collection<SeanceDeCours> seancesDeCours;

	public EnseignantNode() {
	}

	public EnseignantNode(String name) {
		this.name = name;
	}

	@Override
	public Collection<Ressource> getFavoriteResources() {
		return ressourcesFavorites;
	}

	@Override
	public Collection<SeanceDeCours> getTeachingClasses() {
		return seancesDeCours;
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
