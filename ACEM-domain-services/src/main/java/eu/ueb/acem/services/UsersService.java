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
package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface UsersService {

	Long countPersons();
	
	Person createPerson(String name, String login, String password);

	Collection<Person> retrieveAllPersons();
	
	Person retrievePerson(Long id);

	Person retrievePersonByLogin(String login);

	Person updatePerson(Person person);

	Boolean deletePerson(Long id);

	Long countTeachers();
	
	Teacher createTeacher(String name, String login, String password);
	
	Teacher retrieveTeacher(Long id);
	
	Teacher updateTeacher(Teacher teacher);

	Boolean deleteTeacher(Long id);

	Boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	Boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	Boolean addFavoriteToolCategoryForTeacher(Long idTeacher, Long idToolCategory);
	
	Boolean removeFavoriteToolCategoryForTeacher(Long idTeacher, Long idToolCategory);

}
