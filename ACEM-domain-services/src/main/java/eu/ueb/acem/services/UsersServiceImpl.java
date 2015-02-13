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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.PersonDAO;
import eu.ueb.acem.dal.gris.TeacherDAO;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.neo4j.TeacherNode;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonNode;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	@Inject
	private TeacherDAO teacherDAO;

	@Inject
	private PersonDAO personDAO;

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private ResourcesService resourcesService;

	public UsersServiceImpl() {
	}

	@PostConstruct
	public void initUsersService() {
	}

	@Override
	public Person retrievePersonByLogin(String login) {
		Person person = personDAO.retrieveByLogin(login, false);
		return person;
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
	public Person createPerson(String name, String login, String password) {
		return personDAO.create(new PersonNode(name, login, password));
	}

	@Override
	public Collection<Person> retrieveAllPersons() {
		return personDAO.retrieveAll();
	}
	
	@Override
	public Person retrievePerson(Long id) {
		return personDAO.retrieveById(id);
	}

	@Override
	public Person updatePerson(Person person) {
		Person updatedEntity = personDAO.update(person);
		return updatedEntity;
	}

	@Override
	public Boolean deletePerson(Long id) {
		if (personDAO.exists(id)) {
			personDAO.delete(personDAO.retrieveById(id));
		}
		return (!personDAO.exists(id));
	}

	@Override
	public Teacher createTeacher(String name, String login, String password) {
		return teacherDAO.create(new TeacherNode(name, login, password));
	}

	@Override
	public Teacher retrieveTeacher(Long id) {
		return teacherDAO.retrieveById(id);
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		Teacher updatedEntity = teacherDAO.update(teacher);
		return updatedEntity;
	}

	@Override
	public Boolean deleteTeacher(Long id) {
		if (teacherDAO.exists(id)) {
			teacherDAO.delete(teacherDAO.retrieveById(id));
		}
		return (!teacherDAO.exists(id));
	}

	@Override
	public Boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation) {
		logger.debug("associateUserWorkingForOrganisation");
		boolean success = false;
		if (personDAO.exists(idPerson)) {
			Person person = personDAO.retrieveById(idPerson);
			Organisation organisation = organisationsService.retrieveOrganisation(idOrganisation, true);
			if (organisation != null) {
				person.getWorksForOrganisations().add(organisation);
				person = personDAO.update(person);
				organisation = organisationsService.updateOrganisation(organisation);
				success = true;
				logger.debug("association successful");
			}
			else {
				logger.debug("association failed");
			}
		}
		return success;
	}

	@Override
	public Boolean dissociateUserWorkingForOrganisation(Long idPerson, Long idOrganisation) {
		logger.debug("dissociateUserWorkingForOrganisation");
		boolean success = false;
		if (personDAO.exists(idPerson)) {
			Person person = personDAO.retrieveById(idPerson);
			Organisation organisation = organisationsService.retrieveOrganisation(idOrganisation, true);
			if (organisation != null) {
				person.getWorksForOrganisations().remove(organisation);
				person = personDAO.update(person);
				success = true;
				logger.debug("dissociation successful");
			}
		}
		if (!success) {
			logger.debug("dissociation failed");
		}
		return success;
	}

	@Override
	public Boolean addFavoriteToolCategoryForTeacher(Long idTeacher, Long idToolCategory) {
		logger.info("addFavoriteResourceForTeacher");
		Boolean success = false;
		if (personDAO.exists(idTeacher)) {
			Teacher teacher = teacherDAO.retrieveById(idTeacher);
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(idToolCategory, true);
			if (toolCategory != null) {
				teacher.getFavoriteToolCategories().add(toolCategory);
				teacher = teacherDAO.update(teacher);
				success = true;
			}
		}
		return success;
	}

	@Override
	public Boolean removeFavoriteToolCategoryForTeacher(Long idTeacher, Long idToolCategory) {
		logger.info("removeFavoriteResourceForTeacher");
		Boolean success = false;
		if (personDAO.exists(idTeacher)) {
			Teacher teacher = teacherDAO.retrieveById(idTeacher);
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(idToolCategory, true);
			if (toolCategory != null) {
				teacher.getFavoriteToolCategories().remove(toolCategory);
				teacher = teacherDAO.update(teacher);
				success = true;
			}
		}
		return success;
	}

}
