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
import eu.ueb.acem.dal.violet.neo4j.TeachingClassRepository;
import eu.ueb.acem.domain.beans.violet.TeachingClass;
import eu.ueb.acem.domain.beans.violet.neo4j.TeachingClassNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("teachingClassDAO")
public class TeachingClassDAO implements DAO<Long, TeachingClass> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3463144134744279313L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeachingClassDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private TeachingClassRepository repository;

	public TeachingClassDAO() {

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
	public TeachingClass create(TeachingClass entity) {
		return repository.save((TeachingClassNode) entity);
	}

	@Override
	public TeachingClass retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public TeachingClass retrieveById(Long id, boolean initialize) {
		TeachingClass entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getCourse());
			neo4jOperations.fetch(entity.getPedagogicalScenarios());
			neo4jOperations.fetch(entity.getLocation());
		}
		return entity;
	}

	@Override
	public Collection<TeachingClass> retrieveByName(String name) {
		Iterable<TeachingClassNode> nodes = repository.findByName(name);
		Collection<TeachingClass> entities = new HashSet<TeachingClass>();
		for (TeachingClassNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<TeachingClass> retrieveAll() {
		Iterable<TeachingClassNode> endResults = repository.findAll();
		Collection<TeachingClass> collection = new HashSet<TeachingClass>();
		if (endResults.iterator() != null) {
			Iterator<TeachingClassNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public TeachingClass update(TeachingClass entity) {
		TeachingClass updatedEntity = repository.save((TeachingClassNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(TeachingClass entity) {
		repository.delete((TeachingClassNode) entity);
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
