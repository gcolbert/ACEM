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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.omnifaces.util.Ajax;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialog;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialogInterface;
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
@Controller("adminOrganisationsController")
@Scope("view")
public class AdminOrganisationsController extends AbstractContextAwareController implements PageController,
		CommonUploadOneDialogInterface {

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdminOrganisationsController.class);

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3854588801358138982L;

	@Inject
	private OrganisationsService organisationsService;

	private SortableTableBean<CommunityViewBean> communityViewBeans;
	private SortableTableBean<InstitutionViewBean> institutionViewBeans;
	private SortableTableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;
	private SortableTableBean<TeachingDepartmentViewBean> teachingDepartmentViewBeans;

	private OrganisationViewBean objectEdited;

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

	/**
	 * Dialog for upload of one zip file Selection
	 */
	private CommonUploadOneDialog commonUploadOneDialog;

	/**
	 * Uploaded file
	 */
	private Path temporaryFilePath;

	public AdminOrganisationsController() {
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
		this.commonUploadOneDialog = new CommonUploadOneDialog(this);

		Collection<Community> communities = organisationsService.retrieveAllCommunities();
		communityViewBeans.getTableEntries().clear();
		for (Community community : communities) {
			CommunityViewBean communityViewBean = new CommunityViewBean(community);
			communityViewBeans.getTableEntries().add(communityViewBean);
		}
		communityViewBeans.sort();

		Collection<Institution> institutions = organisationsService.retrieveAllInstitutions();
		institutionViewBeans.getTableEntries().clear();
		for (Institution institution : institutions) {
			InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
			institutionViewBeans.getTableEntries().add(institutionViewBean);
		}
		institutionViewBeans.sort();

		Collection<AdministrativeDepartment> administrativeDepartments = organisationsService
				.retrieveAllAdministrativeDepartments();
		administrativeDepartmentViewBeans.getTableEntries().clear();
		for (AdministrativeDepartment administrativeDepartment : administrativeDepartments) {
			AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
					administrativeDepartment);
			administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
		}
		administrativeDepartmentViewBeans.sort();

		Collection<TeachingDepartment> teachingDepartments = organisationsService.retrieveAllTeachingDepartments();
		teachingDepartmentViewBeans.getTableEntries().clear();
		for (TeachingDepartment teachingDepartment : teachingDepartments) {
			TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(
					teachingDepartment);
			teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
		}
		teachingDepartmentViewBeans.sort();
	}

	@Override
	public String getPageTitle() {
		return msgs.getMessage("ADMINISTRATION.ORGANISATIONS.HEADER", null, getCurrentUserLocale());
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
		if (getCurrentOrganisationViewBean() != null) {
			communityViewBeansForCurrentOrganisation.clear();
			for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getDomainBean().getCommunities()
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
		if (getCurrentOrganisationViewBean() != null) {
			institutionViewBeansForCurrentOrganisation.clear();
			for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
				if ((getCurrentOrganisationViewBean() instanceof CommunityViewBean)
						&& (((CommunityViewBean) getCurrentOrganisationViewBean()).getDomainBean().getInstitutions()
								.contains(institutionViewBean.getDomainBean()))) {
					institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
				}
				else if ((getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean)
						&& (((AdministrativeDepartmentViewBean) getCurrentOrganisationViewBean()).getDomainBean()
								.getInstitutions().contains(institutionViewBean.getDomainBean()))) {
					institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
				}
				else if ((getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean)
						&& (((TeachingDepartmentViewBean) getCurrentOrganisationViewBean()).getDomainBean()
								.getInstitutions().contains(institutionViewBean.getDomainBean()))) {
					institutionViewBeansForCurrentOrganisation.add(institutionViewBean);
				}
			}
		}
	}

	public List<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeansForCurrentOrganisation() {
		return administrativeDepartmentViewBeansForCurrentOrganisation;
	}

	private void populateAdministrativeDepartmentViewBeansForCurrentOrganisation() {
		if (getCurrentOrganisationViewBean() != null) {
			administrativeDepartmentViewBeansForCurrentOrganisation.clear();
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
					.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getDomainBean()
						.getAdministrativeDepartments().contains(administrativeDepartmentViewBean.getDomainBean())) {
					administrativeDepartmentViewBeansForCurrentOrganisation.add(administrativeDepartmentViewBean);
				}
			}
		}
	}

	public List<TeachingDepartmentViewBean> getTeachingDepartmentViewBeansForCurrentOrganisation() {
		return teachingDepartmentViewBeansForCurrentOrganisation;
	}

	private void populateTeachingDepartmentViewBeansForCurrentOrganisation() {
		if (getCurrentOrganisationViewBean() != null) {
			teachingDepartmentViewBeansForCurrentOrganisation.clear();
			for (TeachingDepartmentViewBean teachingDepartmentViewBean : teachingDepartmentViewBeans.getTableEntries()) {
				if (((InstitutionViewBean) getCurrentOrganisationViewBean()).getDomainBean().getTeachingDepartments()
						.contains(teachingDepartmentViewBean.getDomainBean())) {
					teachingDepartmentViewBeansForCurrentOrganisation.add(teachingDepartmentViewBean);
				}
			}
		}
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
		 * which has just been selected, so here we refresh the datatables
		 * so that they contain the correct data for the lastly opened
		 * accordion tab (it's stored in memory*****ViewBean)
		 */
		switch ((String) event.getTab().getAttributes().get("id")) {
		case "tabCommunities":
			if (memoryCommunityViewBean != null) {
				setCurrentOrganisationViewBean(memoryCommunityViewBean);
			}
			break;
		case "tabInstitutions":
			if (memoryInstitutionViewBean != null) {
				setCurrentOrganisationViewBean(memoryInstitutionViewBean);
			}
			break;
		case "tabAdministrativeDepartments":
			if (memoryAdministrativeDepartmentViewBean != null) {
				setCurrentOrganisationViewBean(memoryAdministrativeDepartmentViewBean);
			}
			break;
		case "tabTeachingDepartments":
			if (memoryTeachingDepartmentViewBean != null) {
				setCurrentOrganisationViewBean(memoryTeachingDepartmentViewBean);
			}
			break;
		default:
			break;
		}
		populateDataTablesForCurrentOrganisationViewBean();
	}

	public OrganisationViewBean getObjectEdited() {
		return objectEdited;
	}

	public void onSaveOrganisation() {
		if (objectEdited.getDomainBean() == null) {
			onCreateOrganisation();
		}
		else {
			onModifyOrganisation();
		}
	}
	
	/*
	 * ************ CREATION ****************************
	 */

	private void prepareCreation() {
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void prepareCommunityCreation() {
		objectEdited = new CommunityViewBean();
		prepareCreation();
	}

	public void prepareInstitutionCreation() {
		objectEdited = new InstitutionViewBean();
		prepareCreation();
	}

	public void prepareAdministrativeDepartmentCreation() {
		objectEdited = new AdministrativeDepartmentViewBean();
		prepareCreation();
	}

	public void prepareTeachingDepartmentCreation() {
		objectEdited = new TeachingDepartmentViewBean();
		prepareCreation();
	}

	private void onCreateOrganisation() {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (!iconFileName.trim().isEmpty()) {
			moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
		}

		if (objectEdited instanceof CommunityViewBean) {
			Community community = organisationsService.createCommunity(objectEdited.getName(),
					objectEdited.getShortname(), objectEdited.getIconFileName());
			CommunityViewBean communityViewBean = new CommunityViewBean(community);
			communityViewBeans.getTableEntries().add(communityViewBean);
			communityViewBeans.sort();
		}
		else if (objectEdited instanceof InstitutionViewBean) {
			Institution institution = organisationsService.createInstitution(objectEdited.getName(),
					objectEdited.getShortname(), objectEdited.getIconFileName());
			InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
			institutionViewBeans.getTableEntries().add(institutionViewBean);
			institutionViewBeans.sort();
		}
		else if (objectEdited instanceof AdministrativeDepartmentViewBean) {
			AdministrativeDepartment administrativeDepartment = organisationsService.createAdministrativeDepartment(
					objectEdited.getName(), objectEdited.getShortname(), objectEdited.getIconFileName());
			AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
					administrativeDepartment);
			administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
			administrativeDepartmentViewBeans.sort();
		}
		else if (objectEdited instanceof TeachingDepartmentViewBean) {
			TeachingDepartment teachingDepartment = organisationsService.createTeachingDepartment(
					objectEdited.getName(), objectEdited.getShortname(), objectEdited.getIconFileName());
			TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(teachingDepartment);
			teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
			teachingDepartmentViewBeans.sort();
		}
	}

	/*
	 * ************ MODIFICATION ****************************
	 */

	private void prepareModification() {
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void prepareCommunityModification(CommunityViewBean communityViewBean) {
		objectEdited = new CommunityViewBean(organisationsService.retrieveCommunity(communityViewBean.getId(), true));
		prepareModification();
	}

	public void prepareInstitutionModification(InstitutionViewBean institutionViewBean) {
		objectEdited = new InstitutionViewBean(organisationsService.retrieveInstitution(institutionViewBean.getId(),
				true));
		prepareModification();
	}

	public void prepareAdministrativeDepartmentModification(
			AdministrativeDepartmentViewBean administrativeDepartmentViewBean) {
		objectEdited = new AdministrativeDepartmentViewBean(organisationsService.retrieveAdministrativeDepartment(
				administrativeDepartmentViewBean.getId(), true));
		prepareModification();
	}

	public void prepareTeachingDepartmentModification(TeachingDepartmentViewBean teachingDepartmentViewBean) {
		objectEdited = new TeachingDepartmentViewBean(organisationsService.retrieveTeachingDepartment(
				teachingDepartmentViewBean.getId(), true));
		prepareModification();
	}

	private void onModifyOrganisation() {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (!iconFileName.trim().isEmpty()) {
			moveUploadedIconToImagesFolder(this.temporaryFilePath, commonUploadOneDialog.getFileUploadedName());
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
			// We set the icon file name inside this block, because if the user
			// didn't upload any icon, we keep the current icon.
			objectEdited.setIconFileName(iconFileName);
		}

		// We transfer all data from objectEdited to currentOrganisationViewBean
		currentOrganisationViewBean.setName(objectEdited.getName());
		currentOrganisationViewBean.getDomainBean().setName(currentOrganisationViewBean.getName());

		currentOrganisationViewBean.setShortname(objectEdited.getShortname());
		currentOrganisationViewBean.getDomainBean().setShortname(currentOrganisationViewBean.getShortname());

		currentOrganisationViewBean.setIconFileName(objectEdited.getIconFileName());
		currentOrganisationViewBean.getDomainBean().setIconFileName(currentOrganisationViewBean.getIconFileName());

		// We persist the modified Organisation
		currentOrganisationViewBean.setDomainBean(organisationsService.updateOrganisation(currentOrganisationViewBean
				.getDomainBean()));

		MessageDisplayer.info(msgs.getMessage(
				"ADMINISTRATION.ORGANISATIONS.MODIFY_ORGANISATION_MODAL_WINDOW.MODIFICATION_SUCCESSFUL.TITLE", null,
				getCurrentUserLocale()), msgs.getMessage(
				"ADMINISTRATION.ORGANISATIONS.MODIFY_ORGANISATION_MODAL_WINDOW.MODIFICATION_SUCCESSFUL.DETAILS", null,
				getCurrentUserLocale()));
	}

	/*
	 * ************ DELETION ****************************
	 */

	public void onDeleteOrganisation(OrganisationViewBean organisationViewBean) {
		if (organisationsService.deleteOrganisation(organisationViewBean.getDomainBean().getId())) {
			if (organisationViewBean instanceof CommunityViewBean) {
				communityViewBeans.getTableEntries().remove(organisationViewBean);
			}
			else if (organisationViewBean instanceof InstitutionViewBean) {
				institutionViewBeans.getTableEntries().remove(organisationViewBean);
			}
			else if (organisationViewBean instanceof AdministrativeDepartmentViewBean) {
				administrativeDepartmentViewBeans.getTableEntries().remove(organisationViewBean);
			}
			else if (organisationViewBean instanceof TeachingDepartmentViewBean) {
				teachingDepartmentViewBeans.getTableEntries().remove(organisationViewBean);
			}
			MessageDisplayer.info(msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_SUCCESSFUL.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_SUCCESSFUL.DETAILS", null,
					getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.error(msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_FAILURE.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"ADMINISTRATION.ORGANISATIONS.DELETE_ORGANISATION_MODAL_WINDOW.DELETION_FAILURE.DETAILS", null,
					getCurrentUserLocale()), logger);
		}
	}

	/*
	 * ************ PICKLIST OF ORGANISATIONS ****************************
	 */

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public void preparePicklistCommunityViewBeansForCurrentOrganisation() {
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(communityViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (Community communityAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getDomainBean().getCommunities()) {
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
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(institutionViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
				for (Institution institution : ((CommunityViewBean) getCurrentOrganisationViewBean()).getDomainBean()
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
						.getDomainBean().getInstitutions()) {
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
						.getDomainBean().getInstitutions()) {
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
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(administrativeDepartmentViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (AdministrativeDepartment administrativeDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getDomainBean().getAdministrativeDepartments()) {
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
		if (getCurrentOrganisationViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(teachingDepartmentViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			for (TeachingDepartment teachingDepartmentAssociatedWithSelectedInstitution : ((InstitutionViewBean) getCurrentOrganisationViewBean())
					.getDomainBean().getTeachingDepartments()) {
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

	public void onTransfer(TransferEvent event) {
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
						logger.debug("dissociation failed");
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

	/*
	 * ***************** IMAGE UPLOAD ************************************
	 */
	/**
	 * Get the Bean to manage dialog
	 * 
	 * @return the bean
	 */
	@Override
	public CommonUploadOneDialog getCommonUploadOneDialog() {
		return commonUploadOneDialog;
	}

	/**
	 * @see eu.ueb.acem.web.utils.include.CommonUploadOneDialogInterface#setSelectedFromCommonUploadOneDialog(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setSelectedFromCommonUploadOneDialog(Path temporaryFilePath, String originalFileName) {
		deleteTemporaryFileIfExists(this.temporaryFilePath);

		// Memorize new one
		this.temporaryFilePath = temporaryFilePath;

		// If null there was an error on file copy
		if (temporaryFilePath == null) {
			MessageDisplayer.error(msgs.getMessage("SERVICE_FILEUTIL_FILE_NOT_CREATED", null, getCurrentUserLocale()),
					"", logger);
		}

		Ajax.update("modifyOrganisationForm:iconOutputPanel", "createOrganisationForm:iconOutputPanel");
	}

	public Path getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void removeObjectEditedIcon() {
		objectEdited.setIconFileName("");
		resetTemporaryFilePath();
	}

	private void resetTemporaryFilePath() {
		deleteTemporaryFileIfExists(this.temporaryFilePath);
		this.temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	/**
	 * Called when the user closes the dialog containing an image uploader.
	 * 
	 * @param event
	 */
	public void onCloseDialogWithUploadedFile(CloseEvent event) {
		// Remove previously uploaded file if it exists
		deleteTemporaryFileIfExists(this.temporaryFilePath);
	}

}
