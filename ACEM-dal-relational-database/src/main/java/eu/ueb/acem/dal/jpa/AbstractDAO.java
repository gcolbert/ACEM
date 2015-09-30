/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eu.ueb.acem.dal.common.DAO;

/**
 * This abstract class uses Java generics to define the common methods of all
 * JPA DAO instances.
 * 
 * @author Grégoire Colbert
 *
 * @param <E>
 *            A domain bean interface
 * @param <N>
 *            A JPA implementation of interface E
 */
public abstract class AbstractDAO<E, N extends E> implements DAO<Long, E>, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3601463916846760241L;

	@PersistenceContext
	protected transient EntityManager entityManager;

	/**
	 * This method returns the repository that deals with entities of class N.
	 * 
	 * @return the repository for entities of class N
	 */
	protected abstract GenericRepository<N> getRepository();

	/**
	 * This method initializes the {@link java.util.Collection}s of the given
	 * entity.
	 * 
	 * @param entity
	 *            The entity with collections to initialize
	 */
	protected abstract void initializeCollections(E entity);

	/**
	 * This method persists the given entity in the datastore, initializes the
	 * collections and returns the stored version of the entity.
	 * 
	 * @param entity
	 *            The entity to persist
	 * @return the entity received, with potentially new property values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E create(E entity) {
		E e = getRepository().save((N) entity);
		initializeCollections(e);
		return e;
	}

	/**
	 * This method tests that a node of type N exists with the given "id"
	 * property.
	 * 
	 * @param id
	 *            The "id" to check for existence
	 * @return true if there's an entity with the given id in the current DAO,
	 *         false otherwise
	 */
	@Override
	public Boolean exists(Long id) {
		return (id != null) ? getRepository().exists(id) : false;
	}

	@Override
	public E retrieveById(Long id) {
		if (exists(id)) {
			return getRepository().findOne(id);
		}
		else {
			return null;
		}
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
