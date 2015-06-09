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

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.neo4j.AbstractNode;

/**
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@NodeEntity
@TypeAlias("PedagogicalKeyword")
public class PedagogicalKeywordNode extends AbstractNode implements PedagogicalKeyword {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8984120669570156247L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "hasKeyword", direction = INCOMING)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);
	
	public PedagogicalKeywordNode() {
	}

	public PedagogicalKeywordNode(String name) {
		this();
		this.name = name;
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
	public Set<PedagogicalScenario> getPedagogicalScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setPedagogicalScenarios(Set<PedagogicalScenario> pedagogicalScenarios) {
		this.pedagogicalScenarios = pedagogicalScenarios;
	}

	@Override
	public int compareTo(PedagogicalKeyword o) {
		return this.compareTo(o);
	}

}
