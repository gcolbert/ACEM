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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("resourcesController")
@Scope("view")
public class ResourcesController extends AbstractContextAwareController {

	private static final long serialVersionUID = -5663154564837226988L;

	private static final Logger logger = LoggerFactory.getLogger(ResourcesController.class);

	@Autowired
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;
	
	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private EditableTreeBean resourcesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	//private static final String TREE_NODE_TYPE_RESOURCE = "ResourceNode";
	private TreeNode resourcesTreeSelectedNode;

	private Map<Long, ToolCategoryViewBean> toolCategoryViewBeans;
	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private Map<Long, ResourceViewBean> resourceViewBeans;
	private Long selectedResourceId;
	private ResourceViewBean selectedResourceViewBean;

	private static final String[] RESOURCE_TYPES = { "software", "softwareDocumentation", "equipment",
			"pedagogicalAndDocumentaryResources", "professionalTraining" };
	private String selectedResourceType; // One of RESOURCE_TYPES
	private Map<String, List<ToolCategoryViewBean>> categoryViewBeansByResourceType;
	
	//@Autowired
	private EditableTreeBean pedagogicalUsesTreeBean;

	public ResourcesController() {
		toolCategoryViewBeans = new HashMap<Long, ToolCategoryViewBean>();
		resourceViewBeans = new HashMap<Long, ResourceViewBean>();
		categoryViewBeansByResourceType = new HashMap<String, List<ToolCategoryViewBean>>();
	}

	@PostConstruct
	public void initResourcesController() {
		logger.info("entering initResourcesController");
		pedagogicalUsesTreeBean = needsAndAnswersTreeGenerator.createNeedAndAnswersTree(null);

		for (String resourceType : RESOURCE_TYPES) {
			categoryViewBeansByResourceType.put(resourceType, new ArrayList<ToolCategoryViewBean>());
			for (ResourceCategory resourceCategory : resourcesService.retrieveCategoriesForResourceType(resourceType)) {
				categoryViewBeansByResourceType.get(resourceType).add(new ToolCategoryViewBean(resourceCategory));
			}
			Collections.sort(categoryViewBeansByResourceType.get(resourceType));
		}

		for (ResourceCategory resourceCategory : resourcesService.retrieveAllCategories()) {
			toolCategoryViewBeans.put(resourceCategory.getId(), new ToolCategoryViewBean(resourceCategory));
		}

		logger.info("leaving initResourcesController");
		logger.info("------");
	}

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	/*-
	public String getTreeNodeType_RESOURCE() {
		return TREE_NODE_TYPE_RESOURCE;
	}
	*/

	@SuppressWarnings("unchecked")
	public void prepareTree(String resourceType) {
		logger.info("prepareTree for resourceType={}", resourceType);
		this.selectedResourceType = resourceType;
		resourcesTreeBean.clear();
		switch (resourceType) {
		case "software":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL"));
			break;
		case "softwareDocumentation":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL"));
			break;
		case "equipment":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL"));
			break;
		case "pedagogicalAndDocumentaryResources":
			resourcesTreeBean
					.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL"));
			break;
		case "professionalTraining":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL"));
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
		for (ToolCategoryViewBean category : categoryViewBeansByResourceType.get(resourceType)) {
			logger.info("category = {}", category.getName());
			TreeNode categoryNode = resourcesTreeBean.addChild(getTreeNodeType_CATEGORY(), resourcesTreeBean
					.getVisibleRoots().get(0), category.getId(), category.getName(), "category");
			Collection<Ressource> entriesForCategory = null;
			switch (resourceType) {
			case "software":
				entriesForCategory = (Collection<Ressource>) resourcesService.retrieveSoftwaresWithCategory(category
						.getDomainBean());
				break;
			case "softwareDocumentation":
				entriesForCategory = (Collection<Ressource>) resourcesService
						.retrieveSoftwareDocumentationsWithCategory(category.getDomainBean());
				break;
			case "equipment":
				entriesForCategory = (Collection<Ressource>) resourcesService.retrieveEquipmentWithCategory(category
						.getDomainBean());
				break;
			case "pedagogicalAndDocumentaryResources":
				entriesForCategory = (Collection<Ressource>) resourcesService
						.retrievePedagogicalAndDocumentaryResourcesWithCategory(category.getDomainBean());
				break;
			case "professionalTraining":
				entriesForCategory = (Collection<Ressource>) resourcesService
						.retrieveProfessionalTrainingsWithCategory(category.getDomainBean());
				break;
			default:
				logger.error("Unknown resourceType '{}'", resourceType);
				break;
			}
			/*-
			for (Ressource entity : entriesForCategory) {
				resourcesTreeBean.addChild(getTreeNodeType_RESOURCE(), categoryNode, entity.getId(), entity.getName(),
						resourceType);
			}
			*/
		}
		resourcesTreeBean.getVisibleRoots().get(0).setExpanded(true);
	}

	public Long getSelectedToolCategoryId() {
		return selectedToolCategoryId;
	}

	@SuppressWarnings("unchecked")
	public void setSelectedToolCategoryId(Long toolCategoryId) {
		logger.info("Entering setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
		ToolCategoryViewBean toolCategoryViewBean = getToolCategoryViewBean(toolCategoryId);
		if (toolCategoryViewBean != null) {
			selectedToolCategoryId = toolCategoryId;
			selectedToolCategoryViewBean = toolCategoryViewBean;
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(toolCategoryId);
			/*-
			for (Ressource tool : (Set<Ressource>) toolCategory.getResources()) {
				logger.info("tool = {}", tool);
			}
			*/
			setPedagogicalUsesTreeRoot(toolCategory);

			// We initialize the "favoriteToolCategory" attribute of the
			// toolCategoryViewBean
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean) getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteToolCategoryViewBeans().contains(toolCategoryViewBean)) {
					toolCategoryViewBean.setFavoriteToolCategory(true);
				}
				else {
					toolCategoryViewBean.setFavoriteToolCategory(false);
				}
			}
		}
		logger.info("Leaving setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
	}

