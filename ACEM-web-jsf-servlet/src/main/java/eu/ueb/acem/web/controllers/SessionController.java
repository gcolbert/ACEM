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

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;

/**
 * A bean to memorize the context of the application.
 */
public class SessionController extends AbstractDomainAwareBean {

	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SessionController.class);

	/**
	 * The serialization id.
	 */
	static final long serialVersionUID = -5936434246704000653L;
	
	/*
	 * ****************** PROPERTIES ********************
	 */
	private Authentication auth;

	private PersonViewBean currentUserViewBean;
	
	/*
	 * ****************** INIT ********************
	 */

	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
	}

	/*
	 * ****************** CALLBACK ********************
	 */

	/*
	 * ****************** METHODS ********************
	 */
	
	public PersonViewBean getCurrentUserViewBean() {
		if (currentUserViewBean == null) {
			try {
				Person user = getCurrentUser();
				if (user instanceof Teacher) {
					currentUserViewBean = new TeacherViewBean((Teacher) user);
				}
				else {
					currentUserViewBean = new PersonViewBean(user);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return currentUserViewBean;
	}

	/**
	 * @return the current user, or null if guest.
	 * @throws Exception
	 */
	@Override
	public Person getCurrentUser() throws Exception {
		if (this.auth == null) {
			this.auth = SecurityContextHolder.getContext().getAuthentication();
		}
		Person user = getDomainService().getUser(auth.getName());
		
		return user;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * JSF callback.
	 * 
	 * @return a String.
	 * @throws IOException
	 */
	public String logout() throws IOException {
		/*-
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
		String forwardUrl;
		Validate.notNull(casLogoutUrl, "property casLogoutUrl of class " + getClass().getName() + " is null");
		forwardUrl = String.format(casLogoutUrl, StringUtils.utf8UrlEncode(returnUrl));
		// note: the session beans will be kept even when invalidating
		// the session so they have to be reset (by the exception controller).
		// We invalidate the session however for the other attributes.
		request.getSession().invalidate();
		request.getSession(true);
		// calling this method will reset all the beans of the application
		externalContext.redirect(forwardUrl);
		facesContext.responseComplete();
		*/
		return null;
	}

	/*
	 * ****************** ACCESSORS ********************
	 */


}
