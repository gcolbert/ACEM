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
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.TabViewBean;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-19
 * 
 */
@Controller("organisationsController")
@Scope("view")
public class OrganisationsController extends AbstractContextAwareController {

	private static final Logger logger = LoggerFactory.getLogger(OrganisationsController.class);

	private static final long serialVersionUID = 3854588801358138982L;

	@Autowired
	private OrganisationsService organisationsService;

	private SortableTableBean<CommunityViewBean> communityViewBeans;
	private SortableTableBean<InstitutionViewBean> institutionViewBeans;
	private SortableTableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;
	private SortableTableBean<TeachingDepartmentViewBean> teachingDepartmentViewBeans;

	private OrganisationViewBean currentOrganisationViewBean;
	private List<CommunityViewBean> communityViewBeansForCurrentOrganisation;
	private List<InstitutionViewBean> institutionViewBeansForCurrentOrganisation;
	private List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeansForCurrentOrganisation;
	private List<TeachingDepartmentViewBean> teachingDepartmentViewBeansForCurrentOrganisation;

	@Autowired
	private PickListBean pickListBean;

	@Autowired
	private TabViewBean tabViewBean;
	
	/*-
	 * TODO : enable those lines after correction of issue https://github.com/gcolbert/ACEM/issues/3
	 * 
	@Autowired
	private AccordionPanelBean communitiesAccordionPanelBean;

	@Autowired
	private AccordionPanelBean institutionsAccordionPanelBean;

	@Autowired
	private AccordionPanelBean administrativeDepartmentAccordionPanelBean;

	@Autowired
	private AccordionPanelBean teachingDepartmentAccordionPanelBean;
	*/
	
	public OrganisationsController() {
		// TODO : replace those hard-wired instanciations with @Autowired when
		// Spring 4 will be used
		communityViewBeans = new SortableTableBean<CommunityViewBean>();
		institutionViewBeans = new SortableTableBean<InstitutionViewBean>();
		administrativeDepartmentViewBeans = new SortableTableBean<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new SortableTableBean<TeachingDepartmentViewBean>();

		communityViewBeansForCurrentOrganisation = new ArrayList<CommunityViewBean>();
		institutionViewBeansForCurrentOrganisation = new ArrayList<InstitutionViewBean>();
		administrativeDepartmentViewBeansForCurrentOrganisation = new ArrayList<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeansForCurrentOrganisation = new ArrayList<TeachingDepartmentViewBean>();
	}

