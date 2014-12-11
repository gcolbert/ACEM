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
package eu.ueb.acem.dal.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.UseModeRepository;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jaune.neo4j.UseModeNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
@Repository("useModeDAO")
public class UseModeDAO implements DAO<Long, UseMode> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1874254078249425495L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UseModeDAO.class);

	@Inject
	@SuppressWarnings("unused")
	private Neo4jOperations neo4jOperations;

	@Inject
	private UseModeRepository repository;

	public UseModeDAO() {

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
	public UseMode create(UseMode entity) {
		return repository.save((UseModeNode) entity);
	}

	@Override
	public UseMode retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public UseMode retrieveById(Long id, boolean initialize) {
		UseMode entity = retrieveById(id);
		if (initialize) {
		}
		return entity;
	}

	@Override
	public Collection<UseMode> retrieveByName(String name) {
		Iterable<UseModeNode> useModeNodes = repository.findByName(name);
		Collection<UseMode> useModes = new HashSet<UseMode>();
		for (UseMode useMode : useModeNodes) {
			useModes.add(useMode);
		}
		return useModes;
	}

	@Override
	public Collection<UseMode> retrieveAll() {
		Iterable<UseModeNode> endResults = repository.findAll();
		Collection<UseMode> collection = new HashSet<UseMode>();
		if (endResults.iterator() != null) {
			Iterator<UseModeNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public UseMode update(UseMode entity) {
		UseMode updatedEntity = repository.save((UseModeNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(UseMode entity) {
		repository.delete((UseModeNode) entity);
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
