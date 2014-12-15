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
package eu.ueb.acem.dal.violet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.violet.neo4j.CourseRepository;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.neo4j.CourseNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("courseDAO")
public class CourseDAO implements DAO<Long, Course> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 5716802262936318251L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CourseDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private CourseRepository repository;

	public CourseDAO() {

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
	public Course create(Course entity) {
		return repository.save((CourseNode) entity);
	}

	@Override
	public Course retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Course retrieveById(Long id, boolean initialize) {
		Course entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getCredit());
		}
		return entity;
	}

	@Override
	public Collection<Course> retrieveByName(String name) {
		Iterable<CourseNode> nodes = repository.findByName(name);
		Collection<Course> entities = new HashSet<Course>();
		for (CourseNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Course> retrieveAll() {
		Iterable<CourseNode> endResults = repository.findAll();
		Collection<Course> collection = new HashSet<Course>();
		if (endResults.iterator() != null) {
			Iterator<CourseNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Course update(Course entity) {
		Course updatedEntity = repository.save((CourseNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Course entity) {
		repository.delete((CourseNode) entity);
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
