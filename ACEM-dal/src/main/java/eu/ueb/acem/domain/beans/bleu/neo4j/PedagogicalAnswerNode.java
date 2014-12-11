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

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalAnswer")
public class PedagogicalAnswerNode extends AbstractNode implements PedagogicalAnswer {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3066979121350858816L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalNeedNode.class, type = "needAnsweredBy", direction = INCOMING)
	private Set<PedagogicalNeed> pedagogicalNeeds = new HashSet<PedagogicalNeed>(0);

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "answeredUsingResourceCategory", direction = OUTGOING)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	@RelatedTo(elementClass = AdministrativeDepartmentNode.class, type = "answeredByAdministrativeDepartment", direction = OUTGOING)
	private Set<AdministrativeDepartment> administrativeDepartments = new HashSet<AdministrativeDepartment>(0);

	public PedagogicalAnswerNode() {
	}

	public PedagogicalAnswerNode(String name) {
		this();
		setName(name);
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
	public Set<PedagogicalNeed> getNeeds() {
		return pedagogicalNeeds;
	}

	@Override
	public Set<ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}
	
	@Override
	public Set<AdministrativeDepartment> getAdministrativeDepartments() {
		return administrativeDepartments;
	}
	
	@Override
	public Set<PedagogicalScenario> getScenariosRelatedToAnswer() {
		Set<PedagogicalScenario> scenarios = new HashSet<PedagogicalScenario>();
		for (ResourceCategory resourceCategory : resourceCategories) {
			for (PedagogicalActivity pedagogicalActivity : resourceCategory.getPedagogicalActivities()) {
				scenarios.addAll(pedagogicalActivity.getScenarios());
			}
		}
		return scenarios;
	}

	@Override
	public int compareTo(PedagogicalAnswer o) {
		return this.compareTo(o);
	}

}
