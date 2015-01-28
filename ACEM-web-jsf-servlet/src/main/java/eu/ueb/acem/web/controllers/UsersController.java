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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.utils.OrganisationViewBeanGenerator;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

@Controller("usersController")
@Scope("view")
public class UsersController extends AbstractContextAwareController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -977386846045010683L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private PersonViewBean selectedUserViewBean;

	@Inject
	private PickListBean pickListBean;

	@Inject
	private UsersService usersService;

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private OrganisationViewBeanGenerator organisationViewBeanGenerator;

//	private Set<Person> personViewBeans;

	public UsersController() {
	}

	@PostConstruct
	public void init() {
//		personViewBeans.clear();
//		Set<Person> persons = usersService.getPersons();
//		for (Person person : persons) {
//			PersonViewBean personViewBean;
//			if (person instanceof Teacher) {
//				Teacher teacher = (Teacher)person;
//				personViewBean = new TeacherViewBean(teacher);
//			}
//			else {
//				personViewBean = new PersonViewBean(person);
//			}
//			for (Organisation organisation : person.getWorksForOrganisations()) {
//				personViewBean.addOrganisationViewBean(organisationsController.getOrganisationViewBeanFromId(organisation.getId()));
//			}
//			personViewBeans.add(personViewBean);
//		}
	}

	public PickListBean getPickListBean() {
		return pickListBean;
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
				allOrganisationViewBeans.add(organisationViewBeanGenerator.getOrganisationViewBean(organisation.getId()));
			}
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
		logger.debug("setAdministrator({})", personViewBean.getAdministrator());
		personViewBean.getDomainBean().setAdministrator(personViewBean.getAdministrator());
		personViewBean.setDomainBean(usersService.updatePerson(personViewBean.getDomainBean()));
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
	
	/*-
	 * TODO : implement this method so that the worksForOrganisation relationships are not redundant with
	 * the associations of the various organisations. 
	 * Example : if the user works for UNIV, and UNIV is associated with COMMUNITY, then when we say
	 * the user works for UNIV, we should disable COMMUNITY (because he implicitly can access resources of
	 * COMMUNITY through the UNIV->COMMUNITY association). And so there is no need to state that he works
	 * for COMMUNITY too.
	 */
	public Boolean isDisabledInPickList(OrganisationViewBean organisationViewBean) {
		return false;
		/*
		if (organisationViewBean instanceof CommunityViewBean) {
			if (currentUserViewBean.getOrganisationViewBeans().contains(organisationViewBean)) {
				return false;
			}
			else {
				
			}
		}
		*/
	}
	
}
