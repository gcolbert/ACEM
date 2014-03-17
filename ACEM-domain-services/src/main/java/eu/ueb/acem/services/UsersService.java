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

import java.util.Set;

import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface UsersService {

	Long countTeachers();
	
	Long countPersons();
	
	Personne createPerson(String name, String login);

	Personne retrievePerson(Long id);
	
	Personne updatePerson(Personne person);

	Boolean deletePerson(Long id);

	void deleteAllPersons();
	
	Set<Personne> getPersons();

	Enseignant createTeacher(String name, String login);
	
	Enseignant retrieveTeacher(Long id);
	
	Enseignant updateTeacher(Enseignant teacher);

	Boolean deleteTeacher(Long id);

	void deleteAllTeachers();
	
	Set<? extends Enseignant> getTeachers();

	boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation);

	boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation, String typeOfOrganisation);

}
