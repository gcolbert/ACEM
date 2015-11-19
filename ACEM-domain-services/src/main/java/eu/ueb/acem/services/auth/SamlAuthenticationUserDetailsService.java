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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.UsersService;

/**
 * A bean to manage Spring DAO authentication.
 */
@Service
@Transactional
public class SamlAuthenticationUserDetailsService implements SAMLUserDetailsService, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7308722131142441000L;

	private static Logger logger = LoggerFactory.getLogger(SamlAuthenticationUserDetailsService.class);

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
	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
		logger.info("entering loadUserBySAML({})", credential);
		String userID = credential.getNameID().getValue();
		logger.info("{} is logged in", userID);
		Map<String, String> mapOfAttributesFriendlyNamesAndValues = new HashMap<String, String>();
		mapOfAttributesFriendlyNamesAndValues.put("eduPersonAffiliation", null);
		mapOfAttributesFriendlyNamesAndValues.put("eduPersonPrincipalName", null);
		mapOfAttributesFriendlyNamesAndValues.put("eduPersonPrimaryAffiliation", null);
		mapOfAttributesFriendlyNamesAndValues.put("supannEtablissement", null);
		mapOfAttributesFriendlyNamesAndValues.put("supannEntiteAffectationPrincipale", null);
		mapOfAttributesFriendlyNamesAndValues.put("supannOrganisme", null);
		mapOfAttributesFriendlyNamesAndValues.put("displayName", null);
		mapOfAttributesFriendlyNamesAndValues.put("mail", null);
		mapOfAttributesFriendlyNamesAndValues.put("givenName", null);
		mapOfAttributesFriendlyNamesAndValues.put("sn", null);
		mapOfAttributesFriendlyNamesAndValues.put("uid", null);

		for (Attribute attribute : credential.getAttributes()) {
			logger.info("attribute friendly name={}", attribute.getFriendlyName());
			if (mapOfAttributesFriendlyNamesAndValues.containsKey(attribute.getFriendlyName())) {
				// We set the values of the property
				for (XMLObject attributeValueXMLObject : credential.getAttribute(attribute.getName()).getAttributeValues()) {
					logger.info("We care about this attribute, getAttributeValue={}", getAttributeValue(attributeValueXMLObject));
					mapOfAttributesFriendlyNamesAndValues.put(attribute.getFriendlyName(), getAttributeValue(attributeValueXMLObject));
				}
			}
			else {
				logger.info("We don't care about this attribute");
			}
		}
		// TODO : vérifier que l'utilisateur est dans "staff" (voir la doc ICC pour le test exact à appliquer)
		Person user = usersService.getUser(mapOfAttributesFriendlyNamesAndValues.get("eduPersonPrincipalName"));
		user.setLogin(mapOfAttributesFriendlyNamesAndValues.get("eduPersonPrincipalName"));
		user.setEmail(mapOfAttributesFriendlyNamesAndValues.get("mail"));
		user.setName(mapOfAttributesFriendlyNamesAndValues.get("displayName"));
		user.setAdministrator(true);
		user = usersService.updatePerson(user);
		logger.info("leaving loadUserBySAML");
		return loadUserByUser(user);
	}

	private String getAttributeValue(XMLObject attributeValueXMLObject) {
		String attributeValue = null;
		if (attributeValueXMLObject != null) {
			if (attributeValueXMLObject instanceof XSString) {
				attributeValue = getStringAttributeValue((XSString) attributeValueXMLObject);
			}
			else {
				if (attributeValueXMLObject instanceof XSAnyImpl) {
					attributeValue = getAnyAttributeValue((XSAnyImpl) attributeValueXMLObject);
				}
				else {
					attributeValue = attributeValueXMLObject.toString();
				}
			}
		}
		return attributeValue;
	}

	private String getStringAttributeValue(XSString attributeValue) {
		return attributeValue.getValue();
	}

	private String getAnyAttributeValue(XSAnyImpl attributeValue) {
		return attributeValue.getTextContent();
	}

}
