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
	 * Constructor with attributes names in Ldap
	 * @param uidAttribute
	 * @param nameAttribute
	 * @param givenNameAttribute
	 * @param emailAttribute
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
