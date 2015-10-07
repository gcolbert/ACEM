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
package eu.ueb.acem.services;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * A service to manage {@link Person}s and {@link Teacher}s.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface UsersService extends Serializable {

	/**
	 * Counts the number of persistent Teacher objects
	 * 
	 * @return number of teachers
	 */
	Long countTeachers();

	/**
	 * Creates a new persistent Teacher.
	 * 
	 * @param name
	 *            A name for the Teacher
	 * @param login
	 *            A login for the Teacher
	 * @param password
	 *            A password for the Teacher
	 * @return The newly created Teacher, or null.
	 */
	Teacher createTeacher(String name, String login, String password);

	/**
	 * Returns the Teacher whose id is given.
	 * 
	 * @param id
	 *            The id of the Teacher to retrieve
	 * @return The Teacher with the given id, or null if it doesn't exist
	 */
	Teacher retrieveTeacher(Long id);

	/**
	 * Updates the given Person and returns the updated entity.
	 * 
	 * @param person
	 *            The Person to update
	 * @return the updated entity
	 */
	Person updatePerson(Person person);

	/**
	 * Updates the given Teacher and returns the updated entity.
	 * 
	 * @param teacher
	 *            The Teacher to update
	 * @return the updated entity
	 */
	Teacher updateTeacher(Teacher teacher);

	/**
	 * Deletes the Teacher whose id is given.
	 * 
	 * @param id
	 *            The id of the Teacher to delete.
	 * @return true if the entity doesn't exist after the call, false otherwise
	 */
	Boolean deleteTeacher(Long id);

	/**
	 * Associates a Person with an Organisation for which he/she works.
	 * 
	 * @param idPerson
	 *            The identifier of a Person
	 * @param idOrganisation
	 *            The identifier of an Organisation
	 * @return True if the Person and Organisation are associated at the end of
	 *         the method, false otherwise
	 */
	Boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	/**
	 * Dissociates a Person with an Organisation for which he/she used to work.
	 * 
	 * @param idPerson
	 *            The identifier of a Person
	 * @param idOrganisation
	 *            The identifier of an Organisation
	 * @return true if the Person and Organisation are not associated at the end
	 *         of the method, false otherwise
	 */
	Boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	/**
	 * Adds a ResourceCategory as favorite for the Teacher whose id is given.
	 * 
	 * @param idTeacher
	 *            The identifier of a Teacher
	 * @param idResourceCategory
	 *            The identifier of a ResourceCategory
	 * @return true if the Teacher and ResourceCategory are associated at the
	 *         end of the method, false otherwise
	 */
	Boolean addFavoriteToolCategoryForPerson(Long idTeacher, Long idResourceCategory);

	/**
	 * Removes a ResourceCategory from the favorites of the Teacher whose id is
	 * given.
	 * 
	 * @param idTeacher
	 *            The identifier of a Teacher
	 * @param idResourceCategory
	 *            The identifier of a ResourceCategory
	 * @return true if the Teacher and ResourceCategory are not associated at
	 *         the end of the method, false otherwise
	 */
	Boolean removeFavoriteToolCategoryForPerson(Long idTeacher, Long idResourceCategory);

	/**
	 * Returns the collection of all existing {@link Teacher}s.
	 * 
	 * @return collection of all Teachers
	 */
	Collection<Teacher> retrieveAllTeachers();

	/**
	 * Returns the Teacher whose login is given. The difference with
	 * {@link #getUser(String)} is that this method will never create a
	 * non-existent Teacher, even when the authentication scheme ensures that
	 * the received login is legitimate.
	 * 
	 * @param login
	 *            The login of the Teacher
	 * @return The Teacher with the given login, or null if it doesn't exist
	 */
	Teacher retrieveTeacherByLogin(String login);

	/**
	 * Gets a user from its login. If the user doesn't exist and the
	 * authentication scheme is "auth-cas" or "auth-saml", the account is
	 * automatically created. If it doesn't exist and the authentication scheme
	 * is "auth-manual", an exception is thrown.
	 * 
	 * @param login
	 *            A user login
	 * @return a User instance
	 * @throws UsernameNotFoundException
	 *             if the user doesn't exist and the authentication scheme
	 *             doesn't guarantee a genuine authentication.
	 */
	Person getUser(String login) throws UsernameNotFoundException;

}
