/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package eu.ueb.acem.services.auth;

import org.springframework.stereotype.Service;

import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * @author Grégoire Colbert @since 2013-11-25
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 */
@Service("authenticatorService")
public interface AuthenticatorService {

	/**
	 * @return the authenticated user.
	 * @throws Exception 
	 */
	Personne getUser() throws Exception;

}