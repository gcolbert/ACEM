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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.neo4j.TeacherNode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.neo4j.ClassNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalScenario")
public class PedagogicalScenarioNode extends AbstractNode implements PedagogicalScenario {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1233433427413840564L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalScenarioNode.class);

	private Long creationDate;

	private Long modificationDate;

	@Indexed
	private String name;

	private String objective;
	private String evaluationModes;
	private Boolean published;

	@RelatedTo(elementClass = ClassNode.class, type = "scenarioUsedForClass", direction = OUTGOING)
	private Set<Class> classes = new HashSet<Class>(0);

	@RelatedTo(elementClass = PedagogicalActivityNode.class, type = "activityForScenario", direction = INCOMING)
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	@RelatedTo(elementClass = TeacherNode.class, type = "authorsScenario", direction = INCOMING)
	private Set<Teacher> authors = new HashSet<Teacher>(0);

	public PedagogicalScenarioNode() {
		published = false;
	}

	public PedagogicalScenarioNode(String name, String objective) {
		this();
		setName(name);
		this.objective = objective;
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
	public String getEvaluationModes() {
		return evaluationModes;
	}

	@Override
	public void setEvaluationModes(String evaluationModes) {
		this.evaluationModes = evaluationModes;
	}

//	@Override
//	public void addPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
//		if (!pedagogicalActivities.contains(pedagogicalActivity)) {
//			pedagogicalActivities.add(pedagogicalActivity);
//			pedagogicalActivity.setPositionInScenario(new Long(this.pedagogicalActivities.size() + 1));
//		}
//		if (!pedagogicalActivity.getScenarios().contains(this)) {
//			pedagogicalActivity.addScenario(this);
//		}
//		renumberPedagogicalActivities();
//	}
//
//	@Override
//	public void removePedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
//		pedagogicalActivities.remove(pedagogicalActivity);
//		pedagogicalActivity.removeScenario(this);
//		renumberPedagogicalActivities();
//	}
//
//	private void renumberPedagogicalActivities() {
//		Long i = new Long(1);
//		List<PedagogicalActivity> list = new ArrayList<PedagogicalActivity>(pedagogicalActivities);
//		Collections.sort(list);
//		for (PedagogicalActivity pedagogicalActivity : list) {
//			pedagogicalActivity.setPositionInScenario(i);
//			i++;
//		}
//	}

	@Override
	public Set<PedagogicalActivity> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}

	@Override
	public Set<Teacher> getAuthors() {
		return authors;
	}

	@Override
	public void setAuthors(Set<Teacher> authors) {
		this.authors = authors;
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
	public Set<Class> getClasses() {
		return classes;
	}

	@Override
	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}
	
	@Override
	public int compareTo(PedagogicalScenario o) {
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
