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

import eu.ueb.acem.domain.beans.ldap.LdapUser;

import javax.naming.directory.Attributes;
import javax.naming.NamingException;

import org.springframework.ldap.core.AttributesMapper;

/**
 * Simple mapping class for String attributes from Ldap to LdapUser
 * Id / LastName / FirstName / Mail 
 * @author rlorthio
 *
 */
public class LdapAttributesForMapping implements AttributesMapper<LdapUser>, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6946533694863497869L;

	private String uidAttribute;

	private String nameAttribute;

	private String givenNameAttribute;

	private String emailAttribute;

	/**
	 * Constructor with attribute names in Ldap
	 * 
	 * @param uidAttribute The UID attribute
	 * @param nameAttribute The name attribute
	 * @param givenNameAttribute The givenName attribute
	 * @param emailAttribute The email attribute
	 */
	public LdapAttributesForMapping(String uidAttribute, String nameAttribute,
			String givenNameAttribute, String emailAttribute) {
		super();
		this.uidAttribute = uidAttribute;
		this.nameAttribute = nameAttribute;
		this.givenNameAttribute = givenNameAttribute;
		this.emailAttribute = emailAttribute;
	}
	
	/**
	 * @see org.springframework.ldap.core.AttributesMapper#mapFromAttributes(javax.naming.directory.Attributes)
	 */
	@Override
    public LdapUser mapFromAttributes(Attributes attrs) throws NamingException {
    	LdapUser ldapUser = new LdapUser();

    	if (attrs.get(uidAttribute)!=null && attrs.get(uidAttribute).get()!=null){
    		ldapUser.setId((String)attrs.get(uidAttribute).get());
    	}
    	if (attrs.get(nameAttribute)!=null && attrs.get(nameAttribute).get()!=null){
    		ldapUser.setLastName((String)attrs.get(nameAttribute).get());
    	}
    	if (attrs.get(givenNameAttribute)!=null && attrs.get(givenNameAttribute).get()!=null){
    		ldapUser.setFirstName((String)attrs.get(givenNameAttribute).get());
    	}
    	if (attrs.get(emailAttribute)!=null && attrs.get(emailAttribute).get()!=null){
    		ldapUser.setEmail((String)attrs.get(emailAttribute).get());
    	}
    	
        return ldapUser;
     }	
}
