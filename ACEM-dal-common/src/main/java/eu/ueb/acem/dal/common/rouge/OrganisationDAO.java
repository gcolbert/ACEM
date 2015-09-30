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
package eu.ueb.acem.dal.common.rouge;

import java.util.Collection;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of beans that implement the Organisation interface.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2014-03-11
 */
public interface OrganisationDAO<ID, E extends Organisation> extends DAO<ID, E> {

	/**
	 * Returns the collection of instances of class E (subclass of
	 * {@link Organisation}) which are support services for the
	 * {@link eu.ueb.acem.domain.beans.jaune.Resource}s visible by the given
	 * Person.
	 * 
	 * @param person
	 *            A Person
	 * @return the Collection of Organisations of class E that support the
	 *         resources visible to the given Person
	 */
	Collection<E> retrieveSupportServicesForPerson(Person person);

	/**
	 * Creates a new Resource of subclass E with the given name and icon.
	 * 
	 * @param name
	 *            A name for the Organisation (e.g. "University of Somewhere")
	 * @param shortname
	 *            A shortname for the Organisation (e.g. "UoS")
	 * @param iconFileName
	 *            A icon file name for the Organisation, including a relative
	 *            path starting after the base directory defined in the
	 *            "images.path" configuration property
	 * @return the newly created Organisation of class E
	 */
	E create(String name, String shortname, String iconFileName);

}
