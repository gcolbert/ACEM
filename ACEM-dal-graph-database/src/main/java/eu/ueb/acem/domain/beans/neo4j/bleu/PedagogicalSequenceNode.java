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

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
public class PedagogicalSequenceNode extends PedagogicalUnitNode implements PedagogicalSequence {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4106046861957017126L;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "sequenceForScenario", direction = OUTGOING)
	private PedagogicalScenario pedagogicalScenario;

	@RelatedTo(elementClass = PedagogicalSequenceNode.class, type = "nextPedagogicalSequence", direction = OUTGOING)
	private PedagogicalSequence nextPedagogicalSequence;

	@RelatedTo(elementClass = PedagogicalSessionNode.class, type = "sessionForSequence", direction = INCOMING)
	private PedagogicalSession firstPedagogicalSession;

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
	public PedagogicalSession getFirstPedagogicalSession() {
		return firstPedagogicalSession;
	}

	@Override
	public void setFirstPedagogicalSession(PedagogicalSession pedagogicalSession) {
		this.firstPedagogicalSession = pedagogicalSession;
	}

	@Override
	public PedagogicalSequence getNextPedagogicalSequence() {
		return nextPedagogicalSequence;
	}

	@Override
	public void setNextPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		nextPedagogicalSequence = pedagogicalSequence;
	}

}
