/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.ldap.LdapUser;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.utils.OrganisationViewBeanGenerator;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

@Controller("adminUsersController")
@Scope("view")
public class AdminUsersController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -977386846045010683L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdminUsersController.class);

	private PersonViewBean selectedUserViewBean;

	@Inject
	private PickListBean pickListBean;

	@Inject
	private UsersService usersService;

	@Inject
	private OrganisationsService organisationsService;

	private List<PersonViewBean> personViewBeans;

	@Override
	public String getPageTitle() {
		return msgs.getMessage(
				"ADMINISTRATION.USERS.HEADER", null,
				getCurrentUserLocale());
	}

	/*************** LDAP ********************/
	/**
	 * True if ldap search is active
	 */
	@Value("${ldap.search}")
	private boolean existldapSearch;

	/**
	 * String for LDAP search
	 */
	private String searchLdap;

	/**
	 * The result of the search, as a list of LdapUser.
	 */
	private List<LdapUser> ldapUsers;

	/**
	 * The selected User in the List
	 */
	private LdapUser selectedLdapUser;

	/**
	 * The LDAP attribute that contains the name.
	 */
	@Value("${ldap.nameAttribute}")
	private String nameLdapAttribute;

	/**
	 * The LDAP attribute that contains the lastname.
	 */
	@Value("${ldap.givenNameAttribute}")
	private String lastNameLdapAttribute;

	/**
	 * The LDAP attribute that contains the mail.
	 */
	@Value("${ldap.emailAttribute}")
	private String mailLdapAttribute;
	
	public AdminUsersController() {
		personViewBeans = new ArrayList<PersonViewBean>();
	}

	@PostConstruct
	public void init() {
		personViewBeans.clear();
		Collection<Person> persons = usersService.retrieveAllPersons();
		for (Person person : persons) {
			PersonViewBean personViewBean;
			if (person instanceof Teacher) {
				Teacher teacher = (Teacher)person;
				personViewBean = new TeacherViewBean(teacher);
			}
			else {
				personViewBean = new PersonViewBean(person);
			}
			for (Organisation organisation : person.getWorksForOrganisations()) {
				personViewBean.getOrganisationViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisation));
			}
			personViewBeans.add(personViewBean);
		}
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public List<PersonViewBean> getPersonViewBeans() {
		return personViewBeans;
	}

	public PersonViewBean getSelectedUserViewBean() {
		return selectedUserViewBean;
	}

	public void setSelectedUserViewBean(PersonViewBean selectedUserViewBean) {
		this.selectedUserViewBean = selectedUserViewBean;
	}

	public void preparePicklistOrganisationViewBeans() {
		logger.debug("preparePicklistOrganisationViewBeans");
		if (getSelectedUserViewBean() != null) {
			List<OrganisationViewBean> allOrganisationViewBeans = new ArrayList<OrganisationViewBean>();
			for (Organisation organisation : organisationsService.retrieveAllOrganisations()) {
				allOrganisationViewBeans.add(OrganisationViewBeanGenerator.getViewBean(organisation));
			}
			Collections.sort(allOrganisationViewBeans);
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(allOrganisationViewBeans);
			pickListBean.getPickListEntities().getTarget().clear();
			for (OrganisationViewBean organisationViewBean : allOrganisationViewBeans) {
				if (getSelectedUserViewBean().getDomainBean().getWorksForOrganisations().contains(organisationViewBean.getDomainBean())) {
					pickListBean.getPickListEntities().getSource().remove(organisationViewBean);
					pickListBean.getPickListEntities().getTarget().add(organisationViewBean);
				}
			}
		}
	}

	public void setAdministrator(PersonViewBean personViewBean) {
		personViewBean.getDomainBean().setAdministrator(personViewBean.getAdministrator());
		personViewBean.setDomainBean(usersService.updatePerson(personViewBean.getDomainBean()));
		try {
			// if the modified personViewBean is the currentUserViewBean, we update currentUserViewBean too
			if (getSessionController().getCurrentUserViewBean().equals(personViewBean)) {
				getSessionController().getCurrentUserViewBean().setAdministrator(personViewBean.getAdministrator());
			}
		}
		catch (Exception e) {
			
		}
	}

	public void onTransfer(TransferEvent event) {
		logger.debug("onTransfer");
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		try {
			for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
				if (event.isAdd()) {
					logger.debug("We should associate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					if (usersService.associateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId())) {
						selectedUserViewBean.getDomainBean().getWorksForOrganisations().add(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.getOrganisationViewBeans().add(movedOrganisationViewBean);
						logger.debug("association successful");
					}
					else {
						logger.debug("association failed");
					}
				}
				else {
					logger.debug("We should dissociate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					if (usersService.dissociateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId())) {
						selectedUserViewBean.getDomainBean().getWorksForOrganisations().remove(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.getOrganisationViewBeans().remove(movedOrganisationViewBean);
						logger.debug("dissociation successful");
					}
					else {
						logger.debug("dissociation failed");
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("onTransfer exception = ", e);
		}
	}

	/**
	 * This method tries to disable the organisationViewBeans for the "works for organisation" relationships.
	 * 
	 * Example : if the person works for UNIV, and UNIV is associated with COMMUNITY, then when we say
	 * the person works for UNIV, we should disable COMMUNITY (because he implicitly can access resources of
	 * COMMUNITY through the UNIV->COMMUNITY association). And so there is no need to state that he works
	 * for COMMUNITY too.
	 */
	public Boolean isDisabledInPickList(OrganisationViewBean organisationViewBean) {
		if (selectedUserViewBean.getOrganisationViewBeans().contains(organisationViewBean)) {
			return false;
		}
		else {
			for (OrganisationViewBean organisationViewBeanThatSelectedUserWorksFor : selectedUserViewBean.getOrganisationViewBeans()) {
				if (organisationsService.isImplicitlySharingResourcesWith(organisationViewBean.getDomainBean(),organisationViewBeanThatSelectedUserWorksFor.getDomainBean())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/************************************************* LDAP *******************************************/
	/**
	 * True if a user is selected in the list of LDAP users
	 * 
	 * @return true/false
	 */
	public boolean isExistSelectedLdapUser() {
		boolean r = false;
		if (this.selectedLdapUser != null) {
			r = true;
		}
		return r;
	}

	/**
	 * List of users from LDAP for user selection
	 * 
	 * @return the list of users
	 */
	public List<LdapUser> getLdapUsers() {
		if (ldapUsers == null) {
			this.ldapUsers = new ArrayList<LdapUser>();
			if (searchLdap != null && searchLdap.trim().length() > 2) {
				try{
					this.ldapUsers = getDomainService().getLdapUserService().findAllByCnAndUid(searchLdap);
				}
				catch(Exception e){
					MessageDisplayer.error(e, msgs, getCurrentUserLocale(), logger);
				}
			}
		}
		return this.ldapUsers;
	}

	/**
	 * Set the list of LDAP users
	 * 
	 * @param list
	 *            The list of LDAP users
	 */
	public void setLdapUser(List<LdapUser> list) {
		this.ldapUsers = list;
	}

	/**
	 * @return the string to search in LDAP
	 */
	public String getSearchLdap() {
		return searchLdap;
	}

	/**
	 * @param searchLdap
	 *            the string to search in LDAP
	 */
	public void setSearchLdap(String searchLdap) {
		this.searchLdap = searchLdap;
		this.ldapUsers = null;
	}

	/**
	 * Get the selected LDAP user
	 * 
	 * @return The selected LDAP user
	 */
	public LdapUser getSelectedLdapUser() {
		return selectedLdapUser;
	}

	/**
	 * Set the selected LDAP user
	 * 
	 * @param selectedLdapUser
	 *            The LDAP user to set
	 */
	public void setSelectedLdapUser(LdapUser selectedLdapUser) {
		this.selectedLdapUser = selectedLdapUser;
	}

	/**
	 * Add a selected LDAP User
	 */
	public void addFromLdapAction() {
		if (selectedLdapUser != null) {
			Person person = usersService.retrievePersonByLogin(selectedLdapUser.getId());
			if (person == null) {
				Teacher teacher = usersService.createTeacher(selectedLdapUser.getFirstName()+" "+selectedLdapUser.getLastName(), selectedLdapUser.getId(), "pass");
				teacher.setEmail(selectedLdapUser.getEmail());
				PersonViewBean personViewBean = new PersonViewBean(teacher);
				personViewBeans.add(personViewBean);
				Collections.sort(personViewBeans);
			}
			else {
				for (PersonViewBean personViewBean : personViewBeans) {
					if (personViewBean.getId().equals(person.getId())) {
						person.setName(selectedLdapUser.getFirstName()+" "+selectedLdapUser.getLastName());
						person.setEmail(selectedLdapUser.getEmail());
						personViewBean.setDomainBean(person);
					}
				}
			}
		}
		logger.debug("AdminUserController addFromLdapAction ");
	}

	/**
	 * Get the selected LDAP user for toolbar update
	 * 
	 * @param event
	 */
	public void onRowSelectLdapUser(SelectEvent event) {
		this.selectedLdapUser = (LdapUser) event.getObject();
	}
	
	
}
