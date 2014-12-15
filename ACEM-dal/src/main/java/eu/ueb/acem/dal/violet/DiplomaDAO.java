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
import eu.ueb.acem.dal.violet.neo4j.DiplomaRepository;
import eu.ueb.acem.domain.beans.violet.Diploma;
import eu.ueb.acem.domain.beans.violet.neo4j.DiplomaNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("diplomaDAO")
public class DiplomaDAO implements DAO<Long, Diploma> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 20097765170954629L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DiplomaDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private DiplomaRepository repository;

	public DiplomaDAO() {

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
	public Diploma create(Diploma entity) {
		return repository.save((DiplomaNode) entity);
	}

	@Override
	public Diploma retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Diploma retrieveById(Long id, boolean initialize) {
		Diploma entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getCredits());
		}
		return entity;
	}

	@Override
	public Collection<Diploma> retrieveByName(String name) {
		Iterable<DiplomaNode> nodes = repository.findByName(name);
		Collection<Diploma> entities = new HashSet<Diploma>();
		for (DiplomaNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Diploma> retrieveAll() {
		Iterable<DiplomaNode> endResults = repository.findAll();
		Collection<Diploma> collection = new HashSet<Diploma>();
		if (endResults.iterator() != null) {
			Iterator<DiplomaNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Diploma update(Diploma entity) {
		Diploma updatedEntity = repository.save((DiplomaNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Diploma entity) {
		repository.delete((DiplomaNode) entity);
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
