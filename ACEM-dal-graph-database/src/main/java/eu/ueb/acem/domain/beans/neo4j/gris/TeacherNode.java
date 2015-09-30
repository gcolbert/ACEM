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
package eu.ueb.acem.domain.beans.neo4j.gris;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.neo4j.violet.TeachingUnitNode;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The Spring Data Neo4j implementation of Teacher domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Teacher")
public class TeacherNode extends PersonNode implements Teacher {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3193454107919543890L;

	@RelatedTo(elementClass = TeachingUnitNode.class, type = "teacherInvolvedInPedagogicalUnit", direction = OUTGOING)
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "authorsScenario", direction = OUTGOING)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	public TeacherNode() {
	}

	public TeacherNode(String name, String login, String password) {
		super(name, login, password);
		setTeacher(true);
	}

	@Override
	public Set<PedagogicalScenario> getPedagogicalScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setPedagogicalScenarios(Set<PedagogicalScenario> pedagogicalScenarios) {
		this.pedagogicalScenarios = pedagogicalScenarios;
	}

	@Override
	public Set<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	@Override
	public void setTeachingUnits(Set<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

}
