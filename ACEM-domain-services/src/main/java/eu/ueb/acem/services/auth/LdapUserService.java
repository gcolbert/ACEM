package eu.ueb.acem.services.auth;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import eu.ueb.acem.domain.beans.ldap.LdapUser;

/**
 * Simple Ldap Service to get a list of ldap User from a search text
 * Use configuration in Ldap.xml
 * 
 * @author Romuald Lorthioir
 */
@Service("ldapUserService")
public class LdapUserService {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LdapUserService.class);

	private LdapTemplate ldapTemplate;

	private String uidAttribute;

	private String nameAttribute;

	private String givenNameAttribute;

	private String emailAttribute;

	private String searchAttribute;

	private String objectclass;

	/**
	 * Get Users from Ldap on searchAttribute (cn) and uid
	 * 
	 * @param search
	 *            text(wildcards will be inserted)
	 * @return
	 */
	public List<LdapUser> findAllByCnAndUid(String search) {
		// Mapping
		LdapAttributesForMapping ldapAttributesForMapping = new LdapAttributesForMapping(
				uidAttribute, nameAttribute, givenNameAttribute, emailAttribute);
		//Build from Ldap
		List<LdapUser> ldapUsers = ldapTemplate.search(
				query().where("objectclass")
						.is(objectclass)
						.and(query().where(searchAttribute)
								.whitespaceWildcardsLike(search)
								.or(uidAttribute)
								.whitespaceWildcardsLike(search)),
				ldapAttributesForMapping);
		return ldapUsers;
	}

	/**
	 * Given by Ldap.xml
	 * @param ldapTemplate
	 */
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	/**
	 * Given by Ldap.xml
	 * @param uidAttribute
	 */
	public void setUidAttribute(String uidAttribute) {
		this.uidAttribute = uidAttribute;
	}

	/**
	 * Given by Ldap.xml
	 * @param nameAttribute
	 */
	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}

	/**
	 * Given by Ldap.xml
	 * @param givenNameAttribute
	 */
	public void setGivenNameAttribute(String givenNameAttribute) {
		this.givenNameAttribute = givenNameAttribute;
	}

	/**
	 * Given by Ldap.xml
	 * @param emailAttribute
	 */
	public void setEmailAttribute(String emailAttribute) {
		this.emailAttribute = emailAttribute;
	}

	/**
	 * Given by Ldap.xml
	 * @param searchAttribute
	 */
	public void setSearchAttribute(String searchAttribute) {
		this.searchAttribute = searchAttribute;
	}

	/**
	 * Given by Ldap.xml
	 * @param objectclass
	 */
	public void setObjectclass(String objectclass) {
		this.objectclass = objectclass;
	}
}
