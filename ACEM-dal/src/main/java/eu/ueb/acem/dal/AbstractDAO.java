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
package eu.ueb.acem.dal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.data.neo4j.template.Neo4jOperations;

public abstract class AbstractDAO<E, N extends E> implements DAO<Long, E> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6019747777770414715L;

	@Inject
	protected Neo4jOperations neo4jOperations;

	protected abstract GenericRepository<N> getRepository();

	@SuppressWarnings("unchecked")
	@Override
	public E create(E entity) {
		return getRepository().save((N) entity);
	}

	@Override
	public Boolean exists(Long id) {
		// This line should be sufficient but https://jira.spring.io/browse/DATAGRAPH-438
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return getRepository().count(id) > 0 ? true : false;
		}
	}
	
	@Override
	public E retrieveById(Long id) {
		return (id != null) ? getRepository().findOne(id) : null;
	}

	@Override
	public E retrieveById(Long id, boolean initialize) {
		E entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public abstract void initializeCollections(E entity);

	@Override
	public Collection<E> retrieveByName(String name) {
		Iterable<N> nodes = getRepository().findByName(name);
		Collection<E> entities = new HashSet<E>();
		for (E entity : nodes) {
			entities.add(entity);
		}
		return entities;
	}
	
	@Override
	public Collection<E> retrieveByName(String name, boolean initialize) {
		Collection<E> entities = retrieveByName(name);
		if (initialize) {
			for (E entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public Collection<E> retrieveAll() {
		Iterable<N> endResults = getRepository().findAll();
		Collection<E> collection = new HashSet<E>();
		if (endResults.iterator() != null) {
			Iterator<N> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				E entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public E update(E entity) {
		@SuppressWarnings("unchecked")
		E updatedEntity = getRepository().save((N) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(E entity) {
		getRepository().delete((N) entity);
	}

	@Override
	public void deleteAll() {
		getRepository().deleteAll();
	}

	@Override
	public Long count() {
		return getRepository().count();
	}
}
