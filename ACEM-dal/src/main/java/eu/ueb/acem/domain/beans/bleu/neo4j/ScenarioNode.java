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
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;
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

	@GraphId
	private Long id;

	@Indexed(indexName = "indexOfScenarios")
	private String name;

	private String objective;
	private Boolean published;

	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "scenarioUsedForClass", direction = OUTGOING)
	private Collection<SeanceDeCours> teachingClasses;

	@RelatedTo(elementClass = ActivitePedagogiqueNode.class, type = "activityForScenario", direction = INCOMING)
	private Collection<ActivitePedagogique> pedagogicalActivities;

	@RelatedTo(elementClass = EnseignantNode.class, type="authorsScenario", direction = INCOMING)
	private Personne author;

	public ScenarioNode() {
		published = false;
		pedagogicalActivities = new ArrayList<ActivitePedagogique>();
		teachingClasses = new HashSet<SeanceDeCours>();
	}

	public ScenarioNode(Personne author, String name, String objective) {
		this();
		this.name = name;
		this.author = author;
		this.objective = objective;
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
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
	}

	@Override
	public Collection<ActivitePedagogique> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Collection<ActivitePedagogique> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}

	@Override
	public Personne getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(Personne author) {
		this.author = author;
	}
	
	@Override
	public Boolean isPublished() {
		return published;
	}

	@Override
	public void setPublished(Boolean published) {
		this.published = published;
	}

	@Override
	public Collection<SeanceDeCours> getTeachingClasses() {
		return teachingClasses;
	}

}
