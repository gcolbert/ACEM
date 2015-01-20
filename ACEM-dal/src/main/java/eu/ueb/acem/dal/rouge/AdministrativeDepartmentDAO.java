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
import eu.ueb.acem.dal.rouge.neo4j.AdministrativeDepartmentRepository;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("administrativeDepartmentDAO")
public class AdministrativeDepartmentDAO implements DAO<Long, AdministrativeDepartment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 515628413371430770L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AdministrativeDepartmentDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private AdministrativeDepartmentRepository repository;

	public AdministrativeDepartmentDAO() {

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
	public AdministrativeDepartment create(AdministrativeDepartment entity) {
		return repository.save((AdministrativeDepartmentNode) entity);
	}

	@Override
	public AdministrativeDepartment retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public void initializeCollections(AdministrativeDepartment entity) {
		neo4jOperations.fetch(entity.getPossessedResources());
		neo4jOperations.fetch(entity.getViewedResources());
		neo4jOperations.fetch(entity.getUseModes());
		neo4jOperations.fetch(entity.getInstitutions());
	}

	@Override
	public AdministrativeDepartment retrieveById(Long id, boolean initialize) {
		AdministrativeDepartment entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<AdministrativeDepartment> retrieveByName(String name) {
		Iterable<AdministrativeDepartmentNode> nodes = repository.findByName(name);
		Collection<AdministrativeDepartment> entities = new HashSet<AdministrativeDepartment>();
		for (AdministrativeDepartmentNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<AdministrativeDepartment> retrieveByName(String name, boolean initialize) {
		Collection<AdministrativeDepartment> entities = retrieveByName(name);
		if (initialize) {
			for (AdministrativeDepartment entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<AdministrativeDepartment> retrieveAll() {
		Iterable<AdministrativeDepartmentNode> endResults = repository.findAll();
		Collection<AdministrativeDepartment> entities = new HashSet<AdministrativeDepartment>();
		if (endResults.iterator() != null) {
			Iterator<AdministrativeDepartmentNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				AdministrativeDepartment entity = iterator.next();
				initializeCollections(entity);
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public AdministrativeDepartment update(AdministrativeDepartment entity) {
		AdministrativeDepartment updatedEntity = repository.save((AdministrativeDepartmentNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(AdministrativeDepartment entity) {
		repository.delete((AdministrativeDepartmentNode) entity);
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
