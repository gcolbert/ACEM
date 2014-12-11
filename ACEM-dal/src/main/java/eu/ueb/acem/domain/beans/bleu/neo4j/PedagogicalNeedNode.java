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

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalNeed")
public class PedagogicalNeedNode extends AbstractNode implements PedagogicalNeed {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -774562771501521566L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalNeedNode.class);

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalNeedNode.class, type = "hasParentNeed", direction = OUTGOING)
	private Set<PedagogicalNeed> parents = new HashSet<PedagogicalNeed>(0);

	@RelatedTo(elementClass = PedagogicalNeedNode.class, type = "hasParentNeed", direction = INCOMING)
	private Set<PedagogicalNeed> children = new HashSet<PedagogicalNeed>(0);

	@RelatedTo(elementClass = PedagogicalAnswerNode.class, type = "needAnsweredBy", direction = OUTGOING)
	private Set<PedagogicalAnswer> answers = new HashSet<PedagogicalAnswer>(0);

	public PedagogicalNeedNode() {
	}

	public PedagogicalNeedNode(String name) {
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
	public Set<PedagogicalNeed> getParents() {
		return parents;
	}

	@Override
	public void setParents(Set<PedagogicalNeed> parents) {
		this.parents = parents;
	}

	@Override
	public Set<PedagogicalNeed> getChildren() {
		return children;
	}

	@Override
	public void setChildren(Set<PedagogicalNeed> children) {
		this.children = children;
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
	public int compareTo(PedagogicalNeed o) {
		return getName().compareTo(o.getName());
	}

}
