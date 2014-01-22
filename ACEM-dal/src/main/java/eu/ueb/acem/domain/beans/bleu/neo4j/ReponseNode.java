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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 *
 */
@NodeEntity
@TypeAlias("Pedagogical_answer")
public class ReponseNode implements Reponse {
	
	private static final long serialVersionUID = 3066979121350858816L;

	@GraphId private Long id;
	
	@Indexed(indexName = "indexReponse") private String name;
	
	@RelatedTo(elementClass = BesoinNode.class, type = "aPourReponse", direction = INCOMING)
	private Set<Besoin> needs;
	
	@RelatedTo(elementClass = RessourceNode.class, type = "referenceRessource", direction = OUTGOING)
	private Set<Ressource> resources;

    public ReponseNode()  {
    	needs = new HashSet<Besoin>();
    	resources = new HashSet<Ressource>();
    }

    public ReponseNode(String name) {
    	this();
       	setName(name);
    }

    @Override
	public Long getId() {
    	return id;
    }

    public void setId(Long id) {
    	this.id = id;
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
	public Collection<Besoin> getNeeds() {
		return needs;
	}
    
    @Override
	public Collection<Ressource> getResources() {
		return resources;
	}

    @Override
    public void addNeed(Besoin need) {
    	needs.add(need);
    }

    @Override
    public void removeNeed(Besoin need) {
    	needs.remove(need);
    }
    
	@Override
	public void addResource(Ressource resource) {
		resources.add(resource);
	}

    @Override
    public void removeResource(Ressource resource) {
    	needs.remove(resource);
    }
	
}
