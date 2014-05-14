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

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;
import eu.ueb.acem.domain.beans.violet.neo4j.SeanceDeCoursNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Teacher")
public class EnseignantNode extends PersonneNode implements Enseignant {

	private static final long serialVersionUID = -3193454107919543890L;

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "hasFavoriteToolCategory", direction = OUTGOING)
	@Fetch
	private Set<ResourceCategoryNode> favoriteToolCategories;

	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "leadsClass", direction = OUTGOING)
	@Fetch
	private Set<SeanceDeCoursNode> teachingClasses;

	@RelatedTo(elementClass = ScenarioNode.class, type = "authorsScenario", direction = OUTGOING)
	@Fetch
	private Set<ScenarioNode> scenarios;

	public EnseignantNode() {
	}

	public EnseignantNode(String name, String login) {
		super(name, login);
	}

	/*-
	@Override
	public Set<? extends Ressource> getFavoriteResources() {
		return favoriteResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setFavoriteResources(Set<? extends Ressource> favoriteResources) {
		this.favoriteResources = (Set<RessourceNode>) favoriteResources;
	}
	
	@Override
	public Boolean addFavoriteResource(Ressource resource) {
		if (! favoriteResources.contains(resource)) {
			favoriteResources.add((RessourceNode) resource);
		}
		return favoriteResources.contains(resource);
	}

	@Override
	public Boolean removeFavoriteResource(Ressource resource) {
		if (favoriteResources.contains(resource)) {
			favoriteResources.remove(resource);
		}
		return ! favoriteResources.contains(resource);
	}
	*/

	@Override
	public Set<? extends ResourceCategory> getFavoriteToolCategories() {
		return favoriteToolCategories;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setFavoriteToolCategories(Set<? extends ResourceCategory> favoriteToolCategories) {
		this.favoriteToolCategories = (Set<ResourceCategoryNode>) favoriteToolCategories;
	}
	
	@Override
	public Boolean addFavoriteToolCategory(ResourceCategory toolCategory) {
		if (! favoriteToolCategories.contains(toolCategory)) {
			favoriteToolCategories.add((ResourceCategoryNode) toolCategory);
		}
		return favoriteToolCategories.contains(toolCategory);
	}

	@Override
	public Boolean removeFavoriteToolCategory(ResourceCategory toolCategory) {
		if (favoriteToolCategories.contains(toolCategory)) {
			favoriteToolCategories.remove(toolCategory);
		}
		return ! favoriteToolCategories.contains(toolCategory);
	}
	
	@Override
	public Set<? extends Scenario> getScenarios() {
		return scenarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setScenarios(Set<? extends Scenario> scenarios) {
		this.scenarios = (Set<ScenarioNode>) scenarios;
	}
	
	@Override
	public void addAuthor(Scenario scenario) {
		scenarios.add((ScenarioNode)scenario);
	}

	@Override
	public void removeAuthor(Scenario scenario) {
		scenarios.remove(scenario);
	}

	@Override
	public Set<? extends SeanceDeCours> getTeachingClasses() {
		return teachingClasses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTeachingClasses(Set<? extends SeanceDeCours> teachingClasses) {
		this.teachingClasses = (Set<SeanceDeCoursNode>) teachingClasses;
	}

}