	@PostConstruct
	public void initOrganisationsController() {
		logger.debug("initOrganisationsController");
		try {
			Collection<Communaute> communities = organisationsService.retrieveAllCommunities();
			logger.debug("found {} communities", communities.size());
			communityViewBeans.getTableEntries().clear();
			for (Communaute community : communities) {
				logger.debug("community = {}", community.getName());
				communityViewBeans.getTableEntries().add(new CommunityViewBean(community));
			}
			communityViewBeans.sort();

			Collection<Etablissement> institutions = organisationsService.retrieveAllInstitutions();
			logger.debug("found {} institutions", institutions.size());
			institutionViewBeans.getTableEntries().clear();
			for (Etablissement institution : institutions) {
				logger.debug("institution = {}", institution.getName());
				institutionViewBeans.getTableEntries().add(new InstitutionViewBean(institution));
			}
			institutionViewBeans.sort();

			Collection<Service> administrativeDepartments = organisationsService.retrieveAllAdministrativeDepartments();
			logger.debug("found {} administrative departments", administrativeDepartments.size());
			administrativeDepartmentViewBeans.getTableEntries().clear();
			for (Service administrativeDepartment : administrativeDepartments) {
				logger.debug("administrative department = {}", administrativeDepartment.getName());
				administrativeDepartmentViewBeans.getTableEntries().add(
						new AdministrativeDepartmentViewBean(administrativeDepartment));
			}
			administrativeDepartmentViewBeans.sort();

			Collection<Composante> teachingDepartments = organisationsService.retrieveAllTeachingDepartments();
			logger.debug("found {} teaching departments", teachingDepartments.size());
			teachingDepartmentViewBeans.getTableEntries().clear();
			for (Composante teachingDepartment : teachingDepartments) {
				logger.debug("teaching department = {}", teachingDepartment.getName());
				teachingDepartmentViewBeans.getTableEntries().add(new TeachingDepartmentViewBean(teachingDepartment));
			}
			teachingDepartmentViewBeans.sort();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public TabViewBean getTabViewBean() {
		return tabViewBean;
	}

	/*
	 * TODO : Issue at https://github.com/gcolbert/ACEM/issues/3
	 * 
	public AccordionPanelBean getCommunitiesAccordionPanelBean() {
		return communitiesAccordionPanelBean;
	}

	public AccordionPanelBean getInstitutionsAccordionPanelBean() {
		return institutionsAccordionPanelBean;
	}

	public AccordionPanelBean getAdministrativeDepartmentAccordionPanelBean() {
		return administrativeDepartmentAccordionPanelBean;
	}
	
	public AccordionPanelBean getTeachingDepartmentAccordionPanelBean() {
		return teachingDepartmentAccordionPanelBean;
	}
	*/
	
	public TableBean<CommunityViewBean> getCommunityViewBeans() {
		return communityViewBeans;
	}

	public TableBean<InstitutionViewBean> getInstitutionViewBeans() {
		return institutionViewBeans;
	}

	public TableBean<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeans() {
		return administrativeDepartmentViewBeans;
	}

	public TableBean<TeachingDepartmentViewBean> getTeachingDepartmentViewBeans() {
		return teachingDepartmentViewBeans;
	}

	public OrganisationViewBean getCurrentOrganisationViewBean() {
		return currentOrganisationViewBean;
	}

	public void setCurrentOrganisationViewBean(OrganisationViewBean currentOrganisationViewBean) {
		logger.debug("setCurrentOrganisationViewBean({})", currentOrganisationViewBean.getName());
		this.currentOrganisationViewBean = currentOrganisationViewBean;
		if (currentOrganisationViewBean instanceof CommunityViewBean) {
			setInstitutionViewBeansForCurrentOrganisation();
		}
		else if (currentOrganisationViewBean instanceof InstitutionViewBean) {
			setCommunityViewBeansForCurrentOrganisation();
			setAdministrativeDepartmentViewBeansForCurrentOrganisation();
			setTeachingDepartmentViewBeansForCurrentOrganisation();
		}
		else if (currentOrganisationViewBean instanceof AdministrativeDepartmentViewBean) {
			setInstitutionViewBeansForCurrentOrganisation();
		}
		else if (currentOrganisationViewBean instanceof TeachingDepartmentViewBean) {
			setInstitutionViewBeansForCurrentOrganisation();
		}
	}

	public List<CommunityViewBean> getCommunityViewBeansForCurrentOrganisation() {
		return communityViewBeansForCurrentOrganisation;
	}

	// Note : can only be called for an institution (given the current domain
	// modelisation)
	private void setCommunityViewBeansForCurrentOrganisation() {
		logger.debug("setCommunityViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			communityViewBeansForCurrentOrganisation.clear();
			for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getInstitution().getCommunities()
						.contains(communityViewBean.getCommunity())) {
					communityViewBeansForCurrentOrganisation.add(communityViewBean);
				}
			}
		}
	}

	public List<InstitutionViewBean> getInstitutionViewBeansForCurrentOrganisation() {
		return institutionViewBeansForCurrentOrganisation;
	}

	private void setInstitutionViewBeansForCurrentOrganisation() {
		logger.debug("setInstitutionViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			institutionViewBeansForCurrentOrganisation.clear();
			for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
				if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
					if (((CommunityViewBean) getCurrentOrganisationViewBean()).getCommunity().getInstitutions()
							.contains(institutionViewBean.getInstitution())) {
						logger.debug("selectedCommunity is associated with {}", institutionViewBean.getInstitution()
								.getName());
						institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
					if (((AdministrativeDepartmentViewBean) getCurrentOrganisationViewBean())
							.getAdministrativeDepartment().getInstitutions()
							.contains(institutionViewBean.getInstitution())) {
						institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
					if (((TeachingDepartmentViewBean) getCurrentOrganisationViewBean()).getTeachingDepartment()
							.getInstitutions().contains(institutionViewBean.getInstitution())) {
						institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
					}
				}
			}
		}
	}

