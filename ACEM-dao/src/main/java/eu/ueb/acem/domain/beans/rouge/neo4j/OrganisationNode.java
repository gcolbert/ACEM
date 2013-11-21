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

import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public abstract class OrganisationNode implements Organisation {

	private static final long serialVersionUID = -4961037643458063514L;

	@GraphId private Long id;

	@Indexed(indexName = "rechercher-organisation") private String nom;

	@RelatedTo(elementClass = RessourceNode.class, type = "possede", direction = OUTGOING)
	private Set<Ressource> ressourcesPossedees;

	@RelatedTo(elementClass = RessourceNode.class, type = "voit", direction = OUTGOING)
	private Set<Ressource> ressourcesVues;

    public OrganisationNode()  {
    }

    public OrganisationNode(String nom) {
    	this.setNom(nom);
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Ressource> getRessourcesPossedees() {
		return ressourcesPossedees;
	}

	public Set<Ressource> getRessourcesVues() {
		return ressourcesVues;
	}

}
