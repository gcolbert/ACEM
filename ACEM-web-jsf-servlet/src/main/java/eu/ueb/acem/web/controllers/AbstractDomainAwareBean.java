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
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.DomainService;

/**
 * An abstract class inherited by all the beans for them to get: - the domain
 * service (domainService). - the application service (applicationService). -
 * the i18n service (i18nService).
 */
@Component("abstractDomainAwareBean")
public abstract class AbstractDomainAwareBean implements InitializingBean, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3917164118541810598L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractDomainAwareBean.class);

	/**
	 * see {@link DomainService}.
	 */
	@Inject
	private DomainService domainService;
	
	/**
	 * Constructor.
	 */
	protected AbstractDomainAwareBean() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public final void afterPropertiesSet() {
		Validate.notNull(this.domainService, "property domainService of class " + this.getClass().getName()
				+ " can not be null");
		afterPropertiesSetInternal();
		reset();
	}

	/**
	 * This method is run once the object has been initialized, just before
	 * reset().
	 */
	protected void afterPropertiesSetInternal() {
		// override this method
	}

	public void reset() {
		// nothing to reset
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

	/**
	 * @return the domainService
	 */
	public DomainService getDomainService() {
		return domainService;
	}

}
