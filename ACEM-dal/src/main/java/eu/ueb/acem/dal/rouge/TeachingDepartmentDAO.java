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
import eu.ueb.acem.dal.rouge.neo4j.TeachingDepartmentRepository;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.TeachingDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("teachingDepartmentDAO")
public class TeachingDepartmentDAO implements DAO<Long, TeachingDepartment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1601784002431717278L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeachingDepartmentDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private TeachingDepartmentRepository repository;

	public TeachingDepartmentDAO() {

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
	public TeachingDepartment create(TeachingDepartment entity) {
		return repository.save((TeachingDepartmentNode) entity);
	}

	@Override
	public TeachingDepartment retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public void initializeCollections(TeachingDepartment entity) {
		neo4jOperations.fetch(entity.getPossessedResources());
		neo4jOperations.fetch(entity.getViewedResources());
		neo4jOperations.fetch(entity.getUseModes());
		neo4jOperations.fetch(entity.getInstitutions());
	}

	@Override
	public TeachingDepartment retrieveById(Long id, boolean initialize) {
		TeachingDepartment entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<TeachingDepartment> retrieveByName(String name) {
		Iterable<TeachingDepartmentNode> nodes = repository.findByName(name);
		Collection<TeachingDepartment> entities = new HashSet<TeachingDepartment>();
		for (TeachingDepartmentNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<TeachingDepartment> retrieveByName(String name, boolean initialize) {
		Collection<TeachingDepartment> entities = retrieveByName(name);
		if (initialize) {
			for (TeachingDepartment entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<TeachingDepartment> retrieveAll() {
		Iterable<TeachingDepartmentNode> endResults = repository.findAll();
		Collection<TeachingDepartment> collection = new HashSet<TeachingDepartment>();
		if (endResults.iterator() != null) {
			Iterator<TeachingDepartmentNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				TeachingDepartment entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public TeachingDepartment update(TeachingDepartment entity) {
		TeachingDepartment updatedEntity = repository.save((TeachingDepartmentNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(TeachingDepartment entity) {
		repository.delete((TeachingDepartmentNode) entity);
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
