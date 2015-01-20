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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("organisationsController")
@Scope("view")
public class OrganisationsController extends AbstractContextAwareController {

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrganisationsController.class);

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3854588801358138982L;

	@Inject
	private OrganisationsService organisationsService;

	private Map<Long, OrganisationViewBean> organisationViewBeans;

	private SortableTableBean<CommunityViewBean> communityViewBeans;
	private SortableTableBean<InstitutionViewBean> institutionViewBeans;
	private SortableTableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;
	private SortableTableBean<TeachingDepartmentViewBean> teachingDepartmentViewBeans;

	private OrganisationViewBean currentOrganisationViewBean;
	private List<CommunityViewBean> communityViewBeansForCurrentOrganisation;
	private List<InstitutionViewBean> institutionViewBeansForCurrentOrganisation;
	private List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeansForCurrentOrganisation;
	private List<TeachingDepartmentViewBean> teachingDepartmentViewBeansForCurrentOrganisation;

	private OrganisationViewBean memoryCommunityViewBean;
	private OrganisationViewBean memoryInstitutionViewBean;
	private OrganisationViewBean memoryAdministrativeDepartmentViewBean;
	private OrganisationViewBean memoryTeachingDepartmentViewBean;

	@Inject
	private PickListBean pickListBean;

	@Inject
	private FileUploadController fileUploadController;

	public OrganisationsController() {
		communityViewBeans = new SortableTableBean<CommunityViewBean>();
		institutionViewBeans = new SortableTableBean<InstitutionViewBean>();
		administrativeDepartmentViewBeans = new SortableTableBean<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new SortableTableBean<TeachingDepartmentViewBean>();

		communityViewBeansForCurrentOrganisation = new ArrayList<CommunityViewBean>();
		institutionViewBeansForCurrentOrganisation = new ArrayList<InstitutionViewBean>();
		administrativeDepartmentViewBeansForCurrentOrganisation = new ArrayList<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeansForCurrentOrganisation = new ArrayList<TeachingDepartmentViewBean>();

		organisationViewBeans = new HashMap<Long, OrganisationViewBean>();
	}

	@PostConstruct
	public void initOrganisationsController() {
		logger.debug("initOrganisationsController");
		try {
			Collection<Community> communities = organisationsService.retrieveAllCommunities();
			logger.debug("found {} communities", communities.size());
			communityViewBeans.getTableEntries().clear();
			for (Community community : communities) {
				logger.debug("community = {}", community.getName());
				CommunityViewBean communityViewBean = new CommunityViewBean(community);
				communityViewBeans.getTableEntries().add(communityViewBean);
				organisationViewBeans.put(communityViewBean.getId(), communityViewBean);
			}
			communityViewBeans.sort();

			Collection<Institution> institutions = organisationsService.retrieveAllInstitutions();
			logger.debug("found {} institutions", institutions.size());
			institutionViewBeans.getTableEntries().clear();
			for (Institution institution : institutions) {
				logger.debug("institution = {}", institution.getName());
				InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
				institutionViewBeans.getTableEntries().add(institutionViewBean);
				organisationViewBeans.put(institutionViewBean.getId(), institutionViewBean);
			}
			institutionViewBeans.sort();

			Collection<AdministrativeDepartment> administrativeDepartments = organisationsService
					.retrieveAllAdministrativeDepartments();
			logger.debug("found {} administrative departments", administrativeDepartments.size());
			administrativeDepartmentViewBeans.getTableEntries().clear();
			for (AdministrativeDepartment administrativeDepartment : administrativeDepartments) {
				logger.debug("administrative department = {}", administrativeDepartment.getName());
				AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
						administrativeDepartment);
				administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
				organisationViewBeans.put(administrativeDepartmentViewBean.getId(), administrativeDepartmentViewBean);
			}
			administrativeDepartmentViewBeans.sort();

			Collection<TeachingDepartment> teachingDepartments = organisationsService.retrieveAllTeachingDepartments();
			logger.debug("found {} teaching departments", teachingDepartments.size());
			teachingDepartmentViewBeans.getTableEntries().clear();
			for (TeachingDepartment teachingDepartment : teachingDepartments) {
				logger.debug("teaching department = {}", teachingDepartment.getName());
				TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(
						teachingDepartment);
				teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
				organisationViewBeans.put(teachingDepartmentViewBean.getId(), teachingDepartmentViewBean);
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

	public OrganisationViewBean getOrganisationViewBeanFromId(Long id) {
		return organisationViewBeans.get(id);
	}

	public Map<Long, OrganisationViewBean> getOrganisationViewBeans() {
		return organisationViewBeans;
	}

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
		this.currentOrganisationViewBean = currentOrganisationViewBean;
	}

	private void populateDataTablesForCurrentOrganisationViewBean() {
		if ((currentOrganisationViewBean instanceof CommunityViewBean)
				|| (currentOrganisationViewBean instanceof AdministrativeDepartmentViewBean)
				|| (currentOrganisationViewBean instanceof TeachingDepartmentViewBean)) {
			populateInstitutionViewBeansForCurrentOrganisation();
		}
		else if (currentOrganisationViewBean instanceof InstitutionViewBean) {
			populateCommunityViewBeansForCurrentOrganisation();
			populateAdministrativeDepartmentViewBeansForCurrentOrganisation();
			populateTeachingDepartmentViewBeansForCurrentOrganisation();
		}
	}

	public List<CommunityViewBean> getCommunityViewBeansForCurrentOrganisation() {
		return communityViewBeansForCurrentOrganisation;
	}

	// Note : can only be called for an institution (given the current domain
	// modelisation)
	private void populateCommunityViewBeansForCurrentOrganisation() {
		logger.debug("setCommunityViewBeansForCurrentOrganisation");
		if (getCurrentOrganisationViewBean() != null) {
			communityViewBeansForCurrentOrganisation.clear();
			for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getInstitution().getCommunities()
						.contains(communityViewBean.getDomainBean())) {
					communityViewBeansForCurrentOrganisation.add(communityViewBean);
				}
			}
		}
	}

	public List<InstitutionViewBean> getInstitutionViewBeansForCurrentOrganisation() {
		return institutionViewBeansForCurrentOrganisation;
	}

	private void populateInstitutionViewBeansForCurrentOrganisation() {
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

	private void populateAdministrativeDepartmentViewBeansForCurrentOrganisation() {
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

	private void populateTeachingDepartmentViewBeansForCurrentOrganisation() {
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
			for (Community communityAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
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
				for (Institution institution : ((CommunityViewBean) getCurrentOrganisationViewBean()).getCommunity()
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
				for (Institution institution : ((AdministrativeDepartmentViewBean) getCurrentOrganisationViewBean())
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
				for (Institution institution : ((TeachingDepartmentViewBean) getCurrentOrganisationViewBean())
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
			for (AdministrativeDepartment administrativeDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
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
			for (TeachingDepartment teachingDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
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

	public void onCreateCommunity(String name, String shortname, String iconFileName) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateCommunity", name);
		Community community = organisationsService.createCommunity(name, shortname, iconFileName);
		CommunityViewBean communityViewBean = new CommunityViewBean(community);
		communityViewBeans.getTableEntries().add(communityViewBean);
		communityViewBeans.sort();
		organisationViewBeans.put(communityViewBean.getId(), communityViewBean);
	}

	public void onCreateInstitution(String name, String shortname, String iconFileName) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateInstitution", name);
		Institution institution = organisationsService.createInstitution(name, shortname, iconFileName);
		InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
		institutionViewBeans.getTableEntries().add(institutionViewBean);
		institutionViewBeans.sort();
		organisationViewBeans.put(institutionViewBean.getId(), institutionViewBean);
	}

	public void onCreateTeachingDepartment(String name, String shortname, String iconFileName) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateTeachingDepartment", name);
		TeachingDepartment teachingDepartment = organisationsService.createTeachingDepartment(name, shortname,
				iconFileName);
		TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(teachingDepartment);
		teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
		teachingDepartmentViewBeans.sort();
		organisationViewBeans.put(teachingDepartmentViewBean.getId(), teachingDepartmentViewBean);
	}

	public void onCreateAdministrativeDepartment(String name, String shortname, String iconFileName) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateAdministrativeDepartment", name);
		AdministrativeDepartment administrativeDepartment = organisationsService.createAdministrativeDepartment(name,
				shortname, iconFileName);
		AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
				administrativeDepartment);
		administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
		administrativeDepartmentViewBeans.sort();
		organisationViewBeans.put(administrativeDepartmentViewBean.getId(), administrativeDepartmentViewBean);
	}

	public void onRenameOrganisation() {
		currentOrganisationViewBean.getDomainBean().setName(currentOrganisationViewBean.getName());
		currentOrganisationViewBean.getDomainBean().setShortname(currentOrganisationViewBean.getShortname());
		currentOrganisationViewBean.getDomainBean().setIconFileName(currentOrganisationViewBean.getIconFileName());
		currentOrganisationViewBean.setDomainBean(organisationsService.updateOrganisation(currentOrganisationViewBean
				.getDomainBean()));
		fileUploadController.reset();
		MessageDisplayer.showMessageToUserWithSeverityInfo(msgs.getMessage(
				"ADMINISTRATION.ORGANISATIONS.RENAME_ORGANISATION_MODAL_WINDOW.RENAME_SUCCESSFUL.TITLE", null,
				getCurrentUserLocale()), msgs.getMessage(
				"ADMINISTRATION.ORGANISATIONS.RENAME_ORGANISATION_MODAL_WINDOW.RENAME_SUCCESSFUL.DETAILS", null,
				getCurrentUserLocale()));
	}

	public void onDeleteOrganisation() {
		if (organisationsService.deleteOrganisation(currentOrganisationViewBean.getDomainBean().getId())) {
			if (currentOrganisationViewBean instanceof CommunityViewBean) {
				communityViewBeans.getTableEntries().remove(currentOrganisationViewBean);
			}
			else if (currentOrganisationViewBean instanceof InstitutionViewBean) {
				institutionViewBeans.getTableEntries().remove(currentOrganisationViewBean);
			}
			else if (currentOrganisationViewBean instanceof AdministrativeDepartmentViewBean) {
				administrativeDepartmentViewBeans.getTableEntries().remove(currentOrganisationViewBean);
			}
			else if (currentOrganisationViewBean instanceof TeachingDepartmentViewBean) {
				teachingDepartmentViewBeans.getTableEntries().remove(currentOrganisationViewBean);
			}
			organisationViewBeans.remove(currentOrganisationViewBean.getId());
			MessageDisplayer.showMessageToUserWithSeverityInfo(msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_SUCCESSFUL.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_SUCCESSFUL.DETAILS", null,
					getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.showMessageToUserWithSeverityError(msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_FAILURE.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_FAILURE.DETAILS", null,
					getCurrentUserLocale()));
		}
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveCommunity(
							movedOrganisationViewBean.getId(), false));
				}
				else if (movedOrganisationViewBean instanceof InstitutionViewBean) {
					// Based on the domain rules, currentOrganisationViewBean
					// can be an instance of CommunityViewBean,
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveInstitution(
							movedOrganisationViewBean.getId(), false));
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveAdministrativeDepartment(
							movedOrganisationViewBean.getId(), false));
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveTeachingDepartment(
							movedOrganisationViewBean.getId(), false));
				}
			}
			else {
				logger.debug("We should dissociate {} and {}", movedOrganisationViewBean.getName(),
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveCommunity(
							movedOrganisationViewBean.getId(), false));
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
						logger.debug("currentOrganisationViewBean instanceof {}",
								currentOrganisationViewBean.getClass());
					}
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveInstitution(
							movedOrganisationViewBean.getId(), false));
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveAdministrativeDepartment(
							movedOrganisationViewBean.getId(), false));
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
					movedOrganisationViewBean.setDomainBean(organisationsService.retrieveTeachingDepartment(
							movedOrganisationViewBean.getId(), false));
				}
			}
		}
		getCurrentOrganisationViewBean().setDomainBean(
				organisationsService.retrieveOrganisation(getCurrentOrganisationViewBean().getDomainBean().getId(),
						false));
	}

	public void onCommunitiesAccordionPanelTabChange(TabChangeEvent event) {
		memoryCommunityViewBean = (OrganisationViewBean) event.getData();
		setCurrentOrganisationViewBean((OrganisationViewBean) event.getData());
		populateDataTablesForCurrentOrganisationViewBean();
	}

	public void onInstitutionsAccordionPanelTabChange(TabChangeEvent event) {
		memoryInstitutionViewBean = (OrganisationViewBean) event.getData();
		setCurrentOrganisationViewBean((OrganisationViewBean) event.getData());
		populateDataTablesForCurrentOrganisationViewBean();
	}

	public void onAdministrativeDepartmentsAccordionPanelTabChange(TabChangeEvent event) {
		memoryAdministrativeDepartmentViewBean = (OrganisationViewBean) event.getData();
		setCurrentOrganisationViewBean((OrganisationViewBean) event.getData());
		populateDataTablesForCurrentOrganisationViewBean();
	}

	public void onTeachingDepartmentsAccordionPanelTabChange(TabChangeEvent event) {
		memoryTeachingDepartmentViewBean = (OrganisationViewBean) event.getData();
		setCurrentOrganisationViewBean((OrganisationViewBean) event.getData());
		populateDataTablesForCurrentOrganisationViewBean();
	}

	/**
	 * Called when we change the tab
	 * 
	 * @param event
	 */
	public void onOrganisationsTabViewTabChange(TabChangeEvent event) {
		/*
		 * There may be one opened organisation in the accordion of the tab
		 * which has just been selected, so here we refresh the datatables.
		 */
		switch ((String) event.getTab().getAttributes().get("id")) {
		case "tabCommunities":
			if (memoryCommunityViewBean != null) {
				setCurrentOrganisationViewBean(memoryCommunityViewBean);
				populateDataTablesForCurrentOrganisationViewBean();
			}
			break;
		case "tabInstitutions":
			if (memoryInstitutionViewBean != null) {
				setCurrentOrganisationViewBean(memoryInstitutionViewBean);
				populateDataTablesForCurrentOrganisationViewBean();
			}
			break;
		case "tabAdministrativeDepartments":
			if (memoryAdministrativeDepartmentViewBean != null) {
				setCurrentOrganisationViewBean(memoryAdministrativeDepartmentViewBean);
				populateDataTablesForCurrentOrganisationViewBean();
			}
			break;
		case "tabTeachingDepartments":
			if (memoryTeachingDepartmentViewBean != null) {
				setCurrentOrganisationViewBean(memoryTeachingDepartmentViewBean);
				populateDataTablesForCurrentOrganisationViewBean();
			}
			break;
		default:
			break;
		}
	}

	/*
	 * public void handleNewCommunityIconUpload(FileUploadEvent event) {
	 * UploadedFile file = event.getFile();
	 * MessageDisplayer.showMessageToUserWithSeverityInfo
	 * ("handleNewCommunityIconUpload", file.getFileName()); }
	 */

}
