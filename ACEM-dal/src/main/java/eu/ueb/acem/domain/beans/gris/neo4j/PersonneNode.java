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
package eu.ueb.acem.domain.beans.gris.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.neo4j.OrganisationNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public abstract class PersonneNode implements Personne {

	private static final long serialVersionUID = -5697929702791942609L;

	@GraphId private Long id;
	
	@Indexed(indexName = "rechercher-personne") private String nom;
	
	@RelatedTo(elementClass = OrganisationNode.class, type = "travaillePour", direction = OUTGOING)
	private OrganisationNode organisation;
	
	private String login;

	@Override
	public Long getId() {
		return id;
	}

    public void setId(Long id) {
    	this.id = id;
    }

	@Override
	public OrganisationNode getOrganisation() {
		return organisation;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;
	}
}
