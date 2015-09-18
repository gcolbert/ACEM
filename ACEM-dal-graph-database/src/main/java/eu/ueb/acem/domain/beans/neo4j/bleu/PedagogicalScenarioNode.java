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

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.neo4j.gris.TeacherNode;
import eu.ueb.acem.domain.beans.neo4j.violet.TeachingUnitNode;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalScenario")
public class PedagogicalScenarioNode extends PedagogicalUnitNode implements PedagogicalScenario {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1233433427413840564L;

	@Indexed
	private String name;

	private String evaluationModes;
	private Boolean published;

	@RelatedTo(elementClass = TeachingUnitNode.class, type = "scenarioUsedForTeachingUnit", direction = OUTGOING)
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	@RelatedTo(elementClass = PedagogicalSequenceNode.class, type = "sequenceForScenario", direction = INCOMING)
	private Set<PedagogicalSequence> pedagogicalSequences = new HashSet<PedagogicalSequence>(0);

	@RelatedTo(elementClass = TeacherNode.class, type = "authorsScenario", direction = INCOMING)
	private Set<Teacher> authors = new HashSet<Teacher>(0);

	public PedagogicalScenarioNode() {
		published = false;
	}

	public PedagogicalScenarioNode(String name) {
		this();
		setName(name);
	}

	public PedagogicalScenarioNode(String name, String objective) {
		this(name);
		setObjective(objective);
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
	public Set<PedagogicalSequence> getPedagogicalSequences() {
		return pedagogicalSequences;
	}

	@Override
	public void setPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences) {
		this.pedagogicalSequences = pedagogicalSequences;
	}

	@Override
	public PedagogicalScenario getPreviousPedagogicalScenario() {
		return (PedagogicalScenario)getPrevious();
	}

	@Override
	public void setPreviousPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		setPrevious(pedagogicalScenario);
	}

	@Override
	public PedagogicalScenario getNextPedagogicalScenario() {
		return (PedagogicalScenario)getNext();
	}

	@Override
	public void setNextPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		setNext(pedagogicalScenario);
	}

}
