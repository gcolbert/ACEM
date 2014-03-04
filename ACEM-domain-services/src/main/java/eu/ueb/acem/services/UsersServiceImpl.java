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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.dal.gris.PersonneDAO;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonneNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	@Autowired
	EnseignantDAO teacherDAO;

	@Autowired
	PersonneDAO personDAO;

	@Autowired
	OrganisationsService organisationsService;

	Set<Personne> persons;
	Set<Enseignant> teachers;

	public UsersServiceImpl() {
		persons = new HashSet<Personne>();
		teachers = new HashSet<Enseignant>();
	}

	@PostConstruct
	public void initUsersService() {
		persons.clear();
		persons.addAll(personDAO.retrieveAll());

		teachers.clear();
		teachers.addAll(teacherDAO.retrieveAll());
	}

	@Override
	public Set<Personne> getPersons() {
		return persons;
	}

	@Override
	public Long countTeachers() {
		return teacherDAO.count();
	}

	@Override
	public Long countPersons() {
		return personDAO.count();
	}

	@Override
	public Personne createPerson(String name, String login) {
		return personDAO.create(new PersonneNode(name, login));
	}

	@Override
	public Personne retrievePerson(Long id) {
		return personDAO.retrieveById(id);
	}

	@Override
	public Personne updatePerson(Personne person) {
		return personDAO.update(person);
	}

	@Override
	public Boolean deletePerson(Long id) {
		if (personDAO.exists(id)) {
			personDAO.delete(personDAO.retrieveById(id));
		}
		return (!personDAO.exists(id));
	}

	@Override
	public void deleteAllPersons() {
		personDAO.deleteAll();
	}

	@Override
	public Set<Enseignant> getTeachers() {
		return teachers;
	}
	
	@Override
	public Enseignant createTeacher(String name, String login) {
		return teacherDAO.create(new EnseignantNode(name, login));
	}

	@Override
	public Enseignant retrieveTeacher(Long id) {
		return teacherDAO.retrieveById(id);
	}

	@Override
	public Enseignant updateTeacher(Enseignant teacher) {
		return teacherDAO.update(teacher);
	}

	@Override
	public Boolean deleteTeacher(Long id) {
		if (teacherDAO.exists(id)) {
			teacherDAO.delete(teacherDAO.retrieveById(id));
		}
		return (!teacherDAO.exists(id));
	}

	@Override
	public void deleteAllTeachers() {
		teacherDAO.deleteAll();
	}

	@Override
	public boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation) {
		logger.info("associateUserWorkingForOrganisation");
		boolean success = false;
		if (personDAO.exists(idPerson)) {
			Personne person = personDAO.retrieveById(idPerson);
			Organisation organisation = organisationsService.retrieveOrganisation(idOrganisation);
			if (organisation != null) {
				person.addWorksForOrganisations(organisation);
				person = personDAO.update(person);
				organisation = organisationsService.updateOrganisation(organisation);
				success = true;
				logger.info("association successful");
			}
			else {
				logger.info("association failed");
			}
		}
		return success;
	}

	@Override
	public boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation) {
		// TODO Auto-generated method stub
		return false;
	}

}
