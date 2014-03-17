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

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalNeed")
public class BesoinNode implements Besoin {

	private static final long serialVersionUID = -774562771501521566L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BesoinNode.class);

	@GraphId
	private Long id;

	@Indexed
	private String name;

	@RelatedTo(elementClass = BesoinNode.class, type = "hasParentNeed", direction = OUTGOING)
	@Fetch
	private Set<BesoinNode> parents;

	@RelatedTo(elementClass = BesoinNode.class, type = "hasParentNeed", direction = INCOMING)
	@Fetch
	private Set<BesoinNode> children;

	@RelatedTo(elementClass = ReponseNode.class, type = "needAnsweredBy", direction = OUTGOING)
	@Fetch
	private Set<ReponseNode> answers;

	public BesoinNode() {
	}

	public BesoinNode(String name) {
		this();
		this.name = name;
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
	public Set<? extends Besoin> getParents() {
		return parents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setParents(Set<? extends Besoin> parents) {
		this.parents = (Set<BesoinNode>) parents;
	}

	@Override
	@Transactional
	public void addParent(Besoin parent) {
		if (parent != null) {
			if (!parents.contains(parent)) {
				parents.add((BesoinNode) parent);
			}
			if (!parent.getChildren().contains(this)) {
				parent.addChild(this);
			}
		}
	}

	@Override
	@Transactional
	public void removeParent(Besoin need) {
		parents.remove(need);
		need.removeChild(this);
	}

	@Override
	public Set<? extends Besoin> getChildren() {
		return children;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setChildren(Set<? extends Besoin> children) {
		this.children = (Set<BesoinNode>) children;
	}

	@Override
	@Transactional
	public void addChild(Besoin need) {
		if (need != null) {
			if (!children.contains(need)) {
				children.add((BesoinNode) need);
			}
			if (!need.getParents().contains(this)) {
				need.addParent(this);
			}
		}
	}

	@Override
	@Transactional
	public void removeChild(Besoin need) {
		children.remove(need);
		need.removeParent(this);
	}

	@Override
	public Set<? extends Reponse> getAnswers() {
		return answers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAnswers(Set<? extends Reponse> answers) {
		this.answers = (Set<ReponseNode>) answers;
	}

	@Override
	@Transactional
	public void addAnswer(Reponse answer) {
		if (answer != null) {
			if (!answers.contains(answer)) {
				answers.add((ReponseNode) answer);
			}
			if (!answer.getNeeds().contains(this)) {
				answer.addNeed(this);
			}
		}
	}

	@Override
	@Transactional
	public void removeAnswer(Reponse answer) {
		if (answer != null) {
			answers.remove(answer);
			answer.removeNeed(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BesoinNode other = (BesoinNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else
			if (!id.equals(other.id))
				return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		return true;
	}

	@Override
	public int compareTo(Besoin o) {
		return getName().compareTo(o.getName());
	}

}
