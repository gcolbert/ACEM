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
package eu.ueb.acem.web.controllers;

import org.esupportail.commons.utils.Assert;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * An abstract class inherited by all the beans for them to get:
 * 
 * <ul>
 * <li>the context of the application (sessionController).</li>
 * <li>the domain service (domainService).</li>
 * <li>the application service (applicationService).</li>
 * <li>the i18n service (i18nService).
 * </ul>
 */
@Component
public abstract class AbstractContextAwareController extends AbstractDomainAwareBean {

	/*
	 * ****************** PROPERTIES ********************
	 */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1826458262448752328L;

	/**
	 * The SessionController.
	 */
	private SessionController sessionController;

	/*
	 * ****************** INIT ********************
	 */

	/**
	 * Constructor.
	 */
	protected AbstractContextAwareController() {
		super();
	}

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.sessionController, "property sessionController of class " + this.getClass().getName()
				+ " can not be null");
	}

	/*
	 * ****************** CALLBACK ********************
	 */

	/*
	 * ****************** METHODS ********************
	 */

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#getCurrentUser()
	 */
	@Override
	protected Personne getCurrentUser() throws Exception {
		return sessionController.getCurrentUser();
	}

	/**
	 * @param sessionController
	 *            the sessionController to set
	 */
	public void setSessionController(final SessionController sessionController) {
		this.sessionController = sessionController;
	}

	/**
	 * @return the sessionController
	 */
	public SessionController getSessionController() {
		return sessionController;
	}

}