	public ToolCategoryViewBean getSelectedToolCategoryViewBean() {
		return selectedToolCategoryViewBean;
	}
	
	public Long getSelectedResourceId() {
		return selectedResourceId;
	}

	public void setSelectedResourceId(Long resourceId) {
		logger.info("Entering setSelectedResourceId, resourceId = {}", resourceId);
		ResourceViewBean resourceViewBean = getResourceViewBean(resourceId);
		if (resourceViewBean != null) {
			selectedResourceId = resourceId;
			selectedResourceViewBean = resourceViewBean;

			// We prepare the tree according to the selected resource type
			if (resourceViewBean instanceof SoftwareViewBean) {
				prepareTree("software");
			}
			else if (resourceViewBean instanceof SoftwareDocumentationViewBean) {
				prepareTree("softwareDocumentation");
			}
			else if (resourceViewBean instanceof EquipmentViewBean) {
				prepareTree("equipment");
			}
			else if (resourceViewBean instanceof DocumentaryAndPedagogicalResourceViewBean) {
				prepareTree("pedagogicalAndDocumentaryResources");
			}
			else if (resourceViewBean instanceof ProfessionalTrainingViewBean) {
				prepareTree("professionalTraining");
			}

			// We select the node of the selected resource and expand the tree
			// to make it visible
			TreeNode node = resourcesTreeBean.getNodeWithId(resourceId);
			if (node != null) {
				node.setSelected(true);
				resourcesTreeBean.expandOnlyOneNode(node);
			}

			// We initialize the "favoriteResource" attribute of the
			// resourceViewBean (it is not available on the domainBean,
			// because having a Ressource.getUsersHavingThisAsFavoriteResource
			// could potentially lead to a huge set).
			/*-
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean) getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteResourceViewBeans().contains(resourceViewBean)) {
					resourceViewBean.setFavoriteResource(true);
				}
				else {
					resourceViewBean.setFavoriteResource(false);
				}
			}
			*/

			/*-
			Set<Long> idsOfPedagogicalAnswers = new HashSet<Long>();
			for (ResourceCategory resourceCategory : resourceViewBean.getDomainBean().getCategories()) {
				for (Reponse answer : resourceCategory.getAnswers()) {
					idsOfPedagogicalAnswers.add(answer.getId());
				}
				pedagogicalUsesTreeBean.retainLeavesAndParents(idsOfPedagogicalAnswers);
			}
			*/
		}
		else {
			logger.error("Could not find the resource with id = {}", resourceId);
		}
		logger.info("Leaving setSelectedResourceId, resourceId = {}", resourceId);
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public TreeNode getResourcesTreeRoot() {
		return resourcesTreeBean.getRoot();
	}

	public TreeNode getSelectedNode() {
		return resourcesTreeSelectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (this.resourcesTreeSelectedNode != null) {
			this.resourcesTreeSelectedNode.setSelected(false);
		}
		resourcesTreeBean.expandOnlyOneNode(selectedNode);
		this.resourcesTreeSelectedNode = selectedNode;
	}

	public List<ToolCategoryViewBean> getAllCategoryViewBeans() {
		List<ToolCategoryViewBean> allCategoryViewBeans = new ArrayList<ToolCategoryViewBean>(toolCategoryViewBeans.values());
		Collections.sort(allCategoryViewBeans);
		return allCategoryViewBeans;
	}

	public List<ToolCategoryViewBean> getCategoryViewBeansForSelectedResourceType() {
		return categoryViewBeansByResourceType.get(selectedResourceType);
	}

	public void createResourceForSelectedResourceType(ResourceCategory category, String name, String iconFileName) {
		logger.info("createResourceForSelectedResourceType");
		resourcesService.createResource(selectedResourceType, category, name, iconFileName);
	}

	/*-
	public void onLabelSave(EditableTreeBean.TreeNodeData treeNodeData) {
		if (treeNodeData.getConcept().equals("resource")) {
			//resourcesService.saveResourceName(treeNodeData.getId(), treeNodeData.getLabel());
		}
		else if (treeNodeData.getConcept().equals("category")) {
			resourcesService.saveCategoryName(treeNodeData.getId(), treeNodeData.getLabel());
		}
	}
	 */

	public void onNodeSelect() {
		logger.info("Entering onNodeSelect");
		//setSelectedResourceId(((TreeNodeData) resourcesTreeSelectedNode.getData()).getId());
		setSelectedToolCategoryId(((TreeNodeData) resourcesTreeSelectedNode.getData()).getId());
		logger.info("Leaving onNodeSelect");
	}

	public void onSelectedToolCategorySave() {
		logger.info("onSelectedToolCategorySave");
		selectedToolCategoryViewBean.setResourceCategory(resourcesService.updateResourceCategory(selectedToolCategoryViewBean.getResourceCategory()));
	}
	
	public TreeNode getPedagogicalUsesTreeRoot() {
		return pedagogicalUsesTreeBean.getRoot();
	}

	private void setPedagogicalUsesTreeRoot(ResourceCategory resourceCategory) {
		// Set<Long> idsOfLeavesToKeep =
		// resourcesService.retrievePedagogicalNeedsAndAnswersAssociatedWithResourceCategory(resourceCategory.getId());
		Set<Long> idsOfLeavesToKeep = new HashSet<Long>();
		for (Reponse answer : resourceCategory.getAnswers()) {
			idsOfLeavesToKeep.add(answer.getId());
			logger.info("answer found = {}",answer.getId());
		}
		pedagogicalUsesTreeBean.retainLeavesAndParents(idsOfLeavesToKeep);
	}

	public List<Scenario> getScenariosUsingSelectedToolCategory() {
		return new ArrayList<Scenario>(resourcesService.retrieveScenariosAssociatedWithResourceCategory(selectedToolCategoryId));
	}

	private ResourceViewBean getResourceViewBean(Long id) {
		ResourceViewBean resourceViewBean = null;
		if (resourceViewBeans.containsKey(id)) {
			logger.info("resourceViewBean found in resourceViewBeans map, so we don't reload it.");
			resourceViewBean = resourceViewBeans.get(id);
		}
		else {
			Ressource resource = resourcesService.retrieveResource(id);
			if (resource != null) {
				if (resource instanceof Applicatif) {
					resourceViewBean = new SoftwareViewBean((Applicatif) resource);
				}
				else if (resource instanceof DocumentationApplicatif) {
					resourceViewBean = new SoftwareDocumentationViewBean((DocumentationApplicatif) resource);
				}
				else if (resource instanceof Equipement) {
					resourceViewBean = new EquipmentViewBean((Equipement) resource);
				}
				else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
					resourceViewBean = new DocumentaryAndPedagogicalResourceViewBean(
							(RessourcePedagogiqueEtDocumentaire) resource);
				}
				else if (resource instanceof FormationProfessionnelle) {
					resourceViewBean = new ProfessionalTrainingViewBean((FormationProfessionnelle) resource);
				}
				else {
					logger.error("The given resource, with id={}, has an unknown type!", id);
				}
				resourceViewBeans.put(id, resourceViewBean);
			}
			else {
				logger.error("There is no resource with id={}", id);
			}
		}
		return resourceViewBean;
	}

	public ToolCategoryViewBean getToolCategoryViewBean(Long id) {
		ToolCategoryViewBean toolCategoryViewBean = null;
		if (toolCategoryViewBeans.containsKey(id)) {
			logger.info("toolCategoryViewBean found in toolCategoryViewBeans map, so we don't reload it.");
			toolCategoryViewBean = toolCategoryViewBeans.get(id);
		}
		else {
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(id);
			if (toolCategory != null) {
				toolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
				toolCategoryViewBeans.put(id, toolCategoryViewBean);
			}
			else {
				logger.error("There is no category with id={}", id);
			}
		}
		return toolCategoryViewBean;
	}	
}