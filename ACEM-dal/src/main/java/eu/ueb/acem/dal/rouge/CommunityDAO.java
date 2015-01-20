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
package eu.ueb.acem.dal.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.rouge.neo4j.CommunityRepository;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunityNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("communityDAO")
public class CommunityDAO implements DAO<Long, Community> {

	/**
	 * FOr serialization.
	 */
	private static final long serialVersionUID = -6005681827386719691L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommunityDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private CommunityRepository repository;

	public CommunityDAO() {

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
	public Community create(Community entity) {
		return repository.save((CommunityNode) entity);
	}

	@Override
	public Community retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public void initializeCollections(Community entity) {
		neo4jOperations.fetch(entity.getPossessedResources());
		neo4jOperations.fetch(entity.getViewedResources());
		neo4jOperations.fetch(entity.getUseModes());
		neo4jOperations.fetch(entity.getInstitutions());
	}
	
	@Override
	public Community retrieveById(Long id, boolean initialize) {
		Community entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<Community> retrieveByName(String name) {
		Iterable<CommunityNode> nodes = repository.findByName(name);
		Collection<Community> entities = new HashSet<Community>();
		for (CommunityNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Community> retrieveByName(String name, boolean initialize) {
		Collection<Community> entities = retrieveByName(name);
		if (initialize) {
			for (Community entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<Community> retrieveAll() {
		Iterable<CommunityNode> endResults = repository.findAll();
		Collection<Community> entities = new HashSet<Community>();
		if (endResults.iterator() != null) {
			Iterator<CommunityNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Community entity = iterator.next();
				initializeCollections(entity);
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public Community update(Community entity) {
		Community updatedEntity = repository.save((CommunityNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(Community entity) {
		repository.delete((CommunityNode) entity);
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
