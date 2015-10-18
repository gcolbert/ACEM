/**
 *     Copyright Grégoire COLBERT 2015
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

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;

/**
 * An abstract class inherited by all the controllers for them to get:
 * 
 * <ul>
 * <li>the context of the application (sessionController);</li>
 * <li>the ReloadableResourceBundleMessageSource (msgs).
 * </ul>
 */
@Controller("abstractContextAwareController")
@Scope("session")
public abstract class AbstractContextAwareController implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1826458262448752328L;

	/**
	 * The SessionController.
	 */
	@Inject
	private SessionController sessionController;

	/**
	 * The reloadable resource bundle message source (for i18n strings).
	 */
	@Inject
	protected ReloadableResourceBundleMessageSource msgs;

	/**
	 * Constructor.
	 */
	protected AbstractContextAwareController() {
		super();
	}

	public Person getCurrentUser() {
		return sessionController.getCurrentUser();
	}

	public PersonViewBean getCurrentUserViewBean() {
		return sessionController.getCurrentUserViewBean();
	}

	/**
	 * @return the sessionController
	 */
	public SessionController getSessionController() {
		return sessionController;
	}

}
