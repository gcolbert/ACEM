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
import eu.ueb.acem.dal.vert.neo4j.FloorRepository;
import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.neo4j.FloorNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("floorDAO")
public class FloorDAO implements DAO<Long, Floor> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 515628413371430770L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FloorDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private FloorRepository repository;

	public FloorDAO() {

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
	public Floor create(Floor entity) {
		return repository.save((FloorNode) entity);
	}

	@Override
	public Floor retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Floor retrieveById(Long id, boolean initialize) {
		Floor entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getBuilding());
		}
		return entity;
	}

	@Override
	public Collection<Floor> retrieveByName(String name) {
		Iterable<FloorNode> nodes = repository.findByName(name);
		Collection<Floor> entities = new HashSet<Floor>();
		for (FloorNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Floor> retrieveAll() {
		Iterable<FloorNode> endResults = repository.findAll();
		Collection<Floor> collection = new HashSet<Floor>();
		if (endResults.iterator() != null) {
			Iterator<FloorNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Floor update(Floor entity) {
		Floor updatedEntity = repository.save((FloorNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Floor entity) {
		repository.delete((FloorNode) entity);
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
