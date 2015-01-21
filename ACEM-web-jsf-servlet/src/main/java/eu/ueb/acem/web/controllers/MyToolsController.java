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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
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
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
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
	private ResourceViewBeanHandler resourceViewBeanHandler;

	@Inject
	private OrganisationsService organisationsService;
	private List<OrganisationViewBean> allOrganisationViewBeans;
	@Inject
	private OrganisationViewBeanHandler organisationViewBeanHandler;

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;
	private EditableTreeBean pedagogicalUsesTreeBean;

	@Inject
	private EditableTreeBean categoriesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private TreeNode categoriesTreeSelectedNode;

	private Map<Long, ToolCategoryViewBean> toolCategoryViewBeans;
	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private ResourceViewBean selectedResourceViewBean;

	private static final String[] RESOURCE_TYPES = { "software", "softwareDocumentation", "equipment",
			"pedagogicalAndDocumentaryResources", "professionalTraining" };
	private String selectedResourceType; // One of RESOURCE_TYPES
	private Map<String, List<ToolCategoryViewBean>> toolCategoryViewBeansByResourceType;
	private static final String[] RESOURCE_TYPES_I18N_FR = { "Applicatif", "Documentation d'applicatif", "Équipement",
		"Ressource documentaire et pédagogique", "Formation pour les personnels" };
	private static final String[] RESOURCE_TYPES_I18N_EN = { "Software", "Software documentation", "Equipment",
		"Pedagogical and documentary resource", "Professional training" };
	
	public MyToolsController() {
		toolCategoryViewBeans = new HashMap<Long, ToolCategoryViewBean>();
		allOrganisationViewBeans = new ArrayList<OrganisationViewBean>();
		toolCategoryViewBeansByResourceType = new HashMap<String, List<ToolCategoryViewBean>>();
	}

	@PostConstruct
	public void init() {
		logger.info("Entering init()");

		for (ResourceCategory toolCategory : resourcesService.retrieveAllCategories()) {
			ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
			for (Resource tool : toolCategory.getResources()) {
				toolCategoryViewBean.addResourceViewBean(resourceViewBeanHandler.getResourceViewBean(tool.getId()));
			}

			List<PedagogicalScenario> scenarios = new ArrayList<PedagogicalScenario>(resourcesService.retrieveScenariosAssociatedWithResourceCategory(selectedToolCategoryId));
			for (PedagogicalScenario scenario : scenarios) {
				toolCategoryViewBean.addScenarioViewBean(new PedagogicalScenarioViewBean(scenario));
			}
			toolCategoryViewBeans.put(toolCategory.getId(), toolCategoryViewBean);
		}

		for (String resourceType : RESOURCE_TYPES) {
			toolCategoryViewBeansByResourceType.put(resourceType, new ArrayList<ToolCategoryViewBean>());
			for (ResourceCategory resourceCategory : resourcesService.retrieveCategoriesForResourceType(resourceType)) {
				toolCategoryViewBeansByResourceType.get(resourceType).add(new ToolCategoryViewBean(resourceCategory));
			}
			Collections.sort(toolCategoryViewBeansByResourceType.get(resourceType));
		}

		setAllOrganisationViewBeansAsList();

		logger.info("leaving init()");
		logger.info("------");
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
		this.selectedResourceType = resourceType;
		categoriesTreeBean.clear();
		setSelectedToolCategoryId(null);
		// When the category changes, we reset the selected category
		setSelectedToolCategoryViewBean(null);

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
			for (ToolCategoryViewBean categoryViewBean : toolCategoryViewBeansByResourceType.get(resourceType)) {
				categoriesTreeBean.addChild(getTreeNodeType_CATEGORY(), categoriesTreeBean.getVisibleRoots().get(0),
						categoryViewBean.getId(), categoryViewBean.getName(), "category");
			}
			categoriesTreeBean.getVisibleRoots().get(0).setExpanded(true);
		}
		else {
			for (ToolCategoryViewBean categoryViewBean : toolCategoryViewBeansByResourceType.get(resourceType)) {
				categoriesTreeBean.addChild(getTreeNodeType_CATEGORY(), categoriesTreeBean.getRoot(),
						categoryViewBean.getId(), categoryViewBean.getName(), "category");
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
		ToolCategoryViewBean toolCategoryViewBean = getToolCategoryViewBean(toolCategoryId);
		setSelectedToolCategoryViewBean(toolCategoryViewBean);

		// When the category changes, we have to lose the current selected resource
		// in the datatable of this category
		setSelectedResourceViewBean(null);

		if (selectedToolCategoryViewBean != null) {
			// We initialize the "pedagogical advice" tree for the selected
			// ToolCategoryViewBean
			setPedagogicalUsesTreeRoot(selectedToolCategoryViewBean.getDomainBean());

			// Is the selected category a favorite for the current user?
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean) getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteToolCategoryViewBeans().contains(selectedToolCategoryViewBean)) {
					selectedToolCategoryViewBean.setFavoriteToolCategory(true);
				}
				else {
					selectedToolCategoryViewBean.setFavoriteToolCategory(false);
				}
			}
		}
		logger.info("Leaving setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
	}

	public ToolCategoryViewBean getSelectedToolCategoryViewBean() {
		return selectedToolCategoryViewBean;
	}

	public void setSelectedToolCategoryViewBean(ToolCategoryViewBean toolCategoryViewBean) {
		this.selectedToolCategoryViewBean = toolCategoryViewBean;
		if (this.selectedToolCategoryViewBean != null) {
			this.selectedToolCategoryViewBean.getResourceViewBeans().clear();
			for (Resource resource : this.selectedToolCategoryViewBean.getDomainBean().getResources()) {
				ResourceViewBean resourceViewBean = resourceViewBeanHandler.getResourceViewBean(resource.getId());

				resourceViewBean.setOrganisationPossessingResourceViewBean(organisationViewBeanHandler.getOrganisationViewBean(resourceViewBean.getDomainBean().getOrganisationPossessingResource().getId()));
				
				for (Organisation organisation : resourceViewBean.getDomainBean().getOrganisationsHavingAccessToResource()) {
					resourceViewBean.addOrganisationViewingResourceViewBean(organisationViewBeanHandler.getOrganisationViewBean(organisation.getId()));
				}
				
				this.selectedToolCategoryViewBean.getResourceViewBeans().add(resourceViewBean);
			}
			for (PedagogicalActivity pedagogicalActivity : this.selectedToolCategoryViewBean.getDomainBean().getPedagogicalActivities()) {
				// TODO
			}
		}
	}

	private ToolCategoryViewBean getToolCategoryViewBean(Long id) {
		ToolCategoryViewBean viewBean = null;
		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(id);
		if (toolCategory != null) {
			viewBean = new ToolCategoryViewBean(toolCategory);
			for (Resource resource : toolCategory.getResources()) {
				viewBean.addResourceViewBean(resourceViewBeanHandler.getResourceViewBean(resource.getId()));
			}
		}
		else {
			logger.error("There is no category with id={} according to ResourcesService, so we return null", id);
		}
		return viewBean;
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

	public List<ToolCategoryViewBean> getAllToolCategoryViewBeans() {
		List<ToolCategoryViewBean> allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>(
				toolCategoryViewBeans.values());
		Collections.sort(allToolCategoryViewBeans);
		return allToolCategoryViewBeans;
	}

	public List<ToolCategoryViewBean> getToolCategoryViewBeansForSelectedResourceType() {
		return toolCategoryViewBeansByResourceType.get(selectedResourceType);
	}

	public List<OrganisationViewBean> getAllOrganisationViewBeansAsList() {
		return allOrganisationViewBeans;
	}

	private void setAllOrganisationViewBeansAsList() {
		Collection<Organisation> organisations = organisationsService.retrieveAllOrganisations();
		for (Organisation organisation : organisations) {
			if (organisation instanceof Community) {
				allOrganisationViewBeans.add(new CommunityViewBean((Community) organisation));
			}
			else if (organisation instanceof Institution) {
				allOrganisationViewBeans.add(new InstitutionViewBean((Institution) organisation));
			}
			else if (organisation instanceof AdministrativeDepartment) {
				allOrganisationViewBeans.add(new AdministrativeDepartmentViewBean((AdministrativeDepartment) organisation));
			}
			else if (organisation instanceof TeachingDepartment) {
				allOrganisationViewBeans.add(new TeachingDepartmentViewBean((TeachingDepartment) organisation));
			}
		}
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

	public void onNodeSelect() {
		// We need to keep this callback function empty.
		// Its purpose is to bind the Ajax "select" event of treenodes.
		// The real function is "setSelectedNode", which is called
		// by the "selection" attribute of the <p:tree>.
		// Thus, this callback does nothing, but it's necessary to have
		// it so that the tree and the selectedNode variable are
		// synchronized on "select" Ajax event.
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

	public void onCreateResource(String newResourceType, OrganisationViewBean newResourceSupportService, String newResourceName, String iconFileName) {
		logger.debug("onCreateResource, selectedToolCategoryViewBean.name={}", selectedToolCategoryViewBean.getName());
		logger.debug("onCreateResource, newResourceType={}, newResourceSupportService={}", newResourceType, newResourceSupportService);
		logger.debug("onCreateResource, newResourceName={}, iconFileName={}", newResourceName, iconFileName);
		Resource resource = resourcesService.createResource(selectedToolCategoryId, newResourceSupportService.getId(), newResourceType, newResourceName, iconFileName);
		if (resource != null) {
			ResourceViewBean resourceViewBean = resourceViewBeanHandler.getResourceViewBean(resource.getId());
			if (resourceViewBean != null) {
				selectedToolCategoryViewBean.addResourceViewBean(resourceViewBean);
			}
		}
	}

	public void onModifySelectedResource(String iconFileName) {
		logger.info("onModifySelectedResource({})", iconFileName);
		selectedResourceViewBean.setIconFileName(iconFileName);
		resourcesService.updateResource(selectedResourceViewBean.getDomainBean());
	}

	// TODO : implement the deleteResource method in the resourcesServiceImpl
	public void onDeleteSelectedResource() {
		if (resourcesService.deleteResource(getSelectedResourceViewBean().getDomainBean().getId())) {
			selectedToolCategoryViewBean.removeResourceViewBean(getSelectedResourceViewBean());
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

	/**
	 * TODO : move this inside the Administration/ToolCategory controller
	 * 
	 */
	public void onCreateToolCategory(String name, String description, String iconFileName) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateCommunity", name);
		ResourceCategory toolCategory = resourcesService.createResourceCategory(name, description, iconFileName);
//		ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
//		toolCategoryViewBeans.put(toolCategoryViewBean.getId(), toolCategoryViewBean);
	}

	public void onToolCategoryAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onToolCategoryAccordionPanelTabChange, tab={}", event.getTab());
		setSelectedToolCategoryViewBean((ToolCategoryViewBean) event.getData());
	}

	public void toggleFavoriteToolCategoryForCurrentUser(ToolCategoryViewBean toolCategoryViewBean) {
		logger.debug("Entering toggleFavoriteToolCategoryForCurrentUser, tool category name = {}", toolCategoryViewBean.getName());
		if (getCurrentUserViewBean() instanceof TeacherViewBean) {
			TeacherViewBean currentUserViewBean = (TeacherViewBean)getCurrentUserViewBean();
			if (currentUserViewBean.getFavoriteToolCategoryViewBeans().contains(toolCategoryViewBean)) {
				logger.info("user has tool category as favorite, we should remove it");
				if (usersService.removeFavoriteToolCategoryForTeacher(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
					currentUserViewBean.removeFavoriteToolCategoryViewBean(toolCategoryViewBean);
				}
			}
			else {
				logger.debug("user doesn't have tool category as favorite, we should add it");
				if (usersService.addFavoriteToolCategoryForTeacher(currentUserViewBean.getId(), toolCategoryViewBean.getId())) {
					currentUserViewBean.addFavoriteToolCategoryViewBean(toolCategoryViewBean);
				}
			}
		}
		logger.info("Leaving toggleFavoriteToolCategoryForCurrentUser, tool category name = {}", toolCategoryViewBean.getName());
	}

}