	public List<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeansForCurrentOrganisation() {
		return administrativeDepartmentViewBeansForCurrentOrganisation;
	}

	private void setAdministrativeDepartmentViewBeansForCurrentOrganisation() {
		logger.debug("setAdministrativeDepartmentViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			administrativeDepartmentViewBeansForCurrentOrganisation.clear();
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
					.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getInstitution()
						.getAdministrativeDepartments()
						.contains(administrativeDepartmentViewBean.getAdministrativeDepartment())) {
					administrativeDepartmentViewBeansForCurrentOrganisation.add(administrativeDepartmentViewBean);
				}
			}
		}
	}

	public List<TeachingDepartmentViewBean> getTeachingDepartmentViewBeansForCurrentOrganisation() {
		return teachingDepartmentViewBeansForCurrentOrganisation;
	}

	private void setTeachingDepartmentViewBeansForCurrentOrganisation() {
		logger.debug("setTeachingDepartmentViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			teachingDepartmentViewBeansForCurrentOrganisation.clear();
			for (TeachingDepartmentViewBean teachingDepartmentViewBean : teachingDepartmentViewBeans.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getInstitution().getTeachingDepartments()
						.contains(teachingDepartmentViewBean.getTeachingDepartment())) {
					teachingDepartmentViewBeansForCurrentOrganisation.add(teachingDepartmentViewBean);
				}
			}
		}
	}

	public void preparePicklistCommunityViewBeansForCurrentOrganisation() {
		logger.debug("preparePicklistCommunityViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(communityViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (Communaute communityAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getInstitution().getCommunities()) {
				for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
					if (communityAssociatedWithSelectedInstitution.getId().equals(communityViewBean.getId())) {
						pickListBean.getPickListEntities().getSource().remove(communityViewBean);
						pickListBean.getPickListEntities().getTarget().add(communityViewBean);
					}
				}
			}
		}
	}

	public void preparePicklistInstitutionViewBeansForCurrentOrganisation() {
		logger.debug("preparePicklistInstitutionViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(institutionViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
				for (Etablissement institution : ((CommunityViewBean) getCurrentOrganisationViewBean()).getCommunity()
						.getInstitutions()) {
					for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
						if (institution.getId().equals(institutionViewBean.getId())) {
							pickListBean.getPickListEntities().getSource().remove(institutionViewBean);
							pickListBean.getPickListEntities().getTarget().add(institutionViewBean);
						}
					}
				}
			}
			else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
				for (Etablissement institution : ((AdministrativeDepartmentViewBean) getCurrentOrganisationViewBean())
						.getAdministrativeDepartment().getInstitutions()) {
					for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
						if (institution.getId().equals(institutionViewBean.getId())) {
							pickListBean.getPickListEntities().getSource().remove(institutionViewBean);
							pickListBean.getPickListEntities().getTarget().add(institutionViewBean);
						}
					}
				}
			}
			else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
				for (Etablissement institution : ((TeachingDepartmentViewBean) getCurrentOrganisationViewBean())
						.getTeachingDepartment().getInstitutions()) {
					for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
						if (institution.getId().equals(institutionViewBean.getId())) {
							pickListBean.getPickListEntities().getSource().remove(institutionViewBean);
							pickListBean.getPickListEntities().getTarget().add(institutionViewBean);
						}
					}
				}
			}
		}
	}

	public void preparePicklistAdministrativeDepartmentViewBeansForCurrentOrganisation() {
		logger.debug("preparePicklistAdministrativeDepartmentViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(administrativeDepartmentViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (Service administrativeDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getInstitution().getAdministrativeDepartments()) {
				for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
						.getTableEntries()) {
					if (administrativeDepartmentAssociatedWithSelectedInstitution.getId().equals(
							administrativeDepartmentViewBean.getId())) {
						pickListBean.getPickListEntities().getSource().remove(administrativeDepartmentViewBean);
						pickListBean.getPickListEntities().getTarget().add(administrativeDepartmentViewBean);
					}
				}
			}
		}
	}

	public void preparePicklistTeachingDepartmentViewBeansForCurrentOrganisation() {
		logger.debug("preparePicklistTeachingDepartmentViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(teachingDepartmentViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (Composante teachingDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getInstitution().getTeachingDepartments()) {
				for (TeachingDepartmentViewBean teachingDepartmentViewBean : teachingDepartmentViewBeans
						.getTableEntries()) {
					if (teachingDepartmentAssociatedWithSelectedInstitution.getId().equals(
							teachingDepartmentViewBean.getId())) {
						pickListBean.getPickListEntities().getSource().remove(teachingDepartmentViewBean);
						pickListBean.getPickListEntities().getTarget().add(teachingDepartmentViewBean);
					}
				}
			}
		}
	}

	public void onCreateCommunity(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateCommunity", name);
		Communaute community = organisationsService.createCommunity(name, shortname);
		CommunityViewBean communityViewBean = new CommunityViewBean(community);
		communityViewBeans.getTableEntries().add(communityViewBean);
		communityViewBeans.sort();
	}

	public void onCreateInstitution(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateInstitution", name);
		Etablissement institution = organisationsService.createInstitution(name, shortname);
		InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
		institutionViewBeans.getTableEntries().add(institutionViewBean);
		institutionViewBeans.sort();
	}

	public void onCreateTeachingDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateTeachingDepartment", name);
		Composante teachingDepartment = organisationsService.createTeachingDepartment(name, shortname);
		TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(teachingDepartment);
		teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
		teachingDepartmentViewBeans.sort();
	}

	public void onCreateAdministrativeDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateAdministrativeDepartment", name);
		Service administrativeDepartment = organisationsService.createAdministrativeDepartment(name, shortname);
		AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
				administrativeDepartment);
		administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
		administrativeDepartmentViewBeans.sort();
	}

	public void onTransfer(TransferEvent event) {
		logger.debug("onTransfer");
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				logger.debug("We should associate {} and {}", movedOrganisationViewBean.getName(),
						getCurrentOrganisationViewBean().getName());
				if (movedOrganisationViewBean instanceof CommunityViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.associateCommunityAndInstitution(movedOrganisationViewBean.getDomainBean()
							.getId(), getCurrentOrganisationViewBean().getDomainBean().getId())) {
						communityViewBeansForCurrentOrganisation.add((CommunityViewBean) movedOrganisationViewBean);
						logger.debug("association successful");
					}
					else {
						logger.debug("association failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveCommunity(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof InstitutionViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// can be an
					// instance of CommunityViewBean,
					// AdministrativeDepartmentViewBean
					// or TeachingDepartmentViewBean.
					// So here, we have to test which class
					// currentOrganisationViewBean belongs to.
					if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
						if (organisationsService.associateCommunityAndInstitution(getCurrentOrganisationViewBean()
								.getDomainBean().getId(), movedOrganisationViewBean.getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation
									.add((InstitutionViewBean) movedOrganisationViewBean);
							logger.debug("association successful");
						}
						else {
							logger.debug("association failed");
						}
					}
					else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
						if (organisationsService.associateInstitutionAndAdministrativeDepartment(
								movedOrganisationViewBean.getDomainBean().getId(), getCurrentOrganisationViewBean()
										.getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation
									.add((InstitutionViewBean) movedOrganisationViewBean);
							logger.debug("association successful");
						}
						else {
							logger.debug("association failed");
						}
					}
					else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
						if (organisationsService.associateInstitutionAndTeachingDepartment(movedOrganisationViewBean
								.getDomainBean().getId(), getCurrentOrganisationViewBean().getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation
									.add((InstitutionViewBean) movedOrganisationViewBean);
							logger.debug("association successful");
						}
						else {
							logger.debug("association failed");
						}
					}
					else {
						logger.info("currentOrganisationViewBean instanceof {}", currentOrganisationViewBean.getClass());
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveInstitution(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof AdministrativeDepartmentViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.associateInstitutionAndAdministrativeDepartment(
							getCurrentOrganisationViewBean().getDomainBean().getId(), movedOrganisationViewBean
									.getDomainBean().getId())) {
						administrativeDepartmentViewBeansForCurrentOrganisation
								.add((AdministrativeDepartmentViewBean) movedOrganisationViewBean);
						logger.debug("association successful");
					}
					else {
						logger.debug("association failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveAdministrativeDepartment(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof TeachingDepartmentViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.associateInstitutionAndTeachingDepartment(getCurrentOrganisationViewBean()
							.getDomainBean().getId(), movedOrganisationViewBean.getDomainBean().getId())) {
						teachingDepartmentViewBeansForCurrentOrganisation
								.add((TeachingDepartmentViewBean) movedOrganisationViewBean);
						logger.debug("association successful");
					}
					else {
						logger.debug("association failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveTeachingDepartment(movedOrganisationViewBean.getId()));
				}
			}
			else {
				logger.info("We should dissociate {} and {}", movedOrganisationViewBean.getName(),
						getCurrentOrganisationViewBean().getName());
				if (movedOrganisationViewBean instanceof CommunityViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.dissociateCommunityAndInstitution(movedOrganisationViewBean
							.getDomainBean().getId(), getCurrentOrganisationViewBean().getDomainBean().getId())) {
						communityViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
						logger.debug("dissociation successful");
					}
					else {
						logger.debug("dissociation failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveCommunity(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof InstitutionViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// can be an instance of CommunityViewBean,
					// AdministrativeDepartmentViewBean or
					// TeachingDepartmentViewBean.
					// So here, we have to test which class
					// currentOrganisationViewBean belongs to.
					if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
						if (organisationsService.dissociateCommunityAndInstitution(getCurrentOrganisationViewBean()
								.getDomainBean().getId(), movedOrganisationViewBean.getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
							logger.debug("dissociation successful");
						}
						else {
							logger.debug("dissociation failed");
						}
					}
					else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
						if (organisationsService.dissociateInstitutionAndAdministrativeDepartment(
								movedOrganisationViewBean.getDomainBean().getId(), getCurrentOrganisationViewBean()
										.getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
							logger.debug("dissociation successful");
						}
						else {
							logger.debug("dissociation failed");
						}
					}
					else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
						if (organisationsService.dissociateInstitutionAndTeachingDepartment(movedOrganisationViewBean
								.getDomainBean().getId(), getCurrentOrganisationViewBean().getDomainBean().getId())) {
							institutionViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
							logger.debug("dissociation successful");
						}
						else {
							logger.debug("dissociation failed");
						}
					}
					else {
						logger.debug("currentOrganisationViewBean instanceof {}", currentOrganisationViewBean.getClass());
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveInstitution(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof AdministrativeDepartmentViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.dissociateInstitutionAndAdministrativeDepartment(
							getCurrentOrganisationViewBean().getDomainBean().getId(), movedOrganisationViewBean
									.getDomainBean().getId())) {
						administrativeDepartmentViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
						logger.debug("dissociation successful");
					}
					else {
						logger.debug("dissociation failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveAdministrativeDepartment(movedOrganisationViewBean.getId()));
				}
				else if (movedOrganisationViewBean instanceof TeachingDepartmentViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// must be an instance of InstitutionViewBean
					if (organisationsService.dissociateInstitutionAndTeachingDepartment(
							getCurrentOrganisationViewBean().getDomainBean().getId(), movedOrganisationViewBean
									.getDomainBean().getId())) {
						teachingDepartmentViewBeansForCurrentOrganisation.remove(movedOrganisationViewBean);
						logger.debug("dissociation successful");
					}
					else {
						logger.debug("dissociated failed");
					}
					movedOrganisationViewBean.setDomainBean(organisationsService
							.retrieveTeachingDepartment(movedOrganisationViewBean.getId()));
				}
			}
		}
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
	}

	public void onAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onAccordionPanelTabChange, tab={}", event.getTab());
		setCurrentOrganisationViewBean((OrganisationViewBean) event.getData());
	}
	
	/*-
	 * TODO : see issue at https://github.com/gcolbert/ACEM/issues/3
	 * Here, we should collapse the opened accordion panel when we switch tab on the outer tab.
	 */
	public void onTabViewTabChange(TabChangeEvent event) {
		logger.info("onTabViewTabChange, tab={}", event.getTab());
	}

	/*-
	public void handleNewCommunityIconUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		MessageDisplayer.showMessageToUserWithSeverityInfo("handleNewCommunityIconUpload", file.getFileName());
	}
	 */

}
