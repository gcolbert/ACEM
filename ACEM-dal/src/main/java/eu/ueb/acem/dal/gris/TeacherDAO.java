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
package eu.ueb.acem.dal.gris;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.TeacherRepository;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.neo4j.TeacherNode;

/**
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("teacherDAO")
public class TeacherDAO implements DAO<Long, Teacher> {

	/**
	 * For serialization
	 */
	private static final long serialVersionUID = 920105894951436261L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeacherDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private TeacherRepository repository;

	public TeacherDAO() {

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
	public Teacher create(Teacher entity) {
		return repository.save((TeacherNode) entity);
	}

	@Override
	public Teacher retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public void initializeCollections(Teacher entity) {
		neo4jOperations.fetch(entity.getFavoriteToolCategories());
		neo4jOperations.fetch(entity.getScenarios());
		neo4jOperations.fetch(entity.getClasses());
		neo4jOperations.fetch(entity.getWorksForOrganisations());
	}

	@Override
	public Teacher retrieveById(Long id, boolean initialize) {
		Teacher entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	public Teacher retrieveByLogin(String id, boolean initialize) {
		Teacher entity = repository.findByLogin(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Collection<Teacher> retrieveByName(String name) {
		Iterable<TeacherNode> nodes = repository.findByName(name);
		Collection<Teacher> entities = new HashSet<Teacher>();
		for (TeacherNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Teacher> retrieveByName(String name, boolean initialize) {
		Collection<Teacher> entities = retrieveByName(name);
		if (initialize) {
			for (Teacher entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<Teacher> retrieveAll() {
		Iterable<TeacherNode> endResults = repository.findAll();
		Collection<Teacher> collection = new HashSet<Teacher>();
		if (endResults.iterator() != null) {
			Iterator<TeacherNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Teacher entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Teacher update(Teacher entity) {
		Teacher updatedEntity = repository.save((TeacherNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Teacher entity) {
		repository.delete((TeacherNode) entity);
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
