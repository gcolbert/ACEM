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
package eu.ueb.acem.domain.beans.jaune.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-04-09
 * 
 */
@NodeEntity
@TypeAlias("ResourceCategory")
public class ResourceCategoryNode extends RessourceNode implements ResourceCategory {

	private static final long serialVersionUID = -9101136355869813825L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = ReponseNode.class, type = "answeredUsingResourceCategory", direction = INCOMING)
	@Fetch
	private Set<ReponseNode> answers;
	
	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "categoryContains", direction = OUTGOING)
	private Set<RessourceNode> resources;
	
	public ResourceCategoryNode() {
	}

	public ResourceCategoryNode(String name) {
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
	public Set<? extends Reponse> getAnswers() {
		return answers;
	}
	
	@Override
	public Set<? extends Ressource> getResources() {
		return resources;
	}

}
