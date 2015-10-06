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
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface UsersService extends Serializable {

	/**
	 * Gets a user from its login. If the user doesn't exist and the
	 * authentication scheme is "auth-cas", the account is automatically
	 * created. If it doesn't exist and the authentication scheme is
	 * "auth-manual", an exception is thrown.
	 * 
	 * @param login
	 *            A user login
	 * @return a User instance
	 * @throws UsernameNotFoundException
	 *             if the user doesn't exist and the authentication scheme
	 *             doesn't guarantee a genuine authentication.
	 */
	Person getUser(String login) throws UsernameNotFoundException;	

	Long countTeachers();

	Teacher createTeacher(String name, String login, String password);

	Teacher retrieveTeacher(Long id);

	Person updatePerson(Person domainBean);

	Teacher updateTeacher(Teacher teacher);

	Boolean deleteTeacher(Long id);

	Boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	Boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	Boolean addFavoriteToolCategoryForPerson(Long idTeacher, Long idToolCategory);

	Boolean removeFavoriteToolCategoryForPerson(Long idTeacher, Long idToolCategory);

	Collection<Teacher> retrieveAllTeachers();

	Teacher retrieveTeacherByLogin(String login);

}
