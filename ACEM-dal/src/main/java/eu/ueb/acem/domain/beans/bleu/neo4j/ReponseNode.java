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

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public class ReponseNode implements Reponse {
	
	private static final long serialVersionUID = 3066979121350858816L;

	@GraphId private Long id;
	
	@Indexed(indexName = "rechercher-reponse-pedagogique") private String nom;
	
	@RelatedTo(elementClass = BesoinNode.class, type = "aPourReponse", direction = INCOMING)
	private Set<Besoin> besoins = new HashSet<Besoin>();
	
	@RelatedTo(elementClass = RessourceNode.class, type = "referenceRessource", direction = OUTGOING)
	private Set<Ressource> ressources = new HashSet<Ressource>();

    public ReponseNode()  {
    }

    public ReponseNode(String nom) {
       	this.setNom(nom);
    }

    @Override
	public Long getId() {
    	return id;
    }

    public void setId(Long id) {
    	this.id = id;
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
	public Collection<Besoin> getBesoins() {
		return besoins;
	}
    
    @Override
	public Collection<Ressource> getRessources() {
		return ressources;
	}

}
