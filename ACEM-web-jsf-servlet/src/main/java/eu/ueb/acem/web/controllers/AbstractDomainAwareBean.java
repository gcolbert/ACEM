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
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.gris.Person;

/**
 * An abstract class inherited by all the beans for them to get:
 * - the application service (applicationService).
 * - the i18n service (i18nService).
 */
@Component("abstractDomainAwareBean")
public abstract class AbstractDomainAwareBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3917164118541810598L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractDomainAwareBean.class);

	/**
	 * Constructor.
	 */
	protected AbstractDomainAwareBean() {
		super();
	}

	/**
	 * @return the current user.
	 */
	protected abstract Person getCurrentUser();

	/**
	 * @return the current user's locale.
	 */
	public Locale getCurrentUserLocale() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//		Person currentUser = getCurrentUser();
//		if (currentUser == null) {
//			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//		}
//		else {
//			String lang = currentUser.getLanguage();
//			if (lang == null) {
//				locale = new Locale("fr");
//			}
//			else {
//				locale = new Locale(lang);
//			}
//		}
		return locale;
	}

}
