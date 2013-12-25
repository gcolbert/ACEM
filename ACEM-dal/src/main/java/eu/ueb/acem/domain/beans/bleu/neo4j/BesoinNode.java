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

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public class BesoinNode implements Besoin {
	
	private static final long serialVersionUID = -774562771501521566L;

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BesoinNode.class);

	@GraphId private Long id;
	
	@Indexed(indexName = "indexBesoin") private String nom;

	@RelatedTo(elementClass = BesoinNode.class, type = "aPourBesoinParent", direction = OUTGOING)
	private Set<Besoin> parents;
	
	@RelatedTo(elementClass = BesoinNode.class, type = "aPourBesoinEnfant", direction = OUTGOING)
	private Set<Besoin> enfants;

	@RelatedTo(elementClass = ReponseNode.class, type = "aPourReponse", direction = OUTGOING)
	private Set<Reponse> reponses;

    public BesoinNode() {
    	parents = new HashSet<Besoin>();
    	enfants = new HashSet<Besoin>();
    	reponses = new HashSet<Reponse>();
    }

    public BesoinNode(String nom) {
    	this();
    	setNom(nom);
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
	public Set<Besoin> getParents() {
		return parents;
	}
    
	@Override
	public void setParents(Set<Besoin> parents) {
		this.parents = parents;
	}
    
    @Override
    public Set<Besoin> getEnfants() {
    	return enfants;
    }


	@Override
	public void setEnfants(Set<Besoin> enfants) {
		this.enfants = enfants;		
	}

    @Override
    public Set<Reponse> getReponses() {
    	return reponses;
    }

	@Override
	public void setReponses(Set<Reponse> reponses) {
		this.reponses = reponses;
	}
    
	@Override
	public void addParent(Besoin parent) {
		parents.add(parent);
       	if (! parent.getEnfants().contains(this)) {
       		logger.debug("'{}'.addParent('{}') : parent doesn't already contains this node as a child, we create it", this.getNom(), parent.getNom());
       		parent.addEnfant(this);
       	}
       	else {
       		logger.debug("'{}'.addParent('{}') : parent already contains this node as a child, we don't create it", this.getNom(), parent.getNom());
       	}
	}
    
    @Override
    @Transactional
    public void addEnfant(Besoin besoin) {
       	enfants.add(besoin);
       	if (! besoin.getParents().contains(this)) {
       		logger.debug("'{}'.addEnfant('{}') : the child doesn't already have a reference to this, we create it", this.getNom(), besoin.getNom());
       		besoin.addParent(this);
       	}
       	else {
       		logger.debug("'{}'.addEnfant('{}') : the child already has a reference to this, we don't create it", this.getNom(), besoin.getNom());
       	}
    }

    @Override
    public void removeEnfant(Besoin besoin) {
    	enfants.remove(besoin);
    }

    @Override
    public void addReponse(Reponse reponse) {
   		reponses.add(reponse);
   		reponse.addBesoin(this);
    }

	@Override
	public void removeParent(Besoin besoin) {
		parents.remove(besoin);
	}

    @Override
    public void removeReponse(Reponse reponse) {
       	reponses.remove(reponse);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enfants == null) ? 0 : enfants.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		//result = prime * result + ((parents == null) ? 0 : parents.hashCode()); // avoids a stack overflow
		//result = prime * result + ((reponses == null) ? 0 : reponses.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BesoinNode other = (BesoinNode) obj;
		if (enfants == null) {
			if (other.enfants != null)
				return false;
		} else if (!enfants.equals(other.enfants))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (parents == null) {
			if (other.parents != null)
				return false;
		} else if (!parents.equals(other.parents))
			return false;
		if (reponses == null) {
			if (other.reponses != null)
				return false;
		} else if (!reponses.equals(other.reponses))
			return false;
		return true;
	}

}
