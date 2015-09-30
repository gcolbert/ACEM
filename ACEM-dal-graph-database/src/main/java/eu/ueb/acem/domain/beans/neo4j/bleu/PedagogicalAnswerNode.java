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
package eu.ueb.acem.domain.beans.neo4j.bleu;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceCategoryNode;

/**
 * The Spring Data Neo4j implementation of PedagogicalAnswer domain bean.
 * 
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
	
	private String description;

	@RelatedTo(elementClass = PedagogicalNeedNode.class, type = "needAnsweredBy", direction = INCOMING)
	private Set<PedagogicalNeed> pedagogicalNeeds = new HashSet<PedagogicalNeed>(0);

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "answeredUsingResourceCategory", direction = OUTGOING)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	public PedagogicalAnswerNode() {
	}

	public PedagogicalAnswerNode(String name) {
		this();
		setName(name);
	}

	public PedagogicalAnswerNode(String name, String description) {
		this(name);
		setDescription(description);
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
	public Set<PedagogicalNeed> getNeeds() {
		return pedagogicalNeeds;
	}

	@Override
	public void setNeeds(Set<PedagogicalNeed> needs) {
		this.pedagogicalNeeds = needs;
	}
	
	@Override
	public Set<ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}

	@Override
	public void setResourceCategories(Set<ResourceCategory> resourceCategories) {
		this.resourceCategories = resourceCategories;
	}

	@Override
	public int compareTo(PedagogicalAnswer o) {
		return this.compareTo(o);
	}

}
