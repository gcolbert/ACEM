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
package eu.ueb.acem.dal.bleu;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalNeedRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalNeedNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalNeedDAO")
public class PedagogicalNeedDAO implements DAO<Long, PedagogicalNeed> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2890608278433660504L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalNeedDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private PedagogicalNeedRepository repository;

	public PedagogicalNeedDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		// This line should be sufficient but https://jira.spring.io/browse/DATAGRAPH-438
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return repository.count(id) > 0 ? true : false;
		}
	}

	@Override
	public PedagogicalNeed create(PedagogicalNeed entity) {
		return repository.save((PedagogicalNeedNode) entity);
	}

	@Override
	public PedagogicalNeed retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public PedagogicalNeed retrieveById(Long id, boolean initialize) {
		PedagogicalNeed entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<PedagogicalNeed> retrieveByName(String name) {
		Iterable<PedagogicalNeedNode> needNodes = repository.findByName(name);
		Collection<PedagogicalNeed> needs = new HashSet<PedagogicalNeed>();
		for (PedagogicalNeed need : needNodes) {
			needs.add(need);
		}
		return needs;
	}

	@Override
	public Collection<PedagogicalNeed> retrieveByName(String name, boolean initialize) {
		Collection<PedagogicalNeed> entities = retrieveByName(name);
		if (initialize) {
			for (PedagogicalNeed entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}
	
	@Override
	public void initializeCollections(PedagogicalNeed entity) {
		neo4jOperations.fetch(entity.getChildren());
		neo4jOperations.fetch(entity.getAnswers());
		neo4jOperations.fetch(entity.getParents());
	}

	@Override
	public Collection<PedagogicalNeed> retrieveAll() {
		Iterable<PedagogicalNeedNode> endResults = repository.findAll();
		Collection<PedagogicalNeed> collection = new HashSet<PedagogicalNeed>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalNeedNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalNeed entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public PedagogicalNeed update(PedagogicalNeed entity) {
		PedagogicalNeed updatedEntity = repository.save((PedagogicalNeedNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(PedagogicalNeed entity) {
		repository.delete((PedagogicalNeedNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Set<PedagogicalNeed> retrieveNeedsAtRoot() {
		Set<PedagogicalNeedNode> nodes = repository.findRoots();
		Set<PedagogicalNeed> needs = new HashSet<PedagogicalNeed>();
		for (PedagogicalNeedNode need : nodes) {
			initializeCollections(need);
			needs.add(need);
		}
		return needs;
	}

}
