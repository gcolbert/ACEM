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
package eu.ueb.acem.domain.beans.jaune.neo4j;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.ActivitePedagogiqueNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;
import eu.ueb.acem.domain.beans.jaune.ModaliteUtilisation;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Resource")
public abstract class RessourceNode implements Ressource {

	private static final long serialVersionUID = -7922906613944705977L;

	@GraphId
	private Long id;

	private String name;

	@RelatedTo(elementClass = ModaliteUtilisationNode.class, type = "resourceHasUseMode", direction = OUTGOING)
	private Set<ModaliteUtilisation> useModes;
	
	@RelatedTo(elementClass = ReponseNode.class, type="answeredUsingResource", direction = INCOMING)
	private Set<Reponse> answers;

	@RelatedTo(elementClass = ActivitePedagogiqueNode.class, type="activityRequiringResource", direction = INCOMING)
	private Set<ActivitePedagogique> pedagogicalActivities;
	
	public RessourceNode() {
	}

	public RessourceNode(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ModaliteUtilisation> getModalitesUtilisation() {
		return useModes;
	}

	public Set<Reponse> getAnswers() {
		return answers;
	}

}
