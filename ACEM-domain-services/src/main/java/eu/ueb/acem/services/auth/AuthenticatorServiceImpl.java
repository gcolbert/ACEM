/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package eu.ueb.acem.services.auth;

import java.io.Serializable;

import org.esupportail.commons.services.authentication.AuthUtils;
import org.esupportail.commons.services.authentication.AuthenticationService;
import org.esupportail.commons.services.authentication.info.AuthInfo;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.dal.gris.GestionnaireDAO;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

/**
 * @author Grégoire Colbert (Université européenne de Bretagne) @since
 *         2013-11-25
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class AuthenticatorServiceImpl implements Serializable, InitializingBean, AuthenticatorService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6382142726147456592L;

	/**
	 * The session attribute to store the auth info.
	 */
	private static final String AUTH_INFO_ATTRIBUTE = AuthenticatorServiceImpl.class.getName() + ".authInfo";

	/**
	 * The session attribute to store the user.
	 */
	private static final String USER_ATTRIBUTE = AuthenticatorServiceImpl.class.getName() + ".user";

	/**
	 * For logging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticatorServiceImpl.class);

	/**
	 * The external authenticator.
	 */
	private AuthenticationService authenticationService;

	/**
	 * DAO for user
	 */
	@Autowired
	private EnseignantDAO enseignantDAO;
	@Autowired
	private GestionnaireDAO gestionnaireDAO;

	/**
	 * Bean constructor.
	 */
	public AuthenticatorServiceImpl() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationService, "property authenticationService of class " + this.getClass().getName()
				+ " can not be null");
	}

	@Override
	public Personne getUser() throws Exception {
		try {
			AuthInfo authInfo = (AuthInfo) ContextUtils.getSessionAttribute(AUTH_INFO_ATTRIBUTE);
			if (authInfo != null) {
				Personne user = (Personne) ContextUtils.getSessionAttribute(USER_ATTRIBUTE);
				if (logger.isDebugEnabled()) {
					logger.debug("found auth info in session: " + user);
				}
				return user;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("no auth info found in session");
			}
			authInfo = authenticationService.getAuthInfo();
			if (authInfo == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("authInfo is null");
				}
				return null;
			}
			if ((AuthUtils.SHIBBOLETH.equals(authInfo.getType())) || (AuthUtils.CAS.equals(authInfo.getType()))) {
				if (logger.isDebugEnabled()) {
					if (AuthUtils.SHIBBOLETH.equals(authInfo.getType())) {
						logger.debug("Shibboleth authentication");
					}
					else if (AuthUtils.CAS.equals(authInfo.getType())) {
						logger.debug("CAS authentication");
					}
				}
				Personne user;
				logger.info("authInfo.getId = {}", authInfo.getId());
				user = gestionnaireDAO.retrieveByLogin(authInfo.getId());
				if (user == null) {
					user = enseignantDAO.retrieveByLogin(authInfo.getId());
					if (user == null) {
						user = enseignantDAO.create(new EnseignantNode(authInfo.getId()));
					}
				}
				storeToSession(authInfo, user);
				return user;
			}
		} catch (Exception e) {
			String[] args = { e.getMessage() };
			throw new Exception(I18nUtils.createI18nService().getString("AUTHENTICATION_EXCEPTION.TITLE",
					(Object[]) args));
		}
		return null;
	}

	/**
	 * Store the authentication information to the session.
	 * 
	 * @param authInfo
	 * @param user
	 */
	protected void storeToSession(final AuthInfo authInfo, final Personne user) {
		if (logger.isDebugEnabled()) {
			logger.debug("storing to session: " + authInfo);
		}
		ContextUtils.setSessionAttribute(AUTH_INFO_ATTRIBUTE, authInfo);
		ContextUtils.setSessionAttribute(USER_ATTRIBUTE, user);
	}

	/**
	 * @param authenticationService
	 *            the authenticationService to set
	 */
	public void setAuthenticationService(final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/**
	 * @return the authenticationService
	 */

	protected AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

}