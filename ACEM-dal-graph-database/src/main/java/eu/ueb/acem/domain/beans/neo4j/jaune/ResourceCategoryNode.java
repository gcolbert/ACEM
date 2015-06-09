/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.domain.beans.neo4j.jaune;

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
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalActivityNode;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalAnswerNode;

/**
 * @author Grégoire Colbert
 * @since 2014-04-09
 * 
 */
@NodeEntity
@TypeAlias("ResourceCategory")
public class ResourceCategoryNode extends AbstractNode implements ResourceCategory {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -9101136355869813825L;

	@Indexed
	private String name;
	
	private String iconFileName;
	
	private String description;

	@RelatedTo(elementClass = PedagogicalAnswerNode.class, type = "answeredUsingResourceCategory", direction = INCOMING)
	private Set<PedagogicalAnswer> answers = new HashSet<PedagogicalAnswer>(0);
	
	@RelatedTo(elementClass = ResourceNode.class, type = "categoryContains", direction = OUTGOING)
	private Set<Resource> resources = new HashSet<Resource>(0);
	
	@RelatedTo(elementClass = PedagogicalActivityNode.class, type="activityRequiringResourceFromCategory", direction = INCOMING)
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	public ResourceCategoryNode() {
	}

	public ResourceCategoryNode(String name, String description, String iconFileName) {
		setName(name);
		setDescription(description);
		setIconFileName(iconFileName);
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}		
	
	@Override
	public Set<PedagogicalAnswer> getAnswers() {
		return answers;
	}
	
	@Override
	public void setAnswers(Set<PedagogicalAnswer> answers) {
		this.answers = answers;
	}
	
	@Override
	public Set<Resource> getResources() {
		return resources;
	}

	@Override
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public Set<PedagogicalActivity> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}
	
	@Override
	public int compareTo(ResourceCategory o) {
		return getName().compareTo(o.getName());
	}
	
}
