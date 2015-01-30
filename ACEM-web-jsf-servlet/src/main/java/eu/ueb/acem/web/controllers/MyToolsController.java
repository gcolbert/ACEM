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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
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
import eu.ueb.acem.web.utils.ResourceViewBeanGenerator;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("myToolsController")
@Scope("view")
public class MyToolsController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5663154564837226988L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyToolsController.class);

	@Inject
	private ResourcesService resourcesService;
	
	@Inject
	private UsersService usersService;
	
	@Inject
	private ScenariosService scenariosService;

	@Inject
	private ResourceViewBeanGenerator resourceViewBeanGenerator;

	@Inject
	private OrganisationsService organisationsService;
	@Inject
	private OrganisationViewBeanGenerator organisationViewBeanGenerator;
	private List<SelectItem> allOrganisationViewBeans;

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;
	private EditableTreeBean pedagogicalUsesTreeBean;

	@Inject
	private EditableTreeBean categoriesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private TreeNode categoriesTreeSelectedNode;

	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private ResourceViewBean selectedResourceViewBean;

	private static final String[] RESOURCE_TYPES = { "software", "softwareDocumentation", "equipment",
			"pedagogicalAndDocumentaryResources", "professionalTraining" };
	private static final String[] RESOURCE_TYPES_I18N_FR = { "Applicatif", "Documentation d'applicatif", "Équipement",
		"Ressource documentaire et pédagogique", "Formation pour les personnels" };
	private static final String[] RESOURCE_TYPES_I18N_EN = { "Software", "Software documentation", "Equipment",
		"Pedagogical and documentary resource", "Professional training" };

	public MyToolsController() {
		allOrganisationViewBeans = new ArrayList<SelectItem>();
	}

	@PostConstruct
	public void init() {
		logger.info("entering init");
		loadAllOrganisationViewBeans();
		logger.info("leaving init");
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

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public List<String> getAllResourceTypes() {
		return Arrays.asList(RESOURCE_TYPES);
	}

	public List<String> getAllResourceTypes_i18n_fr() {
		logger.info("getAllResourceTypes_i18n_fr");
		return Arrays.asList(RESOURCE_TYPES_I18N_FR);
	}

	public List<String> getAllResourceTypes_i18n_en() {
		logger.info("getAllResourceTypes_i18n_en");
		return Arrays.asList(RESOURCE_TYPES_I18N_EN);
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
			if (resourceType.equals("software")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("softwareDocumentation")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("equipment")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("pedagogicalAndDocumentaryResources")) {
				categoriesTreeBean
						.addVisibleRoot(msgs.getMessage("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL",null,getCurrentUserLocale()));
			}
			else if (resourceType.equals("professionalTraining")) {
				categoriesTreeBean.addVisibleRoot(msgs.getMessage("RESOURCES.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL",null,getCurrentUserLocale()));
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

		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(toolCategoryId);
		if (toolCategory != null) {
			setSelectedToolCategoryViewBean(new ToolCategoryViewBean(toolCategory));
		}
		else {
			selectedToolCategoryId = null;
			selectedToolCategoryViewBean = null;
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
				ResourceViewBean resourceViewBean = resourceViewBeanGenerator.getResourceViewBean(resource.getId());

				resourceViewBean.setOrganisationPossessingResourceViewBean(organisationViewBeanGenerator.getOrganisationViewBean(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId()));

				if (resourceViewBean.getDomainBean().getOrganisationSupportingResource() != null) {
					resourceViewBean.setOrganisationSupportingResourceViewBean(organisationViewBeanGenerator.getOrganisationViewBean(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId()));
				}
				
				for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
					resourceViewBean.getOrganisationViewingResourceViewBeans().add(organisationViewBeanGenerator.getOrganisationViewBean(organisation.getId()));
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

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public void setSelectedResourceViewBean(ResourceViewBean resourceViewBean) {
		selectedResourceViewBean = resourceViewBean;
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
			arrayForCommunities[i] = new SelectItem(organisationViewBeanGenerator.getOrganisationViewBean(community.getId()),community.getName());
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
			arrayForInstitutions[i] = new SelectItem(organisationViewBeanGenerator.getOrganisationViewBean(institution.getId()),institution.getName());
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
			arrayForAdministrativeDepartments[i] = new SelectItem(organisationViewBeanGenerator.getOrganisationViewBean(administrativeDepartment.getId()),administrativeDepartment.getName());
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
			arrayForTeachingDepartments[i] = new SelectItem(organisationViewBeanGenerator.getOrganisationViewBean(teachingDepartment.getId()),teachingDepartment.getName());
			i++;
		}
		groupTeachingDepartments.setSelectItems(arrayForTeachingDepartments);
		allOrganisationViewBeans.add(groupTeachingDepartments);
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

	public void onSelectedToolCategorySave() {
		logger.info("onSelectedToolCategorySave");
		selectedToolCategoryViewBean.setResourceCategory(resourcesService
				.updateResourceCategory(selectedToolCategoryViewBean.getResourceCategory()));
	}

	public void onToolRowSelect(SelectEvent event) {
		logger.info("onToolRowSelect");
		setSelectedResourceViewBean((ResourceViewBean) event.getObject());
	}

	public void onCreateResource(String newResourceType, OrganisationViewBean newResourceOwnerOrganisation, OrganisationViewBean newResourceSupportOrganisation, String newResourceName, String iconFileName) {
		Resource resource = resourcesService.createResource(selectedToolCategoryId, newResourceOwnerOrganisation.getId(), newResourceSupportOrganisation.getId(), newResourceType, newResourceName, iconFileName);
		if (resource != null) {
			ResourceViewBean resourceViewBean = resourceViewBeanGenerator.getResourceViewBean(resource.getId());

			resourceViewBean.setOrganisationPossessingResourceViewBean(organisationViewBeanGenerator.getOrganisationViewBean(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId()));
			resourceViewBean.setOrganisationSupportingResourceViewBean(organisationViewBeanGenerator.getOrganisationViewBean(resourceViewBean.getDomainBean().getOrganisationSupportingResource().getId()));

			for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
				resourceViewBean.getOrganisationViewingResourceViewBeans().add(organisationViewBeanGenerator.getOrganisationViewBean(organisation.getId()));
			}

			selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
		}
	}

	public void onModifySelectedResource(String newIconFileName) {
		selectedResourceViewBean.setIconFileName(newIconFileName);
		selectedResourceViewBean.setDomainBean(resourcesService.updateResource(selectedResourceViewBean.getDomainBean()));
		selectedToolCategoryViewBean.getResourceViewBeans().remove(selectedResourceViewBean);
		selectedToolCategoryViewBean.getResourceViewBeans().add(selectedResourceViewBean);
	}

	public void onDeleteSelectedResource() {
		if (resourcesService.deleteResource(getSelectedResourceViewBean().getDomainBean().getId())) {
			selectedToolCategoryViewBean.getResourceViewBeans().remove(getSelectedResourceViewBean());
			MessageDisplayer.showMessageToUserWithSeverityInfo(
					msgs.getMessage("RESOURCES.DELETE_TOOL_MODAL_WINDOW.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
					msgs.getMessage("RESOURCES.DELETE_TOOL_MODAL_WINDOW.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
		}
		else {
			MessageDisplayer.showMessageToUserWithSeverityError(
					msgs.getMessage("RESOURCES.DELETE_TOOL_MODAL_WINDOW.DELETION_FAILURE.TITLE",null,getCurrentUserLocale()),
					msgs.getMessage("RESOURCES.DELETE_TOOL_MODAL_WINDOW.DELETION_FAILURE.DETAILS",null,getCurrentUserLocale()));
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

}