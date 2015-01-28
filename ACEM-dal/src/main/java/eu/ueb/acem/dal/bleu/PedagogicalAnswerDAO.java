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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalAnswerRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalAnswerNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-26
 * 
 */
@Repository("pedagogicalAnswerDAO")
public class PedagogicalAnswerDAO implements DAO<Long, PedagogicalAnswer> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4117462676183855035L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalAnswerDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;
	
	@Inject
	private PedagogicalAnswerRepository repository;

	public PedagogicalAnswerDAO() {

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
	public PedagogicalAnswer create(PedagogicalAnswer entity) {
		return repository.save((PedagogicalAnswerNode) entity);
	}

	@Override
	public PedagogicalAnswer retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public PedagogicalAnswer retrieveById(Long id, boolean initialize) {
		PedagogicalAnswer entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<PedagogicalAnswer> retrieveByName(String name) {
		Iterable<PedagogicalAnswerNode> nodes = repository.findByName(name);
		Collection<PedagogicalAnswer> entities = new HashSet<PedagogicalAnswer>();
		for (PedagogicalAnswerNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<PedagogicalAnswer> retrieveByName(String name, boolean initialize) {
		Collection<PedagogicalAnswer> entities = retrieveByName(name);
		if (initialize) {
			for (PedagogicalAnswer entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public void initializeCollections(PedagogicalAnswer entity) {
		neo4jOperations.fetch(entity.getResourceCategories());
		neo4jOperations.fetch(entity.getNeeds());
	}

	@Override
	public Collection<PedagogicalAnswer> retrieveAll() {
		Iterable<PedagogicalAnswerNode> endResults = repository.findAll();
		Collection<PedagogicalAnswer> collection = new HashSet<PedagogicalAnswer>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalAnswerNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalAnswer entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public PedagogicalAnswer update(PedagogicalAnswer entity) {
		PedagogicalAnswer updatedEntity = repository.save((PedagogicalAnswerNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(PedagogicalAnswer entity) {
		repository.delete((PedagogicalAnswerNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
