/**
 *     Copyright Grégoire COLBERT 2013
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
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

@Controller("usersController")
@Scope("view")
public class UsersController extends AbstractContextAwareController {

	private static final long serialVersionUID = -977386846045010683L;

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private List<PersonViewBean> personViewBeans;

	private PersonViewBean currentUserViewBean;

	@Autowired
	private PickListBean pickListBean;
	
	@Autowired
	private UsersService usersService;

	public UsersController() {
		personViewBeans = new ArrayList<PersonViewBean>();
	}

	@PostConstruct
	public void initUsersController() {
		personViewBeans.clear();
		Set<Personne> persons = usersService.getPersons();
		for (Personne person : persons) {
			personViewBeans.add(new PersonViewBean(person));
		}
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}
	
	public List<PersonViewBean> getPersonViewBeans() {
		return personViewBeans;
	}

	public void setPersonViewBeans(List<PersonViewBean> personViewBeans) {
		this.personViewBeans = personViewBeans;
	}

	public PersonViewBean getCurrentUserViewBean() {
		return currentUserViewBean;
	}

	public void setCurrentUserViewBean(PersonViewBean currentUserViewBean) {
		this.currentUserViewBean = currentUserViewBean;
	}

	public void setAdministrator(PersonViewBean personViewBean) {
		logger.info("setAdministrator({})", personViewBean.getAdministrator());
		personViewBean.getDomainBean().setAdministrator(personViewBean.getAdministrator());
		personViewBean.setDomainBean(usersService.updatePerson(personViewBean.getDomainBean()));
	}

	public void onTransfer(TransferEvent event) {
		logger.info("onTransfer");
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		try {
			for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
				if (event.isAdd()) {
					logger.info("We should associate {} and {}", movedOrganisationViewBean.getName(), getCurrentUser()
							.getName());
					if (usersService.associateUserWorkingForOrganisation(getCurrentUser().getId(),
							movedOrganisationViewBean.getDomainBean().getId())) {
						currentUserViewBean.getOrganisationViewBeans().add(movedOrganisationViewBean);
						logger.info("association successful");
					}
					else {
						logger.info("association failed");
					}
					currentUserViewBean.setDomainBean(usersService.retrievePerson(currentUserViewBean.getId()));
				}
				else {
					logger.info("We should dissociate {} and {}", movedOrganisationViewBean.getName(), getCurrentUser()
							.getName());
					if (usersService.dissociateUserWorkingForOrganisation(getCurrentUser().getId(),
							movedOrganisationViewBean.getDomainBean().getId())) {
						currentUserViewBean.getOrganisationViewBeans().remove(movedOrganisationViewBean);
						logger.info("dissociation successful");
					}
					else {
						logger.info("dissociation failed");
					}
					currentUserViewBean.setDomainBean(usersService.retrievePerson(currentUserViewBean.getId()));
				}
			}
		}
		catch (Exception e) {
			logger.error("onTransfer exception = ", e);
		}
		/*-
		if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
			getCurrentOrganisationViewBean().setDomainBean(
					organisationsService.retrieveCommunity(getCurrentOrganisationViewBean().getDomainBean().getId()));
		}
		else if (getCurrentOrganisationViewBean() instanceof InstitutionViewBean) {
			getCurrentOrganisationViewBean().setDomainBean(
					organisationsService.retrieveInstitution(getCurrentOrganisationViewBean().getDomainBean().getId()));
		}
		else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
			getCurrentOrganisationViewBean().setDomainBean(
					organisationsService.retrieveAdministrativeDepartment(getCurrentOrganisationViewBean()
							.getDomainBean().getId()));
		}
		else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
			getCurrentOrganisationViewBean().setDomainBean(
					organisationsService.retrieveTeachingDepartment(getCurrentOrganisationViewBean().getDomainBean()
							.getId()));
		}
		 */
	}

}
