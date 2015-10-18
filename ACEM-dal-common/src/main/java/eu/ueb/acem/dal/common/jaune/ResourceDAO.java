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
package eu.ueb.acem.dal.common.jaune;

import java.util.Collection;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of beans that implement the Resource interface.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2014-03-11
 */
public interface ResourceDAO<ID, E extends Resource> extends DAO<ID, E> {

	/**
	 * Returns the collection of all {@link ResourceCategory} instances which
	 * are associated with {@link Resource}s of class E.
	 * 
	 * @return the collection of ResourceCategory associated with
	 *         {@link Resource}s of class E
	 */
	Collection<ResourceCategory> retrieveCategories();

	/**
	 * Returns the collection of {@link ResourceCategory} instances which are
	 * associated with at least one {@link Resource} visible by the given
	 * {@link Person}.
	 * 
	 * A Resource is said to be visible by a Person, if this Person works for an
	 * Organisation, and this Organisation owns the Resource, or supports the
	 * Resource, or views the Resource (owned by another Organisation that
	 * willingly accepts to share it).
	 * 
	 * Organisations also have automatic Resource sharing based on how they are
	 * associated together. If the Person works for a TeachingDepartment or a
	 * AdministrativeDepartment which is associated with an Institution, then
	 * the Resources of the Institution are automatically visible by the Person.
	 * The same holds true for Institution associated with Community instances,
	 * and transitively for AdministrativeDepartments/TeachingDepartments
	 * associated with Institutions associated with Communities.
	 * 
	 * @param person
	 *            A Person
	 * @return the collection of ResourceCategory containing at least one
	 *         Resource visible by the given Person
	 */
	Collection<ResourceCategory> retrieveCategoriesForPerson(Person person);

	/**
	 * Returns the collection of all instances of class E (subclass of
	 * {@link Resource}) which are associated with the given
	 * {@link ResourceCategory}.
	 * 
	 * @param category
	 *            A ResourceCategory
	 * @return the collection of ResourceCategory associated with
	 *         {@link Resource}s of class E
	 */
	Collection<E> retrieveAllWithCategory(ResourceCategory category);

	/**
	 * Returns the collection of all instances of class E (subclass of
	 * {@link Resource}) which are associated with the given
	 * {@link ResourceCategory} and which are visible by the given
	 * {@link Person}. Computing the visibility of a given Resource to the
	 * Person is described in the method {@link
	 * #retrieveCategoriesForPerson(Person)}.
	 * 
	 * @param category
	 *            A ResourceCategory
	 * @param person
	 *            A Person
	 * @return the collection of ResourceCategory associated with
	 *         {@link Resource}s of class E
	 */
	Collection<E> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person);

	/**
	 * Creates a new Resource of subclass E with the given name and icon.
	 * 
	 * @param name
	 *            A name for the ResourceCategory
	 * @param iconFileName
	 *            A icon file name for the Resource, including a relative path
	 *            starting after the base directory defined in the "images.path"
	 *            configuration property
	 * @return the newly created Resource
	 */
	E create(String name, String iconFileName);

}
