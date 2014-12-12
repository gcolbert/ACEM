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
import eu.ueb.acem.dal.vert.neo4j.CampusRepository;
import eu.ueb.acem.domain.beans.vert.Campus;
import eu.ueb.acem.domain.beans.vert.neo4j.CampusNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("campusDAO")
public class CampusDAO implements DAO<Long, Campus> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 515628413371430770L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CampusDAO.class);

	@SuppressWarnings("unused")
	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private CampusRepository repository;

	public CampusDAO() {

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
	public Campus create(Campus entity) {
		return repository.save((CampusNode) entity);
	}

	@Override
	public Campus retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Campus retrieveById(Long id, boolean initialize) {
		Campus entity = retrieveById(id);
		if (initialize) {
		}
		return entity;
	}

	@Override
	public Collection<Campus> retrieveByName(String name) {
		Iterable<CampusNode> nodes = repository.findByName(name);
		Collection<Campus> entities = new HashSet<Campus>();
		for (CampusNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Campus> retrieveAll() {
		Iterable<CampusNode> endResults = repository.findAll();
		Collection<Campus> collection = new HashSet<Campus>();
		if (endResults.iterator() != null) {
			Iterator<CampusNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Campus update(Campus entity) {
		Campus updatedEntity = repository.save((CampusNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(Campus entity) {
		repository.delete((CampusNode) entity);
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
