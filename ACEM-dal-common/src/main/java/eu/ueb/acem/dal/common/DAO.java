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
package eu.ueb.acem.dal.common;

import java.util.Collection;

/**
 * This interface defines the operations that every DAO class must implement.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * @param <E>
 *            A bean interface, taken from eu.ueb.acem.domain.beans package
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface DAO<ID, E> {

	/**
	 * Tests if an object with the given identifier exists according to the
	 * queried DAO. This method's implementation must limit its internal test to
	 * entities of type E.
	 * 
	 * @param id
	 *            The id value to test
	 * @return true if the object was found through this DAO, false otherwise
	 */
	Boolean exists(ID id);

	/**
	 * Persists the entity of type E and returns the persisted version of this
	 * entity.
	 * 
	 * @param entity
	 *            An entity to persist through the DAO
	 * @return the persisted object (it may be different from the given entity)
	 */
	E create(E entity);

	/**
	 * Retrieves an entity of type E with the given id.
	 * 
	 * @param id
	 *            The id value of the object to retrieve through the DAO
	 * @return the retrieved object, or null
	 */
	E retrieveById(ID id);

	/**
	 * Retrieves an entity of type E with the given id.
	 * 
	 * @param id
	 *            The id value of the object to retrieve through the DAO
	 * @param initialize
	 *            Sets to true if the DAO must initialize the associated objects
	 *            (in the collections of the entity), false if it can leave them
	 *            uninitialized. If in doubt, pass true.
	 * @return the retrieved object, or null
	 */
	E retrieveById(ID id, boolean initialize);

	/**
	 * Retrieves the collection of entities of type E with the given name.
	 * 
	 * @param name
	 *            The "name" property value of the objects to retrieve through
	 *            the DAO
	 * @return the retrieved object
	 */
	Collection<E> retrieveByName(String name);

	/**
	 * Retrieves the collection of entities of type E with the given name.
	 * 
	 * @param name
	 *            The "name" property value of the objects to retrieve through
	 *            the DAO
	 * @param initialize
	 *            Sets to true if the DAO must initialize the associated objects
	 *            (in the collections of the entity), false if it can leave them
	 *            uninitialized. If in doubt, pass true.
	 * @return the retrieved object
	 */
	Collection<E> retrieveByName(String name, boolean initialize);

	/**
	 * Retrieves the collection of all entities known to this DAO.
	 * 
	 * @return the collection of all objects of type E
	 */
	Collection<E> retrieveAll();

	/**
	 * Saves the changes of the given entity.
	 * 
	 * @param entity
	 *            An object of type E to update
	 * @return the updated object
	 */
	E update(E entity);

	/**
	 * Deletes the given entity. It is advised to test that the entity don't
	 * exists afterwards with the {@link #exists} method.
	 * 
	 * @param entity
	 *            An object of type E to delete
	 */
	void delete(E entity);

	/**
	 * Deletes all the entities known to this DAO. Use with extreme caution.
	 */
	void deleteAll();

	/**
	 * Counts the number of entities accessible through this DAO.
	 * 
	 * @return the number of entities known to this DAO
	 */
	Long count();

}
