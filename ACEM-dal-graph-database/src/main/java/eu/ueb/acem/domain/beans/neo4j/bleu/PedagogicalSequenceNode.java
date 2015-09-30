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

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * The Spring Data Neo4j implementation of PedagogicalSequence domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-13
 */
@NodeEntity
@TypeAlias("PedagogicalSequence")
public class PedagogicalSequenceNode extends PedagogicalUnitNode implements PedagogicalSequence {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4106046861957017126L;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "sequenceForScenario", direction = OUTGOING)
	private PedagogicalScenario pedagogicalScenario;

	@RelatedTo(elementClass = PedagogicalSessionNode.class, type = "sessionForSequence", direction = INCOMING)
	private Set<PedagogicalSession> pedagogicalSessions = new HashSet<PedagogicalSession>(0);

	@RelatedTo(elementClass = PedagogicalActivityNode.class, type = "activityForSequence", direction = INCOMING)
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	@Indexed
	private String name;

	public PedagogicalSequenceNode() {
	}

	public PedagogicalSequenceNode(String name) {
		this();
		setName(name);
	}

	public PedagogicalSequenceNode(String name, String objective) {
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
	public PedagogicalScenario getPedagogicalScenario() {
		return pedagogicalScenario;
	}

	@Override
	public void setPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		this.pedagogicalScenario = pedagogicalScenario;
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
	public Set<PedagogicalSession> getPedagogicalSessions() {
		return pedagogicalSessions;
	}

	@Override
	public void setPedagogicalSessions(Set<PedagogicalSession> pedagogicalSessions) {
		this.pedagogicalSessions = pedagogicalSessions;
	}

	@Override
	public PedagogicalSequence getPreviousPedagogicalSequence() {
		return (PedagogicalSequence)getPrevious();
	}

	@Override
	public void setPreviousPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		setPrevious(pedagogicalSequence);
	}

	@Override
	public PedagogicalSequence getNextPedagogicalSequence() {
		return (PedagogicalSequence)getNext();
	}

	@Override
	public void setNextPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		setNext(pedagogicalSequence);
	}

}
