package eu.ueb.acem.services.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.DomainService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A bean to manage Spring DAO authentication.
 */
@Service
@Transactional
public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<Authentication>, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7308722131142441000L;

	@Inject
	private DomainService domainService;

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @param targetUser
	 *            The Person to load
	 * @return userDetails for Spring
	 * @throws UsernameNotFoundException
	 *             If the targetUser cannot be loaded (bad targetUser.login
	 *             and/or targetUser.password)
	 */
	public UserDetails loadUserByUser(Person targetUser) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		// Roles
		if (targetUser.isAdministrator()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return new org.springframework.security.core.userdetails.User(targetUser.getLogin(), targetUser.getPassword(),
				true, // enabled
				true, // account not expired
				true, // credentials not expired
				true, // account not locked
				authorities);

	}

	@Override
	public UserDetails loadUserDetails(Authentication arg0) {
		Person user = getDomainService().getUser(arg0.getName());
		return loadUserByUser(user);
	}

}
