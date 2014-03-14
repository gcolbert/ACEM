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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

@Controller("usersController")
@Scope("view")
public class UsersController extends AbstractContextAwareController {

	private static final long serialVersionUID = -977386846045010683L;

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private List<PersonViewBean> personViewBeans;

	private PersonViewBean selectedUserViewBean;

	@Autowired
	private PickListBean pickListBean;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	public OrganisationsController organisationsController;
	
	@Autowired
	@Qualifier(value = "tableBean")
	public TableBean<OrganisationViewBean> organisationViewBeans;

	public UsersController() {
		personViewBeans = new ArrayList<PersonViewBean>();
	}

	@PostConstruct
	public void initUsersController() {
		logger.debug("initUsersController");

		List<OrganisationViewBean> organisationViewBeansList = new ArrayList<OrganisationViewBean>();
		organisationViewBeansList.addAll(organisationsController.getCommunityViewBeans().getTableEntries());
		organisationViewBeansList.addAll(organisationsController.getInstitutionViewBeans().getTableEntries());
		organisationViewBeansList.addAll(organisationsController.getAdministrativeDepartmentViewBeans().getTableEntries());
		organisationViewBeansList.addAll(organisationsController.getTeachingDepartmentViewBeans().getTableEntries());
		organisationViewBeans.setTableEntries(organisationViewBeansList);

		personViewBeans.clear();
		Set<Personne> persons = usersService.getPersons();
		for (Personne person : persons) {
			PersonViewBean personViewBean = new PersonViewBean(person);
			personViewBeans.add(personViewBean);
			for (Organisation organisation : person.getWorksForOrganisations()) {
				personViewBean.addOrganisationViewBean(organisationsController.getOrganisationViewBeanFromId(organisation.getId()));
			}
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

	public PersonViewBean getSelectedUserViewBean() {
		return selectedUserViewBean;
	}

	public void setSelectedUserViewBean(PersonViewBean selectedUserViewBean) {
		this.selectedUserViewBean = selectedUserViewBean;
	}

	public void preparePicklistOrganisationViewBeans() {
		if (getSelectedUserViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(organisationViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (OrganisationViewBean organisationViewBean : organisationViewBeans.getTableEntries()) {
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
		logger.info("onTransfer");
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		try {
			for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
				if (event.isAdd()) {
					logger.info("We should associate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					if (usersService.associateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId())) {
						selectedUserViewBean.getDomainBean().addWorksForOrganisations(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.addOrganisationViewBean(movedOrganisationViewBean);
						logger.info("association successful");
					}
					else {
						logger.info("association failed");
					}
				}
				else {
					logger.debug("We should dissociate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					String typeOfOrganisation = "";
					// TODO : remove that "typeOfOrganisation" shit (see https://groups.google.com/d/msg/neo4j/GZlft7vw8n0/8Rb70lQ6jM8J)
					if (movedOrganisationViewBean.getDomainBean() instanceof Communaute) {
						typeOfOrganisation = "Community";
					}
					else if (movedOrganisationViewBean.getDomainBean() instanceof Etablissement) {
						typeOfOrganisation = "Institution";
					}
					else if (movedOrganisationViewBean.getDomainBean() instanceof Service) {
						typeOfOrganisation = "AdministrativeDepartment";
					}
					else if (movedOrganisationViewBean.getDomainBean() instanceof Composante) {
						typeOfOrganisation = "TeachingDepartment";
					}
					if (usersService.dissociateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId(), typeOfOrganisation)) {
						selectedUserViewBean.getDomainBean().removeWorksForOrganisations(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.removeOrganisationViewBean(movedOrganisationViewBean);
						logger.info("dissociation successful");
					}
					else {
						logger.info("dissociation failed");
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
