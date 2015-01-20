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
package eu.ueb.acem.dal.vert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.vert.neo4j.BuildingRepository;
import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.neo4j.BuildingNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("buildingDAO")
public class BuildingDAO implements DAO<Long, Building> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8261719192237944983L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BuildingDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private BuildingRepository repository;

	public BuildingDAO() {

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
	public Building create(Building entity) {
		return repository.save((BuildingNode) entity);
	}

	@Override
	public Building retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public void initializeCollections(Building entity) {
		neo4jOperations.fetch(entity.getCampus());
	}

	@Override
	public Building retrieveById(Long id, boolean initialize) {
		Building entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<Building> retrieveByName(String name) {
		Iterable<BuildingNode> nodes = repository.findByName(name);
		Collection<Building> entities = new HashSet<Building>();
		for (BuildingNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Building> retrieveByName(String name, boolean initialize) {
		Collection<Building> entities = retrieveByName(name);
		if (initialize) {
			for (Building entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<Building> retrieveAll() {
		Iterable<BuildingNode> endResults = repository.findAll();
		Collection<Building> entities = new HashSet<Building>();
		if (endResults.iterator() != null) {
			Iterator<BuildingNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Building entity = iterator.next();
				initializeCollections(entity);
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public Building update(Building entity) {
		Building updatedEntity = repository.save((BuildingNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(Building entity) {
		repository.delete((BuildingNode) entity);
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
