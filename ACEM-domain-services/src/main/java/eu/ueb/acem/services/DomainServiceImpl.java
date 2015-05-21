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
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.PersonDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.services.auth.LdapUserService;

/**
 * @author Grégoire Colbert
 * @since 2013-11-25
 */
@Service("domainService")
public class DomainServiceImpl implements DomainService, Serializable, InitializingBean, EnvironmentAware {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);

	@Inject
	private PersonDAO<Long, Teacher> teacherDAO;
	
	/**
	 * {@link ldapUserService}.
	 */
	@Inject
	private LdapUserService ldapUserService;
	
	private Environment environment;

	/**
	 * The path for the tmp directory
	 */
	@Value("${tmp.path}")
	private String tmpPath;

	/**
	 * The path for the tmp directory
	 */
	@Value("${images.path}")
	private String imagesPath;

	/**
	 * Auto create users?
	 */
	private Boolean autoCreateUsers;

	/**
	 * Constructor.
	 */
	public DomainServiceImpl() {
		autoCreateUsers = false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
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

	@Override
	public Person getUser(String login) throws UsernameNotFoundException {
		Person user = teacherDAO.retrieveByLogin(login, true);
		// If the user is missing from the database but the authentication mode
		// guarantees a legit authentication, then we want to automatically
		// create a user account.
		if ((user == null) && autoCreateUsers) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user = teacherDAO.create(login, login, passwordEncoder.encode("pass"));
			user.setLogin(login);
			user.setLanguage("fr");
		}
		else if (user == null) {
		    throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

	/**
	 * @see eu.ueb.acem.services.DomainService#getLdapUserService()
	 */
	@Override
	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	/**
	 * @see eu.ueb.acem.services.DomainService#getTemporaryDirectory()
	 */
	@Override
	public String getTemporaryDirectory() {
		return this.tmpPath;
	}

	/**
	 * @see eu.ueb.acem.services.DomainService#getImagesDirectory()
	 */
	@Override
	public String getImagesDirectory() {
		return this.imagesPath;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
}
