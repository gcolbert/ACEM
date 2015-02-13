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
import java.util.Arrays;
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
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.utils.NeedsAndAnswersTreeGenerator;
import eu.ueb.acem.web.utils.OrganisationViewBeanGenerator;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialog;
import eu.ueb.acem.web.utils.include.CommonUploadOneDialogInterface;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
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
	private ScenariosService scenariosService;

	/* ***********************************************************************/

	@Inject
	private OrganisationsService organisationsService;

	/**
	 * List of all organisations (needed for the admin to associate a resource with an organisation)
	 */
	private List<SelectItem> allOrganisationViewBeans;

	/* ***********************************************************************/

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;
	private EditableTreeBean pedagogicalUsesTreeBean;

	@Inject
	private EditableTreeBean categoriesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private TreeNode categoriesTreeSelectedNode;

	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private List<ToolCategoryViewBean> allToolCategoryViewBeans;
	private ToolCategoryViewBean objectEdited;

	/* ***********************************************************************/

	@Inject
	private ResourcesService resourcesService;
	private ResourceViewBean selectedResourceViewBean;
	private static final String[] RESOURCE_TYPES = { "RESOURCE_TYPE_SOFTWARE", "RESOURCE_TYPE_SOFTWARE_DOCUMENTATION",
			"RESOURCE_TYPE_EQUIPMENT", "RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE",
			"RESOURCE_TYPE_PROFESSIONAL_TRAINING" };
	
	/* ***********************************************************************/

	/**
	 * Dialog for uploading an icon
	 */
	private CommonUploadOneDialog commonUploadOneDialog;

	/**
	 * Uploaded file
	 */
	private Path temporaryFilePath;

	/* ***********************************************************************/

	public MyToolsController() {
		allOrganisationViewBeans = new ArrayList<SelectItem>();
		allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
	}

	@PostConstruct
	public void init() {
		this.commonUploadOneDialog = new CommonUploadOneDialog(this);
		loadAllOrganisationViewBeans();
		loadAllToolCategoryViewBeans();
	}

	@Override
	public String getPageTitle() {
		StringBuffer sb = new StringBuffer();
		sb.append(msgs.getMessage("MENU.MY_TOOLS",null,getCurrentUserLocale()));
		if (getSelectedToolCategoryViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedToolCategoryViewBean().getName());
		}
		return sb.toString();
	}

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public List<String> getAllResourceTypes() {
		return Arrays.asList(RESOURCE_TYPES);
	}

	public void prepareToolCategoryTreeForResourceType(String resourceType) {
		logger.info("Entering prepareToolCategoryTreeForResourceType for resourceType={}", resourceType);
		categoriesTreeBean.clear();
		setSelectedToolCategoryId(null);
		// When the category changes, we reset the selected category
		setSelectedToolCategoryViewBean(null);

		List<ResourceCategory> resourceCategoriesForCurrentType = new ArrayList<ResourceCategory>(resourcesService.retrieveCategoriesForResourceType(resourceType));
		Collections.sort(resourceCategoriesForCurrentType);

		// TODO : check if we need to keep the "true" possibility (allows to
		// start from an empty tree, and add nodes by right-clicking on the
		// visible root node)
		boolean repeatSelectedCategoryAsVisibleRootNode = false;
		if (repeatSelectedCategoryAsVisibleRootNode) {
			if (resourceType.equals("RESOURCE_TYPE_SOFTWARE")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("MY_TOOLS.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("RESOURCE_TYPE_SOFTWARE_DOCUMENTATION")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("MY_TOOLS.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("RESOURCE_TYPE_EQUIPMENT")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("MY_TOOLS.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE")) {
				categoriesTreeBean
						.addVisibleRoot(msgs.getMessage("MY_TOOLS.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_MY_TOOLS.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("RESOURCE_TYPE_PROFESSIONAL_TRAINING")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("MY_TOOLS.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL",null,getCurrentUserLocale()));
			}
			else {
				logger.error("Unknown resourceType '{}'", resourceType);
			}
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
		logger.info("Leaving prepareToolCategoryTreeForResourceType for resourceType={}", resourceType);
	}

	public Long getSelectedToolCategoryId() {
		return selectedToolCategoryId;
	}

	public void setSelectedToolCategoryId(Long toolCategoryId) {
		logger.info("Entering setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
		selectedToolCategoryId = toolCategoryId;
		if (toolCategoryId != null) {
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(toolCategoryId, true);
			if (toolCategory != null) {
				setSelectedToolCategoryViewBean(new ToolCategoryViewBean(toolCategory));
			}
			else {
				selectedToolCategoryId = null;
				selectedToolCategoryViewBean = null;
			}
		}
		logger.info("Leaving setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
	}

	public ToolCategoryViewBean getSelectedToolCategoryViewBean() {
		return selectedToolCategoryViewBean;
	}

	public void setSelectedToolCategoryViewBean(ToolCategoryViewBean toolCategoryViewBean) {
		logger.info("Entering setSelectedToolCategoryViewBean");
		selectedToolCategoryViewBean = toolCategoryViewBean;

		// When the category changes, we have to lose the current selected resource
		// in the datatable of this category
		setSelectedResourceViewBean(null);

		if (selectedToolCategoryViewBean != null) {
			// We initialize the checkbox "category is a favorite category for the user"
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean) getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteToolCategoryViewBeans().contains(selectedToolCategoryViewBean)) {
					selectedToolCategoryViewBean.setFavoriteToolCategory(true);
				}
				else {
					selectedToolCategoryViewBean.setFavoriteToolCategory(false);
				}
			}

			// We associate the ResourceViewBeans
			selectedToolCategoryViewBean.getResourceViewBeans().clear();
			for (Resource resource : selectedToolCategoryViewBean.getDomainBean().getResources()) {
				resource = resourcesService.retrieveResource(resource.getId(), true);
				ResourceViewBean resourceViewBean = getResourceViewBean(resource);

				resourceViewBean.setOrganisationPossessingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(resourceViewBean.getDomainBean().getOrganisationPossessingResource()));

				if (resourceViewBean.getDomainBean().getOrganisationSupportingResource() != null) {
					Organisation supportService = organisationsService.retrieveOrganisation(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId(), true);
					resourceViewBean.setOrganisationSupportingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(supportService));
				}

				for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
					resourceViewBean.getOrganisationViewingResourceViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisation));
				}

				for (UseMode useMode : resourceViewBean.getDomainBean().getUseModes()) {
					resourceViewBean.getUseModeViewBeans().add(new UseModeViewBean(useMode));
				}

				selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
			}

			// We initialize the "pedagogical advice" tree
			setPedagogicalUsesTreeRoot(selectedToolCategoryViewBean.getDomainBean());

			// We associate the PedagogicalScenarioViewBeans
			for (PedagogicalActivity pedagogicalActivity : selectedToolCategoryViewBean.getDomainBean().getPedagogicalActivities()) {
				pedagogicalActivity = scenariosService.retrievePedagogicalActivity(pedagogicalActivity.getId(), true);
				for (PedagogicalScenario pedagogicalScenario : pedagogicalActivity.getScenarios()) {
					pedagogicalScenario = scenariosService.retrievePedagogicalScenario(pedagogicalScenario.getId(), true);
					PedagogicalScenarioViewBean pedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(pedagogicalScenario);
					if (! selectedToolCategoryViewBean.getScenarioViewBeans().contains(pedagogicalScenarioViewBean)) {
						selectedToolCategoryViewBean.getScenarioViewBeans().add(pedagogicalScenarioViewBean);
					}
				}
			}
		}
		logger.info("Leaving setSelectedToolCategoryViewBean");
	}

	public TreeNode getCategoriesTreeRoot() {
		return categoriesTreeBean.getRoot();
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
	public TreeNode getSelectedNode() {
		return categoriesTreeSelectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (categoriesTreeSelectedNode != null) {
			categoriesTreeSelectedNode.setSelected(false);
		}
		categoriesTreeSelectedNode = selectedNode;

		categoriesTreeBean.expandOnlyOneNode(categoriesTreeSelectedNode);

		if ((categoriesTreeSelectedNode != null)
				&& (categoriesTreeSelectedNode.getType().equals(getTreeNodeType_CATEGORY()))) {
			setSelectedToolCategoryId(((TreeNodeData) categoriesTreeSelectedNode.getData()).getId());
		}
	}

	public void prepareToolCategoryCreation() {
		objectEdited = new ToolCategoryViewBean();
		selectedToolCategoryViewBean = null;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void prepareToolCategoryModification(ToolCategoryViewBean toolCategoryViewBean) {
		objectEdited = toolCategoryViewBean;
		selectedToolCategoryViewBean = toolCategoryViewBean;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public ToolCategoryViewBean getObjectEdited() {
		return objectEdited;
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

	public void onCreateToolCategory(String name, String description) {
		objectEdited.setName(name);
		objectEdited.setDescription(description);

		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (! iconFileName.trim().isEmpty()) {
			moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
		}
		objectEdited.setIconFileName(iconFileName);

		ResourceCategory toolCategory = resourcesService.createResourceCategory(name, description, iconFileName);
		if (toolCategory != null) {
			ToolCategoryViewBean newToolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
			allToolCategoryViewBeans.add(newToolCategoryViewBean);
			Collections.sort(allToolCategoryViewBeans);
			MessageDisplayer.info(msgs.getMessage(
					"TOOL_CATEGORIES.CREATION_SUCCESSFUL.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"TOOL_CATEGORIES.CREATION_SUCCESSFUL.DETAILS", null,
					getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.info(msgs.getMessage(
					"TOOL_CATEGORIES.CREATION_FAILURE.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"TOOL_CATEGORIES.CREATION_FAILURE.DETAILS", null,
					getCurrentUserLocale()));
		}
	}

	public void onModifyToolCategory(ToolCategoryViewBean toolCategoryViewBean) {
		if (toolCategoryViewBean != null) {
			toolCategoryViewBean.getDomainBean().setName(toolCategoryViewBean.getName());
			toolCategoryViewBean.getDomainBean().setDescription(toolCategoryViewBean.getDescription());
			toolCategoryViewBean.getDomainBean().setIconFileName(toolCategoryViewBean.getIconFileName());
			toolCategoryViewBean.setDomainBean(resourcesService.updateResourceCategory(toolCategoryViewBean.getDomainBean()));
			MessageDisplayer.info(msgs.getMessage(
					"TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.DETAILS", null,
					getCurrentUserLocale()));
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
		if (getCurrentUserViewBean() instanceof TeacherViewBean) {
			TeacherViewBean currentUserViewBean = (TeacherViewBean)getCurrentUserViewBean();
			if (currentUserViewBean.getFavoriteToolCategoryViewBeans().contains(toolCategoryViewBean)) {
				if (usersService.removeFavoriteToolCategoryForTeacher(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
					currentUserViewBean.getFavoriteToolCategoryViewBeans().remove(toolCategoryViewBean);
				}
			}
			else {
				if (usersService.addFavoriteToolCategoryForTeacher(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
					currentUserViewBean.getFavoriteToolCategoryViewBeans().add(toolCategoryViewBean);
				}
			}
		}
	}

	/*
	 * ****************** Resources ********************
	 */
	public ResourceViewBean getResourceViewBean(Resource resource) {
		ResourceViewBean viewBean = null;
		if (resource != null) {
			if (resource instanceof Software) {
				viewBean = new SoftwareViewBean((Software) resource);
			}
			else if (resource instanceof PedagogicalAndDocumentaryResource) {
				viewBean = new DocumentaryAndPedagogicalResourceViewBean((PedagogicalAndDocumentaryResource) resource);
			}
			else if (resource instanceof Equipment) {
				viewBean = new EquipmentViewBean((Equipment) resource);
			}
			else if (resource instanceof SoftwareDocumentation) {
				viewBean = new SoftwareDocumentationViewBean((SoftwareDocumentation) resource);
			}
			else if (resource instanceof ProfessionalTraining) {
				viewBean = new ProfessionalTrainingViewBean((ProfessionalTraining) resource);
			}
		}
		return viewBean;
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public void setSelectedResourceViewBean(ResourceViewBean resourceViewBean) {
		selectedResourceViewBean = resourceViewBean;
	}

	public void prepareResourceCreation() {
		selectedResourceViewBean = null;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void prepareResourceModification(ResourceViewBean resourceViewBean) {
		selectedResourceViewBean = resourceViewBean;
		temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	public void onCreateResource(String newResourceType, OrganisationViewBean newResourceOwnerOrganisation, OrganisationViewBean newResourceSupportOrganisation, String newResourceName) {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (! iconFileName.trim().isEmpty()) {
			moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
		}

		Resource resource = resourcesService.createResource(selectedToolCategoryId, newResourceOwnerOrganisation.getId(), newResourceSupportOrganisation.getId(), newResourceType, newResourceName, iconFileName);
		if (resource != null) {
			ResourceViewBean resourceViewBean = getResourceViewBean(resource);

			resourceViewBean.setOrganisationPossessingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(resourceViewBean.getDomainBean().getOrganisationPossessingResource()));
			resourceViewBean.setOrganisationSupportingResourceViewBean(OrganisationViewBeanGenerator.getViewBean(resourceViewBean.getDomainBean().getOrganisationSupportingResource()));

			for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
				resourceViewBean.getOrganisationViewingResourceViewBeans().add(OrganisationViewBeanGenerator.getViewBean(organisation));
			}

			selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
		}
	}

	public void onModifySelectedResource() {
		String iconFileName = commonUploadOneDialog.getFileUploadedName();
		if (! iconFileName.trim().isEmpty()) {
			moveUploadedIconToImagesFolder(this.temporaryFilePath, iconFileName);
			this.temporaryFilePath = null;
			commonUploadOneDialog.reset();
			// We set the icon file name inside this block, because if the user
			// didn't upload any icon, we keep the current icon.
			selectedResourceViewBean.setIconFileName(iconFileName);
		}
		selectedResourceViewBean.setDomainBean(resourcesService.updateResource(selectedResourceViewBean.getDomainBean()));
		selectedToolCategoryViewBean.getResourceViewBeans().remove(selectedResourceViewBean);
		selectedToolCategoryViewBean.getResourceViewBeans().add(selectedResourceViewBean);
	}

	public void onDeleteSelectedResource() {
		if (resourcesService.deleteResource(getSelectedResourceViewBean().getDomainBean().getId())) {
			selectedToolCategoryViewBean.getResourceViewBeans().remove(getSelectedResourceViewBean());
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
		logger.info("onToolRowSelect");
		setSelectedResourceViewBean((ResourceViewBean) event.getObject());
	}

	/*
	 * ****************** Pedagogical uses of selected tool category ********************
	 */
	public String getTreeNodeType_NEED_LEAF() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_LEAF();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS();
	}

	public String getTreeNodeType_ANSWER_LEAF() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_ANSWER_LEAF();
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
		logger.info("Entering setPedagogicalUsesTreeRoot");
		pedagogicalUsesTreeBean = needsAndAnswersTreeGenerator.createNeedAndAnswersTree(null);
		Set<Long> idsOfLeavesToKeep = new HashSet<Long>();
		for (PedagogicalAnswer answer : resourceCategory.getAnswers()) {
			idsOfLeavesToKeep.add(answer.getId());
		}
		pedagogicalUsesTreeBean.retainLeavesAndParents(idsOfLeavesToKeep);
		logger.info("Leaving setPedagogicalUsesTreeRoot");
	}

	/*
	 * ****************** Modal dialogs ********************
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
	public void setSelectedFromCommonUploadOneDialog(Path temporaryFilePath,
			String originalFileName) {
		// Remove previously uploaded file if it exists
		deleteTemporaryFileIfExists(this.temporaryFilePath);

		// Memorize new one
		this.temporaryFilePath = temporaryFilePath;

		// If null there was an error on file copy
		if (temporaryFilePath == null) {
			MessageDisplayer.error(msgs.getMessage(
					"SERVICE_FILEUTIL_FILE_NOT_CREATED", null,
					getCurrentUserLocale()), "", logger);
		}

		Ajax.update("createToolCategoryForm:iconOutputPanel", "createResourceForm:iconOutputPanel", "modifyResourceForm:iconOutputPanel");
	}

	public Path getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void removeToolCategoryIcon(ToolCategoryViewBean toolCategoryViewBean) {
		if (toolCategoryViewBean != null) {
			toolCategoryViewBean.setIconFileName("");
		}
		resetTemporaryFilePath();
	}

	public void removeResourceIcon(ResourceViewBean resourceViewBean) {
		if (resourceViewBean != null) {
			resourceViewBean.setIconFileName("");
		}
		resetTemporaryFilePath();
	}

	private void resetTemporaryFilePath() {
		deleteTemporaryFileIfExists(this.temporaryFilePath);
		this.temporaryFilePath = null;
		commonUploadOneDialog.reset();
	}

	/**
	 * Called when the user closes the dialog containing an image uploader.
	 * @param event
	 */
	public void onCloseDialogWithUploadedFile(CloseEvent event) {
		// Remove previously uploaded file if it exists
		deleteTemporaryFileIfExists(this.temporaryFilePath);
	}

	
}