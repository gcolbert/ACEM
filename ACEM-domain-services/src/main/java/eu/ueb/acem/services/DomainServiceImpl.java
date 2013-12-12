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

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * @author Grégoire Colbert @since 2013-11-25
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
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
	private final Logger logger = new LoggerImpl(this.getClass());

	@Autowired
	private DAO<Enseignant> enseignantDAO;

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
	public Personne getUser(String uid) {
		Personne user = enseignantDAO.retrieve(uid);
		if (user == null) {
			// TODO : on récupère l'utilisateur par le service CAS
			//user.setLogin(uid);
			//user.setNom(uid);
			//user.setLanguage("fr");
			//users.add(user);
		}
		return user;
	}

}
