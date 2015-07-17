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
package eu.ueb.acem.domain.beans.bleu.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.jpa.TeacherEntity;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;
import eu.ueb.acem.domain.beans.violet.jpa.TeachingUnitEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "PedagogicalScenario")
public class PedagogicalScenarioEntity extends PedagogicalUnitEntity implements PedagogicalScenario {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -512829316498046439L;

	@Lob
	private String evaluationModes;

	private Boolean published;

	@OneToMany(targetEntity = TeachingUnitEntity.class, mappedBy = "pedagogicalScenario")
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	@ManyToMany(targetEntity = TeacherEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "Teachers_PedagogicalScenarios")
	private Set<Teacher> authors = new HashSet<Teacher>(0);

	@OneToMany(targetEntity = PedagogicalSequenceEntity.class, mappedBy = "pedagogicalScenario")
	private Set<PedagogicalSequence> firstPedagogicalSequences;

	public PedagogicalScenarioEntity() {
		published = false;
	}

	public PedagogicalScenarioEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalScenarioEntity(String name, String objective) {
		this(name);
		setObjective(objective);
	}

	@Override
	public String getEvaluationModes() {
		return evaluationModes;
	}

	@Override
	public void setEvaluationModes(String evaluationModes) {
		this.evaluationModes = evaluationModes;
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
	public Set<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	@Override
	public void setTeachingUnits(Set<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

	@Override
	public Set<PedagogicalSequence> getFirstPedagogicalSequences() {
		return firstPedagogicalSequences;
	}

	@Override
	public void setFirstPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences) {
		this.firstPedagogicalSequences = pedagogicalSequences;
	}

//	@Override
//	public Set<PedagogicalSequence> getAllPedagogicalSequences() {
//		return allPedagogicalSequences;
//	}

	@Override
	public PedagogicalScenario getNextPedagogicalScenario() {
		return (PedagogicalScenario)getNext();
	}

	@Override
	public void setNextPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		setNext(pedagogicalScenario);
	}

}
