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
package eu.ueb.acem.domain.beans.bleu.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;
import eu.ueb.acem.domain.beans.violet.neo4j.SeanceDeCoursNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 *
 */
@NodeEntity
@TypeAlias("Scenario")
public class ScenarioNode implements Scenario {

	private static final long serialVersionUID = -5248471016348742765L;

	@GraphId private Long id;
	
	@Indexed(indexName = "indexScenario") private String name;

	private String titre;
	private String objectif;
	
	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "reference", direction = OUTGOING)
	private Set<SeanceDeCours> seancesDeCours;
	
	public ScenarioNode() {
	}

	public ScenarioNode(String name) {
		setName(name);
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

    @Override
	public void setName(String name) {
		this.name = name;
	}
    
	@Override
	public String getObjectif() {
		return objectif;
	}

	@Override
	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	@Override
	public String getTitre() {
		return titre;
	}

	@Override
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	@Override
	public Collection<SeanceDeCours> getSeancesDeCours() {
		return seancesDeCours;
	}
	
}
