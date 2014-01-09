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
	
	@Indexed(indexName = "indexBesoin") private String name;

	@RelatedTo(elementClass = BesoinNode.class, type = "aPourBesoinParent", direction = OUTGOING)
	private Set<Besoin> parents;
	
	@RelatedTo(elementClass = BesoinNode.class, type = "aPourBesoinParent", direction = INCOMING)
	private Set<Besoin> children;

	@RelatedTo(elementClass = ReponseNode.class, type = "aPourReponse", direction = OUTGOING)
	private Set<Reponse> reponses;

    public BesoinNode() {
    	parents = new HashSet<Besoin>();
    	children = new HashSet<Besoin>();
    	reponses = new HashSet<Reponse>();
    }

    public BesoinNode(String nom) {
    	this();
    	setName(nom);
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
    public void setName(String nom) {
    	this.name = nom;
    }

	@Override
	public Set<Besoin> getParents() {
		return parents;
	}

	@Override
	@Transactional
	public void setParents(Set<Besoin> parents) {
		this.parents = parents;
	}

	@Override
	@Transactional
	public void addParent(Besoin parent) {
		parents.add(parent);
	   	if (! parent.getChildren().contains(this)) {
	   		logger.info("'{}'.addParent('{}') : this node doesn't already have a reference to this parent, we add it", this.getName(), parent.getName());
	   		parent.addChild(this);
	   	}
	   	else {
	   		logger.info("'{}'.addParent('{}') : this node already has a reference to this parent, we don't add it", this.getName(), parent.getName());
	   	}
	}

	@Override
	@Transactional
	public void removeParent(Besoin besoin) {
		parents.remove(besoin);
		besoin.removeChild(this);
	}

	@Override
    public Set<Besoin> getChildren() {
    	return children;
    }

	@Override
	@Transactional
	public void setChildren(Set<Besoin> children) {
		this.children = children;
	}

    @Override
	@Transactional
	public void addChild(Besoin besoin) {
	   	children.add(besoin);
	   	if (! besoin.getParents().contains(this)) {
	   		logger.info("'{}'.addChild('{}') : the child doesn't have a reference to this parent, we add it", this.getName(), besoin.getName());
	   		besoin.addParent(this);
	   	}
	   	else {
	   		logger.info("'{}'.addChild('{}') : the child already has a reference to this parent, we don't add it", this.getName(), besoin.getName());
	   	}
	}

	@Override
	@Transactional
	public void removeChild(Besoin besoin) {
		children.remove(besoin);
		besoin.removeParent(this);
	}

	@Override
    public Set<Reponse> getReponses() {
    	return reponses;
    }

	@Override
	@Transactional
	public void setReponses(Set<Reponse> reponses) {
		this.reponses = reponses;
	}
    
	@Override
	@Transactional
	public void addReponse(Reponse reponse) {
		reponses.add(reponse);
		reponse.addBesoin(this);
	}

	@Override
	@Transactional
	public void removeReponse(Reponse reponse) {
	   	reponses.remove(reponse);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
