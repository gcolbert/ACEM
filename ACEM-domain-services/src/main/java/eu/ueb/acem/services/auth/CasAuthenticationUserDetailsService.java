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
package eu.ueb.acem.services.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.UsersService;

/**
 * A bean to manage Spring CAS authentication.
 */
@Service
@Transactional
public class CasAuthenticationUserDetailsService implements AuthenticationUserDetailsService<Authentication>, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7308722131142441000L;

	@Inject
	private UsersService usersService;

	/**
	 * @param targetUser
	 *            The Person to load
	 * @return userDetails for Spring
	 * @throws UsernameNotFoundException
	 *             If the targetUser cannot be loaded (bad targetUser.login
	 *             and/or targetUser.password)
	 */
	private UserDetails loadUserByUser(Person targetUser) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		// Roles
		if (targetUser.isAdministrator()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return new User(targetUser.getLogin(), targetUser.getPassword(),
				true, // enabled
				true, // account not expired
				true, // credentials not expired
				true, // account not locked
				authorities);

	}

	@Override
	public UserDetails loadUserDetails(Authentication arg0) {
		Person user = usersService.getUser(arg0.getName());
		return loadUserByUser(user);
	}

}
