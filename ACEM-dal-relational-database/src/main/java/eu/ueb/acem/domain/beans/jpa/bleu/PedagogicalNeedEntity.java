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
package eu.ueb.acem.domain.beans.jpa.bleu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;

/**
 * The Spring Data JPA implementation of PedagogicalNeed domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-19
 * 
 */
@Entity(name = "PedagogicalNeed")
@Table(name = "PedagogicalNeed")
public class PedagogicalNeedEntity extends AbstractEntity implements PedagogicalNeed {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 831188826416445449L;

	private String name;

	@Lob
	private String description;

	@ManyToMany(targetEntity = PedagogicalNeedEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name="PedagogicalNeed_relationships")
	private Set<PedagogicalNeed> parents = new HashSet<PedagogicalNeed>(0);

	@ManyToMany(targetEntity = PedagogicalNeedEntity.class, fetch = FetchType.LAZY, mappedBy="parents")
	private Set<PedagogicalNeed> children = new HashSet<PedagogicalNeed>(0);

	@ManyToMany(targetEntity = PedagogicalAnswerEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalAnswer> answers = new HashSet<PedagogicalAnswer>(0);

	public PedagogicalNeedEntity() {
	}

	public PedagogicalNeedEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalNeedEntity(String name, String description) {
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
