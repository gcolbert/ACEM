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
package eu.ueb.acem.domain.beans.bleu.neo4j;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

import java.util.Collection;
import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.BOTH;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public class BesoinNode implements Besoin {

	private static final long serialVersionUID = -774562771501521566L;

	@GraphId private Long id;
	
	@Indexed(indexName = "rechercher-besoin-pedagogique") private String nom;
	
	@RelatedTo(elementClass = BesoinNode.class, type = "reference", direction = BOTH)
	private Set<Besoin> besoins;

	@RelatedTo(elementClass = ReponseNode.class, type = "reference", direction = OUTGOING)
	private Set<Reponse> reponses;

    public BesoinNode() {
    }

    public BesoinNode(String nom) {
    	this.nom = nom;
    }

    public BesoinNode(Besoin besoin) {
		this.setNom(besoin.getNom());
		this.setBesoins(besoin.getBesoins());
		this.setReponses(besoin.getReponses());
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

    public Collection<Besoin> getBesoins() {
    	return besoins;
    }

    public void setBesoins(Collection<Besoin> besoins) {
    	this.besoins = (Set<Besoin>) besoins;
    }
    
    public Collection<Reponse> getReponses() {
    	return reponses;
    }

    public void setReponses(Collection<Reponse> reponses) {
    	this.reponses = (Set<Reponse>) reponses;
    }
    
}
