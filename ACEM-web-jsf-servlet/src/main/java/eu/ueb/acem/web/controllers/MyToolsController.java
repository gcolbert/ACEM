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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;

import org.omnifaces.util.Ajax;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.services.ApplicationService;
import eu.ueb.acem.services.ImagesService;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.utils.OrganisationViewBeanGenerator;
import eu.ueb.acem.web.utils.PedagogicalAdviceTreeGenerator;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialog;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialogInterface;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.jaune.DocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.PedagogicalAndDocumentaryResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
import eu.ueb.acem.web.viewbeans.jaune.UseModeViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("myToolsController")
@Scope("view")
public class MyToolsController extends AbstractContextAwareController implements PageController, CommonUploadOneDialogInterface {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5663154564837226988L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyToolsController.class);

	/* ***********************************************************************/

	@Inject
	private UsersService usersService;

	/* ***********************************************************************/

	@Inject
	private OrganisationsService organisationsService;

	/**
	 * List of all organisations (needed for the admin to associate a resource with an organisation)
	 */
	private List<SelectItem> allOrganisationViewBeans;

	/* ***********************************************************************/

	@Inject
	private PedagogicalAdviceTreeGenerator pedagogicalAdviceTreeGenerator;
	private EditableTreeBean pedagogicalUsesTreeBean;

	@Inject
	private EditableTreeBean categoriesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private TreeNode categoriesTreeSelectedNode;
	private String selectedResourceTypeForCategoriesTree;

	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private List<ToolCategoryViewBean> allToolCategoryViewBeans;
	private ToolCategoryViewBean objectEditedToolCategory;

	/* ***********************************************************************/

	@Inject
	private ResourcesService resourcesService;
	private ResourceViewBean selectedResourceViewBean;
	
	private ResourceViewBean objectEditedResource;

	/**
	 * Picklist for choosing organisations having access to a resource
	 */
	@Inject
	private PickListBean pickListBean;

	/* ***********************************************************************/

	/**
	 * Dialog for uploading an icon
	 */
	private CommonUploadOneDialog commonUploadOneDialog;

	/**
	 * Uploaded file
	 */
	private Path temporaryFilePath;

	@Inject
	private ImagesService imagesService;

	@Inject
	private ApplicationService applicationService;

	/* ***********************************************************************/

	public MyToolsController() {
		logger.debug("Constructor");
		allOrganisationViewBeans = new ArrayList<SelectItem>();
		allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
	}

	@PostConstruct
	public void init() {
		logger.debug("Entering init()");
		this.commonUploadOneDialog = new CommonUploadOneDialog(this);
		loadAllOrganisationViewBeans();
		loadAllToolCategoryViewBeans();
		logger.debug("Leaving init()");
	}

	@Override
	public String getPageTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(msgs.getMessage("MENU.MY_TOOLS",null,getCurrentUserLocale()));
		if (getSelectedToolCategoryViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedToolCategoryViewBean().getName());
		}
		return sb.toString();
	}

	public List<SelectItem> getAllOrganisationViewBeans() {
		return allOrganisationViewBeans;
	}

	public void loadAllOrganisationViewBeans() {
		SelectItemGroup groupCommunities = new SelectItemGroup(msgs.getMessage("ORGANISATIONS_GROUP.COMMUNITIES",null,getCurrentUserLocale()));
		List<Community> communities = new ArrayList<Community>(organisationsService.retrieveAllCommunities());
		Collections.sort(communities);
		SelectItem[] arrayForCommunities = new SelectItem[communities.size()];
		int i = 0;
		for (Community community : communities) {
			arrayForCommunities[i] = new SelectItem(OrganisationViewBeanGenerator.getViewBean(community),community.getName());
			i++;
		}
		groupCommunities.setSelectItems(arrayForCommunities);
		allOrganisationViewBeans.add(groupCommunities);

		SelectItemGroup groupInstitutions = new SelectItemGroup(msgs.getMessage("ORGANISATIONS_GROUP.INSTITUTIONS",null,getCurrentUserLocale()));
		List<Institution> institutions = new ArrayList<Institution>(organisationsService.retrieveAllInstitutions());
		Collections.sort(institutions);
		SelectItem[] arrayForInstitutions = new SelectItem[institutions.size()];
		i = 0;
		for (Institution institution : institutions) {
			arrayForInstitutions[i] = new SelectItem(OrganisationViewBeanGenerator.getViewBean(institution),institution.getName());
			i++;
		}
		groupInstitutions.setSelectItems(arrayForInstitutions);
		allOrganisationViewBeans.add(groupInstitutions);

		SelectItemGroup groupAdministrativeDepartments = new SelectItemGroup(msgs.getMessage("ORGANISATIONS_GROUP.ADMINISTRATIVE_DEPARTMENTS",null,getCurrentUserLocale()));
		List<AdministrativeDepartment> administrativeDepartments = new ArrayList<AdministrativeDepartment>(organisationsService.retrieveAllAdministrativeDepartments());
		Collections.sort(administrativeDepartments);
		SelectItem[] arrayForAdministrativeDepartments = new SelectItem[administrativeDepartments.size()];
		i = 0;
		for (AdministrativeDepartment administrativeDepartment : administrativeDepartments) {
			arrayForAdministrativeDepartments[i] = new SelectItem(OrganisationViewBeanGenerator.getViewBean(administrativeDepartment),administrativeDepartment.getName());
			i++;
		}
		groupAdministrativeDepartments.setSelectItems(arrayForAdministrativeDepartments);
		allOrganisationViewBeans.add(groupAdministrativeDepartments);

		SelectItemGroup groupTeachingDepartments = new SelectItemGroup(msgs.getMessage("ORGANISATIONS_GROUP.TEACHING_DEPARTMENTS",null,getCurrentUserLocale()));
		List<TeachingDepartment> teachingDepartments = new ArrayList<TeachingDepartment>(organisationsService.retrieveAllTeachingDepartments());
		Collections.sort(teachingDepartments);
		SelectItem[] arrayForTeachingDepartments = new SelectItem[teachingDepartments.size()];
		i = 0;
		for (TeachingDepartment teachingDepartment : teachingDepartments) {
			arrayForTeachingDepartments[i] = new SelectItem(OrganisationViewBeanGenerator.getViewBean(teachingDepartment),teachingDepartment.getName());
			i++;
		}
		groupTeachingDepartments.setSelectItems(arrayForTeachingDepartments);
		allOrganisationViewBeans.add(groupTeachingDepartments);
	}

	/*
	 * ****************** Tool categories ********************
	 */
	public String getSelectedResourceTypeForCategoriesTree() {
		return selectedResourceTypeForCategoriesTree;
	}

	public void setSelectedResourceTypeForCategoriesTree(String resourceType) {
		if (resourcesService.getResourceType_RESOURCE_TYPE_EQUIPMENT().equals(resourceType)) {
			selectedResourceTypeForCategoriesTree = resourceType;
			prepareToolCategoryTreeForResourceType(new EquipmentViewBean());
		}
		else if (resourcesService.getResourceType_RESOURCE_TYPE_SOFTWARE().equals(resourceType)) {
			selectedResourceTypeForCategoriesTree = resourceType;
			prepareToolCategoryTreeForResourceType(new SoftwareViewBean());
		}
		else if (resourcesService.getResourceType_RESOURCE_TYPE_DOCUMENTATION().equals(resourceType)) {
			selectedResourceTypeForCategoriesTree = resourceType;
			prepareToolCategoryTreeForResourceType(new DocumentationViewBean());
		}
		else if (resourcesService.getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE().equals(resourceType)) {
			selectedResourceTypeForCategoriesTree = resourceType;
			prepareToolCategoryTreeForResourceType(new PedagogicalAndDocumentaryResourceViewBean());
		}
		else if (resourcesService.getResourceType_RESOURCE_TYPE_PROFESSIONAL_TRAINING().equals(resourceType)) {
			selectedResourceTypeForCategoriesTree = resourceType;
			prepareToolCategoryTreeForResourceType(new ProfessionalTrainingViewBean());
		}
		else {
			MessageDisplayer.warn("Wrong resource type","Unknown resource type '"+resourceType+"'");
		}
	}

	public void prepareToolCategoryTreeForResourceType(ResourceViewBean resourceViewBean) {
		logger.debug("Entering prepareToolCategoryTreeForResourceType for type={}", resourceViewBean.getType());
		categoriesTreeBean.clear();
		setSelectedToolCategoryId(null);
		// When the category changes, we reset the selected category
		setSelectedToolCategoryViewBean(null);

		List<ResourceCategory> resourceCategoriesForCurrentType = new ArrayList<ResourceCategory>();
		// If the user is an administrator, he sees all the resources
		if (getCurrentUserViewBean().getAdministrator()) {
			resourceCategoriesForCurrentType.addAll(resourcesService.retrieveCategoriesForResourceType(resourceViewBean
					.getType()));
		}
		else {
			// Otherwise he only sees a subset of the resources determined
			// by the organisation he works for and the associations between
			// this organisation and the other organisations.
			resourceCategoriesForCurrentType.addAll(resourcesService.retrieveCategoriesForResourceTypeAndPerson(
					resourceViewBean.getType(), getCurrentUserViewBean().getDomainBean()));
		}
		Collections.sort(resourceCategoriesForCurrentType);

		// For future reference, we keep the code that allows to repeat the
		// selected category name as a visible root for the tree, allowing to
		// add nodes by right-clicking on the visible root.
		boolean repeatSelectedCategoryAsVisibleRootNode = false;
		if (repeatSelectedCategoryAsVisibleRootNode) {
			categoriesTreeBean.addVisibleRoot(msgs.getMessage(resourceViewBean.getTypePluralFormMessageKey(), null,
					getCurrentUserLocale()));
			for (ResourceCategory resourceCategory : resourceCategoriesForCurrentType) {
				categoriesTreeBean.addChild(getTreeNodeType_CATEGORY(), categoriesTreeBean.getVisibleRoots().get(0),
						resourceCategory.getId(), resourceCategory.getName(), "category");
			}
			categoriesTreeBean.getVisibleRoots().get(0).setExpanded(true);
		}
		else {
			for (ResourceCategory resourceCategory : resourceCategoriesForCurrentType) {
				categoriesTreeBean.addChild(getTreeNodeType_CATEGORY(), categoriesTreeBean.getRoot(),
						resourceCategory.getId(), resourceCategory.getName(), "category");
			}
			categoriesTreeBean.getRoot().setExpanded(true);
		}
		logger.debug("Leaving prepareToolCategoryTreeForResourceType");
	}

	public Long getSelectedToolCategoryId() {
		return selectedToolCategoryId;
	}

	public void setSelectedToolCategoryId(Long toolCategoryId) {
		logger.debug("Entering setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(toolCategoryId, true);
		if (toolCategory != null) {
			setSelectedToolCategoryViewBean(new ToolCategoryViewBean(toolCategory));
		}
		else {
			selectedToolCategoryId = null;
			selectedToolCategoryViewBean = null;
		}
		logger.debug("Leaving setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
	}

	public ToolCategoryViewBean getSelectedToolCategoryViewBean() {
		return selectedToolCategoryViewBean;
	}

	public void setSelectedToolCategoryViewBean(ToolCategoryViewBean toolCategoryViewBean) {
		logger.debug("Entering setSelectedToolCategoryViewBean");
		selectedToolCategoryViewBean = toolCategoryViewBean;

		// When the category changes, we have to reset the currently selected resource
		// in the datatable of this category
		setSelectedResourceViewBean(null);

		if (selectedToolCategoryViewBean != null) {
			// We initialize the checkbox "category is a favorite category for the user"
			selectedToolCategoryViewBean.setFavoriteToolCategory(getCurrentUserViewBean()
					.getFavoriteToolCategoryViewBeans().contains(selectedToolCategoryViewBean));

			// We associate the ResourceViewBeans
			selectedToolCategoryViewBean.getResourceViewBeans().clear();
			Collection<Resource> resourcesTheUserCanUse = new HashSet<Resource>(0);
			// If the user is an administrator, he sees all resources
			if (getCurrentUserViewBean().getAdministrator()) {
				resourcesTheUserCanUse.addAll(selectedToolCategoryViewBean.getDomainBean().getResources());
			}
			else {
				// Otherwise he only sees a subset of the resources determined
				// by the organisation he works for and the associations between
				// this organisation and the other organisations.
				resourcesTheUserCanUse.addAll(resourcesService.getResourcesInCategoryForPerson(selectedToolCategoryViewBean.getDomainBean(), getCurrentUserViewBean().getDomainBean()));
			}
			for (Resource resource : resourcesTheUserCanUse) {
				resource = resourcesService.retrieveResource(resource.getId(), true);
				ResourceViewBean resourceViewBean = createResourceViewBean(resource);

				// The organisation possessing the resource
				Organisation possessingOrganisation = organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId(), false);
				resourceViewBean.setOrganisationPossessingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(possessingOrganisation));

				// The organisation supporting the resource
				if (resourceViewBean.getDomainBean().getOrganisationSupportingResource() != null) {
					Organisation supportService = organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId(), false);
					resourceViewBean.setOrganisationSupportingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(supportService));
				}

				// The organisations having access to the resource
				// (NOTE : we don't repeat here the organisation possessing the resource)
				for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
					resourceViewBean.getOrganisationViewingResourceViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisation));
				}

				// The use modes of the resource
				for (UseMode useMode : resourceViewBean.getDomainBean().getUseModes()) {
					resourceViewBean.getUseModeViewBeans().add(new UseModeViewBean(useMode));
				}

				selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
			}

			// We initialize the "pedagogical advice" tree
			setPedagogicalUsesTreeRoot(selectedToolCategoryViewBean.getDomainBean());

			// We associate the PedagogicalScenarioViewBeans
			Collection<PedagogicalScenario> pedagogicalScenarios = resourcesService.retrieveScenariosAssociatedWithResourceCategory(selectedToolCategoryViewBean.getDomainBean());
			for (PedagogicalScenario pedagogicalScenario : pedagogicalScenarios) {
				PedagogicalScenarioViewBean pedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(pedagogicalScenario);
				if (! selectedToolCategoryViewBean.getScenarioViewBeans().contains(pedagogicalScenarioViewBean)) {
					selectedToolCategoryViewBean.getScenarioViewBeans().add(pedagogicalScenarioViewBean);
				}
			}
		}
		logger.debug("Leaving setSelectedToolCategoryViewBean");
	}

	public TreeNode getCategoriesTreeRoot() {
		return categoriesTreeBean.getRoot();
	}

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public TreeNode getSelectedNode() {
		return categoriesTreeSelectedNode;
	}

	public Long getSelectedNodeId() {
		return ((EditableTreeBean.TreeNodeData)categoriesTreeSelectedNode.getData()).getId();
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (categoriesTreeSelectedNode != null) {
			categoriesTreeSelectedNode.setSelected(false);
		}
		categoriesTreeSelectedNode = selectedNode;

		categoriesTreeBean.expandOnlyOneNode(categoriesTreeSelectedNode);
	}

	public void prepareToolCategoryCreation() {
		objectEditedToolCategory = new ToolCategoryViewBean();
		selectedToolCategoryViewBean = null;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void prepareToolCategoryModification(ToolCategoryViewBean toolCategoryViewBean) {
		objectEditedToolCategory = new ToolCategoryViewBean(resourcesService.retrieveResourceCategory(toolCategoryViewBean.getId(), true));
		selectedToolCategoryViewBean = toolCategoryViewBean;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public ToolCategoryViewBean getObjectEditedToolCategory() {
		return objectEditedToolCategory;
	}

	public List<ToolCategoryViewBean> getAllToolCategoryViewBeans() {
		return allToolCategoryViewBeans;
	}

	public void loadAllToolCategoryViewBeans() {
		for (ResourceCategory resourceCategory : resourcesService.retrieveAllCategories()) {
			allToolCategoryViewBeans.add(new ToolCategoryViewBean(resourceCategory));
		}
		Collections.sort(allToolCategoryViewBeans);
	}

	public void onSaveToolCategory() {
		if (objectEditedToolCategory.getId() == null) {
			createToolCategoryFromObjectEdited();
		}
		else {
			modifyToolCategoryFromObjectEdited();
		}
	}

	private void createToolCategoryFromObjectEdited() {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (!iconFileName.trim().isEmpty()) {
			commonUploadOneDialog.moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
		}
		objectEditedToolCategory.setIconFileName(iconFileName);

		ResourceCategory toolCategory = resourcesService.createResourceCategory(objectEditedToolCategory.getName(),
				objectEditedToolCategory.getDescription(), iconFileName);
		if (toolCategory != null) {
			ToolCategoryViewBean newToolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
			allToolCategoryViewBeans.add(newToolCategoryViewBean);
			Collections.sort(allToolCategoryViewBeans);
			MessageDisplayer.info(
					msgs.getMessage("TOOL_CATEGORIES.CREATION_SUCCESSFUL.TITLE", null, getCurrentUserLocale()),
					msgs.getMessage("TOOL_CATEGORIES.CREATION_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.error(
					msgs.getMessage("TOOL_CATEGORIES.CREATION_FAILURE.TITLE", null, getCurrentUserLocale()),
					msgs.getMessage("TOOL_CATEGORIES.CREATION_FAILURE.DETAILS", null, getCurrentUserLocale()), logger);
		}
	}

	private void modifyToolCategoryFromObjectEdited() {
		if (objectEditedToolCategory != null) {
			String iconFileName = commonUploadOneDialog.getFileUploadedName();
			if (!iconFileName.trim().isEmpty()) {
				commonUploadOneDialog.moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
				this.temporaryFilePath = null;
				commonUploadOneDialog.reset();
				// We set the icon file name inside this block, because if the user
				// didn't upload any icon, we keep the current icon.
				objectEditedToolCategory.setIconFileName(iconFileName);
			}

			objectEditedToolCategory.getDomainBean().setName(objectEditedToolCategory.getName());
			objectEditedToolCategory.getDomainBean().setDescription(objectEditedToolCategory.getDescription());
			objectEditedToolCategory.getDomainBean().setIconFileName(objectEditedToolCategory.getIconFileName());
			objectEditedToolCategory.setDomainBean(resourcesService.updateResourceCategory(objectEditedToolCategory.getDomainBean()));

			// We have to update the ToolCategoryViewBean inside allToolCategoryViewBeans (which is used in the east panel)
			int index = allToolCategoryViewBeans.indexOf(objectEditedToolCategory);
			allToolCategoryViewBeans.set(index, objectEditedToolCategory);
			setSelectedToolCategoryViewBean(objectEditedToolCategory);

			// We have to update selectedToolCategoryViewBean if it is equal to objectEditedToolCategory
			if (selectedToolCategoryViewBean.equals(objectEditedToolCategory)) {
				selectedToolCategoryViewBean = objectEditedToolCategory;
			}

			MessageDisplayer.info(
					msgs.getMessage("TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.TITLE", null, getCurrentUserLocale()),
					msgs.getMessage("TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
		}
	}

	public void onDeleteToolCategory(ToolCategoryViewBean toolCategoryViewBean) {
		if (toolCategoryViewBean != null) {
			if (resourcesService.deleteResourceCategory(toolCategoryViewBean.getDomainBean().getId())) {
				allToolCategoryViewBeans.remove(toolCategoryViewBean);
				MessageDisplayer.info(
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.info(
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()));
			}
		}
	}

	public void toggleFavoriteToolCategoryForCurrentUser(ToolCategoryViewBean toolCategoryViewBean) {
		PersonViewBean currentUserViewBean = getCurrentUserViewBean();
		if (currentUserViewBean.getFavoriteToolCategoryViewBeans().contains(toolCategoryViewBean)) {
			if (usersService.removeFavoriteToolCategoryForPerson(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
				currentUserViewBean.getFavoriteToolCategoryViewBeans().remove(toolCategoryViewBean);
			}
		}
		else {
			if (usersService.addFavoriteToolCategoryForPerson(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
				currentUserViewBean.getFavoriteToolCategoryViewBeans().add(toolCategoryViewBean);
			}
		}
	}

	/*
	 * ****************** Resources ********************
	 */
	/**
	 * Helper method that will create a correctly typed ResourceViewBean given a Resource
	 * @param resource The Resource to use to construct the ResourceViewBean
	 * @return the corresponding ResourceViewBean, or null if given resource is null
	 */
	public ResourceViewBean createResourceViewBean(Resource resource) {
		ResourceViewBean viewBean = null;
		if (resource != null) {
			if (resource instanceof Software) {
				viewBean = new SoftwareViewBean((Software) resource);
			}
			else if (resource instanceof PedagogicalAndDocumentaryResource) {
				viewBean = new PedagogicalAndDocumentaryResourceViewBean((PedagogicalAndDocumentaryResource) resource);
			}
			else if (resource instanceof Equipment) {
				viewBean = new EquipmentViewBean((Equipment) resource);
			}
			else if (resource instanceof Documentation) {
				viewBean = new DocumentationViewBean((Documentation) resource);
			}
			else if (resource instanceof ProfessionalTraining) {
				viewBean = new ProfessionalTrainingViewBean((ProfessionalTraining) resource);
			}
		}
		return viewBean;
	}

	public ResourceViewBean getObjectEditedResource() {
		return objectEditedResource;
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public void setSelectedResourceViewBean(ResourceViewBean resourceViewBean) {
		selectedResourceViewBean = resourceViewBean;
	}

	/**
	 * This method prepares objectEditedResource to be a new ResourceViewBean.
	 * This method takes a ResourceViewBean as parameter because we annotated
	 * all classes which extend ResourceViewBean with @Named. This annotation
	 * allows to pass the name of the annotated class right from the JSF code,
	 * for example : prepareResourceCreation(equipmentViewBean) will
	 * automatically create an instance of EquipmentViewBean before entering the
	 * function prepareResourceCreation. And so we don't have to pass a String
	 * and do a big ugly switch on the received String to instanciate the
	 * correct view bean below.
	 * 
	 * NOTE : this works once, but not twice, because the initialized bean
	 * is reused (see http://stackoverflow.com/questions/29253863), so
	 * I'm keeping a switch here to create a new view bean. If one day I use
	 * CDI to manage the scopes (and not Spring), then putting @RequestScoped
	 * in all the view beans inheriting from ResourceViewBean might allow me
	 * to create a new view bean each time we click on "Create new resource".
	 * 
	 * @param freshlyCreatedResourceViewBean
	 *            freshly created ResourceViewBean
	 */
	public void prepareResourceCreation(ResourceViewBean freshlyCreatedResourceViewBean) {
		if (freshlyCreatedResourceViewBean instanceof EquipmentViewBean) {
			objectEditedResource = new EquipmentViewBean();
		}
		else if (freshlyCreatedResourceViewBean instanceof SoftwareViewBean) {
			objectEditedResource = new SoftwareViewBean();
		}
		else if (freshlyCreatedResourceViewBean instanceof PedagogicalAndDocumentaryResourceViewBean) {
			objectEditedResource = new PedagogicalAndDocumentaryResourceViewBean();
		}
		else if (freshlyCreatedResourceViewBean instanceof DocumentationViewBean) {
			objectEditedResource = new DocumentationViewBean();
		}
		else if (freshlyCreatedResourceViewBean instanceof ProfessionalTrainingViewBean) {
			objectEditedResource = new ProfessionalTrainingViewBean();
		}
		preparePicklistOrganisationViewBeans();
		setSelectedResourceViewBean(null);
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	/**
	 * This method prepares objectEditedResource to be a copy of an existing
	 * ResourceViewBean.
	 * 
	 * @param resourceViewBean A resourceViewBean to copy and prepare for modification
	 */
	public void prepareResourceModification(ResourceViewBean resourceViewBean) {
		// To make sure we work on a copy, we load the domain bean
		Resource resource = resourcesService.retrieveResource(resourceViewBean.getId(), true);
		// and we instanciate a new ResourceViewBean above it
		objectEditedResource = createResourceViewBean(resource);

		// We initialize Organisations attached to objectEditedResource
		objectEditedResource.setOrganisationPossessingResourceViewBean(resourceViewBean.getOrganisationPossessingResourceViewBean());
		objectEditedResource.setOrganisationSupportingResourceViewBean(resourceViewBean.getOrganisationSupportingResourceViewBean());
		objectEditedResource.setOrganisationViewingResourceViewBeans(resourceViewBean.getOrganisationViewingResourceViewBeans());

		preparePicklistOrganisationViewBeans();
		setSelectedResourceViewBean(resourceViewBean);
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void onSaveResource() {
		if (objectEditedResource.getId() == null) {
			logger.info("onSaveResource, we call createResourceFromObjectEdited");
			createResourceFromObjectEdited();
		}
		else {
			logger.info("onSaveResource, we call modifyResourceFromObjectEdited");
			modifyResourceFromObjectEdited();
		}
	}

	private void createResourceFromObjectEdited() {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (!iconFileName.trim().isEmpty()) {
			commonUploadOneDialog.moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
		}
		objectEditedResource.setIconFileName(iconFileName);

		Resource resource = resourcesService.createResource(selectedToolCategoryViewBean.getDomainBean(),
				objectEditedResource.getOrganisationPossessingResourceViewBean().getDomainBean(),
				objectEditedResource.getOrganisationSupportingResourceViewBean().getDomainBean(),
				objectEditedResource.getType(),
				objectEditedResource.getName(),
				objectEditedResource.getIconFileName());
		if (resource != null) {
			for (OrganisationViewBean organisationViewBean : objectEditedResource.getOrganisationViewingResourceViewBeans()) {
				resource.getOrganisationsHavingAccessToResource().add(organisationViewBean.getDomainBean());
			}
			resource = resourcesService.updateResource(resource);

			ResourceViewBean resourceViewBean = createResourceViewBean(resource);
			resourceViewBean.setOrganisationPossessingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId(),false)));
			resourceViewBean.setOrganisationSupportingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId(),false)));
			for (Organisation organisation : resource.getOrganisationsHavingAccessToResource()) {
				resourceViewBean.getOrganisationViewingResourceViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(organisation.getId(),false)));
			}

			selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
			Collections.sort(selectedToolCategoryViewBean.getResourceViewBeans());

			MessageDisplayer.info(
					msgs.getMessage("MY_TOOLS.RESOURCE_CREATION_SUCCESSFUL.TITLE", null, getCurrentUserLocale()),
					msgs.getMessage("MY_TOOLS.RESOURCE_CREATION_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.error(
					msgs.getMessage("MY_TOOLS.RESOURCE_CREATION_FAILURE.TITLE", null, getCurrentUserLocale()),
					msgs.getMessage("MY_TOOLS.RESOURCE_CREATION_FAILURE.DETAILS", null, getCurrentUserLocale()),logger);
		}
	}

	private void modifyResourceFromObjectEdited() {
		if (objectEditedResource != null) {
			String iconFileName = commonUploadOneDialog.getFileUploadedName();
			if (!iconFileName.trim().isEmpty()) {
				commonUploadOneDialog.moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
				this.temporaryFilePath = null;
				commonUploadOneDialog.reset();
				// We set the icon file name inside this block, because if the user
				// didn't upload any icon, we keep the current icon.
				objectEditedResource.setIconFileName(iconFileName);
			}

			Resource resource = resourcesService.updateResource(objectEditedResource.getDomainBean());
			if (resource != null) {
				resource.getOrganisationsHavingAccessToResource().clear();
				for (OrganisationViewBean organisationViewBean : objectEditedResource.getOrganisationViewingResourceViewBeans()) {
					resource.getOrganisationsHavingAccessToResource().add(organisationViewBean.getDomainBean());
				}
				resource = resourcesService.updateResource(resource);

				ResourceViewBean resourceViewBean = createResourceViewBean(resource);
				resourceViewBean.setOrganisationPossessingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId(),false)));
				resourceViewBean.setOrganisationSupportingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId(),false)));
				for (Organisation organisation : resource.getOrganisationsHavingAccessToResource()) {
					resourceViewBean.getOrganisationViewingResourceViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisationsService.retrieveOrganisation(organisation.getId(),false)));
				}
				int selectedResourcePosition = selectedToolCategoryViewBean.getResourceViewBeans().lastIndexOf(selectedResourceViewBean);
				selectedToolCategoryViewBean.getResourceViewBeans().set(selectedResourcePosition, resourceViewBean);
				Collections.sort(selectedToolCategoryViewBean.getResourceViewBeans());
				selectedResourceViewBean = resourceViewBean;

				MessageDisplayer.info(
						msgs.getMessage("MY_TOOLS.RESOURCE_MODIFICATION_SUCCESSFUL.TITLE", null, getCurrentUserLocale()),
						msgs.getMessage("MY_TOOLS.RESOURCE_MODIFICATION_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(
						msgs.getMessage("MY_TOOLS.RESOURCE_MODIFICATION_FAILURE.TITLE", null, getCurrentUserLocale()),
						msgs.getMessage("MY_TOOLS.RESOURCE_MODIFICATION_FAILURE.DETAILS", null, getCurrentUserLocale()),logger);
			}
		}
	}

	public void onDeleteSelectedResource() {
		if (resourcesService.deleteResource(getSelectedResourceViewBean().getDomainBean().getId())) {
			selectedToolCategoryViewBean.getDomainBean().getResources().remove(getSelectedResourceViewBean().getDomainBean());
			selectedToolCategoryViewBean.getResourceViewBeans().remove(getSelectedResourceViewBean());
			setSelectedResourceViewBean(null);
			MessageDisplayer.info(
					msgs.getMessage("MY_TOOLS.DELETE_TOOL_MODAL_WINDOW.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
					msgs.getMessage("MY_TOOLS.DELETE_TOOL_MODAL_WINDOW.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.error(
					msgs.getMessage("MY_TOOLS.DELETE_TOOL_MODAL_WINDOW.DELETION_FAILURE.TITLE",null,getCurrentUserLocale()),
					msgs.getMessage("MY_TOOLS.DELETE_TOOL_MODAL_WINDOW.DELETION_FAILURE.DETAILS",null,getCurrentUserLocale()),
					logger);
		}
	}

	public void onToolRowSelect(SelectEvent event) {
		setSelectedResourceViewBean((ResourceViewBean) event.getObject());
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public void preparePicklistOrganisationViewBeans() {
		List<OrganisationViewBean> allOrganisationViewBeans = new ArrayList<OrganisationViewBean>();
		for (Organisation organisation : organisationsService.retrieveAllOrganisations()) {
			allOrganisationViewBeans.add(OrganisationViewBeanGenerator.getViewBean(organisation));
		}
		Collections.sort(allOrganisationViewBeans);
		pickListBean.getPickListEntities().getSource().clear();
		pickListBean.getPickListEntities().getSource().addAll(allOrganisationViewBeans);
		pickListBean.getPickListEntities().getTarget().clear();
		if (getObjectEditedResource().getDomainBean() != null) {
			for (OrganisationViewBean organisationViewBean : allOrganisationViewBeans) {
				if (getObjectEditedResource().getDomainBean().getOrganisationsHavingAccessToResource().contains(organisationViewBean.getDomainBean())) {
					pickListBean.getPickListEntities().getSource().remove(organisationViewBean);
					pickListBean.getPickListEntities().getTarget().add(organisationViewBean);
				}
			}
		}
	}

	public void onTransfer(TransferEvent event) {
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				objectEditedResource.getOrganisationViewingResourceViewBeans().add(movedOrganisationViewBean);
			}
			else {
				objectEditedResource.getOrganisationViewingResourceViewBeans().remove(movedOrganisationViewBean);
			}
		}
	}

	/*
	 * ****************** Pedagogical uses of selected tool category ********************
	 */
	public String getTreeNodeType_NEED_LEAF() {
		return PedagogicalAdviceTreeGenerator.getTreeNodeType_NEED_LEAF();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return PedagogicalAdviceTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return PedagogicalAdviceTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS();
	}

	public String getTreeNodeType_ANSWER_LEAF() {
		return PedagogicalAdviceTreeGenerator.getTreeNodeType_ANSWER_LEAF();
	}

	public TreeNode getPedagogicalUsesTreeRoot() {
		if (pedagogicalUsesTreeBean != null) {
			TreeNode root = pedagogicalUsesTreeBean.getRoot();
			pedagogicalUsesTreeBean.expandIncludingChildren(root);
			return root;
		}
		else {
			return null;
		}
	}

	private void setPedagogicalUsesTreeRoot(ResourceCategory resourceCategory) {
		logger.debug("Entering setPedagogicalUsesTreeRoot");
		pedagogicalUsesTreeBean = pedagogicalAdviceTreeGenerator.createNeedAndAnswersTree(null);
		Set<Long> idsOfLeavesToKeep = new HashSet<Long>();
		for (PedagogicalAnswer answer : resourceCategory.getAnswers()) {
			idsOfLeavesToKeep.add(answer.getId());
		}
		pedagogicalUsesTreeBean.retainLeavesAndParents(idsOfLeavesToKeep);
		logger.debug("Leaving setPedagogicalUsesTreeRoot");
	}

	/*
	 * ****************** Modal dialogs ********************
	 */
	@Override
	public ImagesService getImagesService() {
		return imagesService;
	}

	@Override
	public ApplicationService getApplicationService() {
		return applicationService;
	}

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
	 * @see eu.ueb.acem.web.utils.include.CommonUploadOneDialogInterface#setSelectedFromCommonUploadOneDialog(java.nio.file.Path)
	 */
	@Override
	public void setSelectedFromCommonUploadOneDialog(Path temporaryFilePath) {
		// Remove previously uploaded file if it exists
		commonUploadOneDialog.deleteTemporaryFileIfExists(this.temporaryFilePath);

		// Memorize new one
		this.temporaryFilePath = temporaryFilePath;

		// If null there was an error on file copy
		if (temporaryFilePath == null) {
			MessageDisplayer.error(msgs.getMessage(
					"SERVICE_FILEUTIL_FILE_NOT_CREATED", null,
					getCurrentUserLocale()), "", logger);
		}

		Ajax.update("createToolCategoryForm:iconOutputPanel", "modifyToolCategoryForm:iconOutputPanel",
				"createResourceForm:iconOutputPanel", "modifyResourceForm:iconOutputPanel");
	}

	public Path getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void removeObjectEditedToolCategoryIcon() {
		objectEditedToolCategory.setIconFileName("");
		resetTemporaryFilePath();
	}

	public void removeObjectEditedResourceIcon() {
		objectEditedResource.setIconFileName("");
		resetTemporaryFilePath();
	}

	private void resetTemporaryFilePath() {
		commonUploadOneDialog.deleteTemporaryFileIfExists(this.temporaryFilePath);
		this.temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	/**
	 * Called when the user closes the dialog containing an image uploader.
	 * @param event A Primefaces CloseEvent
	 */
	public void onCloseDialogWithUploadedFile(CloseEvent event) {
		// Remove previously uploaded file if it exists
		commonUploadOneDialog.deleteTemporaryFileIfExists(this.temporaryFilePath);
	}

}