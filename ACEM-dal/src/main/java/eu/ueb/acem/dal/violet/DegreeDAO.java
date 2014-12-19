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
import eu.ueb.acem.dal.violet.neo4j.DegreeRepository;
import eu.ueb.acem.domain.beans.violet.Degree;
import eu.ueb.acem.domain.beans.violet.neo4j.DegreeNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("diplomaDAO")
public class DegreeDAO implements DAO<Long, Degree> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 20097765170954629L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DegreeDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private DegreeRepository repository;

	public DegreeDAO() {

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
	public Degree create(Degree entity) {
		return repository.save((DegreeNode) entity);
	}

	@Override
	public Degree retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Degree retrieveById(Long id, boolean initialize) {
		Degree entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getCredits());
		}
		return entity;
	}

	@Override
	public Collection<Degree> retrieveByName(String name) {
		Iterable<DegreeNode> nodes = repository.findByName(name);
		Collection<Degree> entities = new HashSet<Degree>();
		for (DegreeNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Degree> retrieveAll() {
		Iterable<DegreeNode> endResults = repository.findAll();
		Collection<Degree> collection = new HashSet<Degree>();
		if (endResults.iterator() != null) {
			Iterator<DegreeNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Degree update(Degree entity) {
		Degree updatedEntity = repository.save((DegreeNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Degree entity) {
		repository.delete((DegreeNode) entity);
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
