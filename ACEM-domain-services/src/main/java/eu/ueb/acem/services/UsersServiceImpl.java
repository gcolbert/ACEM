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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.common.gris.PersonDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService, EnvironmentAware {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1969594272542872653L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	@Inject
	private PersonDAO<Long, Teacher> teacherDAO;

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private ResourcesService resourcesService;

	/**
	 * Auto create users?
	 */
	private Boolean autoCreateUsers;

	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public UsersServiceImpl() {
		autoCreateUsers = false;
	}

	@PostConstruct
	public void initUsersService() {
		logger.info("*************************** initUserService********************");
		List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
		List<String> defaultProfiles = Arrays.asList(environment.getDefaultProfiles());
		if (activeProfiles.contains("auth-manual")) {
			autoCreateUsers = false;
		}
		else if (activeProfiles.contains("auth-cas")) {
			autoCreateUsers = true;
		}
		else {
			if (defaultProfiles.contains("auth-manual")) {
				autoCreateUsers = false;
			}
			else if (defaultProfiles.contains("auth-cas")) {
				autoCreateUsers = true;
			}
			else {
				autoCreateUsers = false;
			}
		}
	}

//	@Override
//	public void afterPropertiesSet() throws Exception {
//		List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
//		List<String> defaultProfiles = Arrays.asList(environment.getDefaultProfiles());
//		if (activeProfiles.contains("auth-manual")) {
//			autoCreateUsers = false;
//		}
//		else if (activeProfiles.contains("auth-cas")) {
//			autoCreateUsers = true;
//		}
//		else {
//			if (defaultProfiles.contains("auth-manual")) {
//				autoCreateUsers = false;
//			}
//			else if (defaultProfiles.contains("auth-cas")) {
//				autoCreateUsers = true;
//			}
//			else {
//				autoCreateUsers = false;
//			}
//		}
//	}

	@Override
	public Person getUser(String login) throws UsernameNotFoundException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Person user = teacherDAO.retrieveByLogin(login, true);
		// If the "admin" account gets deleted, we recreate it.
		if (user==null && login.equals("admin")) {
			user = teacherDAO.create("Administrator", "admin", passwordEncoder.encode("admin"));
			user.setAdministrator(true);
			user = teacherDAO.update((Teacher)user);
		}
		// If the user is missing from the database but the authentication mode
		// guarantees a legit authentication (e.g. CAS), then we want to
		// automatically create a user account.
		if ((user == null) && autoCreateUsers) {
			user = teacherDAO.create(login, login, passwordEncoder.encode("pass"));
			user.setLogin(login);
			user.setLanguage("fr");
		}
		else if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

	@Override
	public Teacher retrieveTeacherByLogin(String login) {
		return teacherDAO.retrieveByLogin(login, false);
	}

	@Override
	public Long countTeachers() {
		return teacherDAO.count();
	}

	@Override
	public Teacher createTeacher(String name, String login, String password) {
		return teacherDAO.create(name, login, password);
	}

	@Override
	public Teacher retrieveTeacher(Long id) {
		return teacherDAO.retrieveById(id);
	}

	@Override
	public Person updatePerson(Person person) {
		Person updatedPerson = null;
		if (person instanceof Teacher) {
			updatedPerson = updateTeacher((Teacher) person);
		}
		else {
			logger.error("Unknown concrete type of Person interface.");
			updatedPerson = null;
		}
		return updatedPerson;
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		return teacherDAO.update(teacher);
	}

	@Override
	public Boolean deleteTeacher(Long id) {
		if (teacherDAO.exists(id)) {
			teacherDAO.delete(teacherDAO.retrieveById(id));
		}
		return !teacherDAO.exists(id);
	}

	@Override
	public Boolean associateUserWorkingForOrganisation(Long idPerson, Long idOrganisation) {
		logger.debug("associateUserWorkingForOrganisation");
		boolean success = false;
		if (teacherDAO.exists(idPerson)) {
			Teacher teacher = teacherDAO.retrieveById(idPerson);
			Organisation organisation = organisationsService.retrieveOrganisation(idOrganisation, true);
			if (organisation != null) {
				teacher.getWorksForOrganisations().add(organisation);
				teacher = teacherDAO.update(teacher);
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
		if (teacherDAO.exists(idPerson)) {
			Teacher teacher = teacherDAO.retrieveById(idPerson);
			Organisation organisation = organisationsService.retrieveOrganisation(idOrganisation, true);
			if (organisation != null) {
				teacher.getWorksForOrganisations().remove(organisation);
				teacher = teacherDAO.update(teacher);
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
	public Boolean addFavoriteToolCategoryForPerson(Long idPerson, Long idToolCategory) {
		Boolean success = false;
		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(idToolCategory, true);
		if (toolCategory != null) {
			if (teacherDAO.exists(idPerson)) {
				Teacher teacher = teacherDAO.retrieveById(idPerson);
				teacher.getFavoriteToolCategories().add(toolCategory);
				teacher = teacherDAO.update(teacher);
				success = true;
			}
		}
		return success;
	}

	@Override
	public Boolean removeFavoriteToolCategoryForPerson(Long idPerson, Long idToolCategory) {
		Boolean success = false;
		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(idToolCategory, true);
		if (toolCategory != null) {
			if (teacherDAO.exists(idPerson)) {
				Teacher teacher = teacherDAO.retrieveById(idPerson);
				teacher.getFavoriteToolCategories().remove(toolCategory);
				teacher = teacherDAO.update(teacher);
				success = true;
			}
		}
		return success;
	}

	@Override
	public Collection<Teacher> retrieveAllTeachers() {
		return teacherDAO.retrieveAll();
	}

}
