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
package eu.ueb.acem.dal.common.gris;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.domain.beans.gris.Person;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of Person beans.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * @param <E>
 *            A bean interface that extends Person, taken from
 *            eu.ueb.acem.domain.beans package
 * @author Grégoire Colbert
 * @since 2015-05-21
 * 
 */
public interface PersonDAO<ID, E extends Person> extends DAO<ID, E> {

	/**
	 * Creates a new implementation of Person, and initializes it with the given
	 * name, login and password. The password should be already encoded with Spring's BCryptPasswordEncoder#encode(String) method.
	 * 
	 * @param name
	 *            A name for the new Person
	 * @param login
	 *            A login for the new Person
	 * @param encodedPassword
	 *            An encoded password for the new Person
	 * @return The new instance of class E
	 * @see <a href="http://docs.spring.io/spring-security/site/docs/3.1.x/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html#encode%28java.lang.CharSequence%29">Spring's BCryptPasswordEncoder</a>
	 */
	E create(String name, String login, String encodedPassword);

	/**
	 * This method returns the instance of Person having the given "id" property
	 * value, or null if such an instance doesn't exist.
	 * 
	 * @param id
	 *            The "id" property value to look for
	 * @param initialize
	 *            Sets to true if the DAO must initialize the associated objects
	 *            (in the collections of the entity), false if it can leave them
	 *            uninitialized. If in doubt, pass true.
	 * @return The Person with the given "id" property, or null if it doesn't
	 *         exist
	 */
	E retrieveByLogin(String id, boolean initialize);

}
