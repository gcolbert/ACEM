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
package eu.ueb.acem.domain.beans.rouge.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Etablissement;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation:Teaching_institution")
public class EtablissementNode extends OrganisationNode implements Etablissement {

	private static final long serialVersionUID = 4218521116992739925L;

	@Indexed(indexName = "indexOfInstitutions")
	private String name;

	@RelatedTo(elementClass = CommunauteNode.class, type = "institutionMemberOfCommunity", direction = OUTGOING)
	Communaute communaute;

	public EtablissementNode() {
	}

	public EtablissementNode(String name) {
		this();
		this.setName(name);
	}

	@Override
	public Communaute getCommunaute() {
		return communaute;
	}

}
