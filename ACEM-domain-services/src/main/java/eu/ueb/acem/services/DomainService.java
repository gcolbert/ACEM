/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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

import java.io.Serializable;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.auth.LdapUserService;

/**
 * @author Grégoire Colbert
 * @since 2013-11-25
 */
public interface DomainService extends Serializable {

	/**
	 * Gets a user from its login. If the user doesn't exist and the
	 * authentication scheme is "auth-cas", the account is automatically
	 * created. If it doesn't exist and the authentication scheme is
	 * "auth-manual", an exception is thrown.
	 * 
	 * @param login
	 *            A user login
	 * @return a User instance
	 * @throws UsernameNotFoundException
	 *             if the user doesn't exist and the authentication scheme
	 *             doesn't guarantee a genuine authentication.
	 */
	Person getUser(String login) throws UsernameNotFoundException;

	LdapUserService getLdapUserService();

	/**
	 * @return Path to the temp directory for uploaded files
	 */
	String getTemporaryDirectory();

	/**
	 * @return Path to the images directory
	 */
	String getImagesDirectory();

}
