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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.TeacherDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.neo4j.TeacherNode;
import eu.ueb.acem.services.auth.LdapUserService;

/**
 * @author Grégoire Colbert
 * @since 2013-11-25
 */
@Service("domainService")
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);

	@Inject
	private TeacherDAO teacherDAO;

	/**
	 * {@link ldapUserService}.
	 */
	@Inject
	private LdapUserService ldapUserService;

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
	 * Constructor.
	 */
	public DomainServiceImpl() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// nothing to do yet.
	}

	@Override
	public Person getUser(String login) {
		Person user = teacherDAO.retrieveByLogin(login, true);
		if (user == null) {
			user = teacherDAO.create(new TeacherNode(login, login, "pass"));
			user.setLogin(login);
			user.setLanguage("fr");
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
	
}
