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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

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

	private static final Logger logger = LoggerFactory.getLogger(ScenarioNode.class);

	private static final long serialVersionUID = -5248471016348742765L;

	@GraphId
	private Long id;

	private Long creationDate;

	private Long modificationDate;

	@Indexed(indexName = "indexOfScenarios")
	private String name;

	private String objective;
	private Boolean published;

	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "scenarioUsedForClass", direction = OUTGOING)
	@Fetch
	private Set<SeanceDeCoursNode> teachingClasses;

	@RelatedTo(elementClass = ActivitePedagogiqueNode.class, type = "activityForScenario", direction = INCOMING)
	@Fetch
	private Set<ActivitePedagogiqueNode> pedagogicalActivities;

	@RelatedTo(elementClass = EnseignantNode.class, type = "authorsScenario", direction = INCOMING)
	private Personne author;

	public ScenarioNode() {
		published = false;
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
	public Long getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Long date) {
		this.creationDate = date;
	}

	@Override
	public Long getModificationDate() {
		return modificationDate;
	}

	@Override
	public void setModificationDate(Long date) {
		this.modificationDate = date;
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
	@Transactional
	public void addPedagogicalActivity(ActivitePedagogique pedagogicalActivity) {
		if (!pedagogicalActivities.contains(pedagogicalActivity)) {
			pedagogicalActivities.add((ActivitePedagogiqueNode) pedagogicalActivity);
			pedagogicalActivity.setPositionInScenario(new Long(this.pedagogicalActivities.size() + 1));
		}
		if (!pedagogicalActivity.getScenarios().contains(this)) {
			pedagogicalActivity.addScenario(this);
		}
		renumberPedagogicalActivities();
	}

	@Override
	@Transactional
	public void removePedagogicalActivity(ActivitePedagogique pedagogicalActivity) {
		pedagogicalActivities.remove(pedagogicalActivity);
		pedagogicalActivity.removeScenario(this);
		renumberPedagogicalActivities();
	}

	private void renumberPedagogicalActivities() {
		Long i = new Long(1);
		List<ActivitePedagogiqueNode> list = new ArrayList<ActivitePedagogiqueNode>(pedagogicalActivities);
		Collections.sort(list);
		for (ActivitePedagogiqueNode pedagogicalActivity : list) {
			pedagogicalActivity.setPositionInScenario(i);
			i++;
		}
	}

	@Override
	public Set<? extends ActivitePedagogique> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<? extends ActivitePedagogique> pedagogicalActivities) {
		this.pedagogicalActivities = (Set<ActivitePedagogiqueNode>) pedagogicalActivities;
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
	public Set<? extends SeanceDeCours> getTeachingClasses() {
		return teachingClasses;
	}

	@Override
	public int compareTo(Scenario o) {
		int returnValue;
		if ((getModificationDate() != null) && (o.getModificationDate() != null)) {
			returnValue = getModificationDate().compareTo(o.getModificationDate());
		}
		else {
			if (getModificationDate() != null) {
				returnValue = getModificationDate().compareTo(o.getCreationDate());
			}
			else {
				if (o.getModificationDate() != null) {
					returnValue = getCreationDate().compareTo(o.getModificationDate());
				}
				else {
					returnValue = getCreationDate().compareTo(o.getCreationDate());
				}
			}
		}
		return returnValue;
	}

}
