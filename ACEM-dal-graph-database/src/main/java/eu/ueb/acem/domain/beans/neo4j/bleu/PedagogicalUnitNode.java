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

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.bleu.PedagogicalUnit;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;

/**
 * The Spring Data Neo4j implementation of PedagogicalUnit abstract domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalUnit")
public abstract class PedagogicalUnitNode extends AbstractNode implements PedagogicalUnit {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7906188691519080373L;

	private String description;
	
	private String objective;

	private String prerequisites;

	private String targetedSkills;

	private String pedagogicalMaterial;

	private Long start;

	private Long duration;

	private Long creationDate;

	private Long modificationDate;

	@RelatedTo(elementClass = TeachingModeNode.class, type = "pedagogicalUnitHasTeachingMode", direction = OUTGOING)
	private TeachingMode teachingMode;

	@RelatedTo(elementClass = PedagogicalKeywordNode.class, type = "hasKeyword", direction = OUTGOING)
	private Set<PedagogicalKeyword> pedagogicalKeywords = new HashSet<PedagogicalKeyword>(0);

	public PedagogicalUnitNode() {
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
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
	}

	@Override
	public String getPrerequisites() {
		return prerequisites;
	}

	@Override
	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	@Override
	public String getTargetedSkills() {
		return targetedSkills;
	}

	@Override
	public void setTargetedSkills(String targetedSkills) {
		this.targetedSkills = targetedSkills;
	}

	@Override
	public String getPedagogicalMaterial() {
		return pedagogicalMaterial;
	}

	@Override
	public void setPedagogicalMaterial(String pedagogicalMaterial) {
		this.pedagogicalMaterial = pedagogicalMaterial;
	}

	@Override
	public Long getStart() {
		return start;
	}

	@Override
	public void setStart(Long start) {
		this.start = start;
	}

	@Override
	public Long getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Long duration) {
		this.duration = duration;
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
	public TeachingMode getTeachingMode() {
		return teachingMode;
	}

	@Override
	public void setTeachingMode(TeachingMode teachingMode) {
		this.teachingMode = teachingMode;
	}

	@Override
	public Set<PedagogicalKeyword> getPedagogicalKeywords() {
		return pedagogicalKeywords;
	}

	@Override
	public void setPedagogicalKeywords(Set<PedagogicalKeyword> pedagogicalKeywords) {
		this.pedagogicalKeywords = pedagogicalKeywords;
	}

	@Override
	public int compareTo(PedagogicalUnit o) {
		return this.getName().compareTo(o.getName());
	}

}
