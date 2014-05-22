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

import java.util.Locale;

import javax.inject.Inject;

import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.controllers.Resettable;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.DomainService;

/**
 * An abstract class inherited by all the beans for them to get: - the domain
 * service (domainService). - the application service (applicationService). -
 * the i18n service (i18nService).
 */
@Component("abstractDomainAwareBean")
public abstract class AbstractDomainAwareBean extends AbstractJsfMessagesAwareBean implements Resettable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3917164118541810598L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());

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
		super.afterPropertiesSet();
		Assert.notNull(this.domainService, "property domainService of class " + this.getClass().getName()
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

	/**
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	public void reset() {
		// nothing to reset

	}

	/**
	 * @return the current user.
	 */
	protected Personne getCurrentUser() throws Exception {
		// this method should be overriden
		return null;
	}

	/**
	 * @return the current user's locale.
	 */
	@Override
	public Locale getCurrentUserLocale() {
		if (logger.isDebugEnabled()) {
			logger.debug(this.getClass().getName() + ".getCurrentUserLocale()");
		}
		Personne currentUser = null;
		try {
			currentUser = getCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (currentUser == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("no current user, return null");
			}
			return null;
		}
		String lang = currentUser.getLanguage();
		if (lang == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("language not set for user '" + currentUser.getLogin() + "', return null");
			}
			return null;
		}
		Locale locale = new Locale(lang);
		if (logger.isDebugEnabled()) {
			logger.debug("language for user '" + currentUser.getLogin() + "' is '" + locale + "'");
		}
		return locale;
	}

	/**
	 * @param domainService
	 *            the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the domainService
	 */
	public DomainService getDomainService() {
		return domainService;
	}

}
