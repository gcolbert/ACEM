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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

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
	private static final Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);

	@Autowired
	private EnseignantDAO enseignantDAO;

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
		Personne user = enseignantDAO.retrieveByLogin(uid);
		if (user == null) {
			user = enseignantDAO.create(new EnseignantNode(uid, uid));
			user.setLogin(uid);
			user.setLanguage("fr");
		}
		return user;
	}

}
