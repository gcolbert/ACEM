package eu.ueb.acem.web.controllers;

import org.esupportail.commons.utils.Assert;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * An abstract class inherited by all the beans for them to get: - the context
 * of the application (sessionController). - the domain service (domainService).
 * - the application service (applicationService). - the i18n service
 * (i18nService).
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
