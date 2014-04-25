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
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
	private ResourcesService resourcesService;

	@Autowired
	private NeedsAndAnswersController needsAndAnswersTreeController;

	@Autowired
	private EditableTreeBean resourcesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private static final String TREE_NODE_TYPE_RESOURCE = "ResourceNode";
	private TreeNode resourcesTreeSelectedNode;

	private static final String[] RESOURCE_TYPES = { "software", "softwareDocumentation", "equipment",
			"pedagogicalAndDocumentaryResources", "professionalTraining" };
	private String selectedResourceType; // One of RESOURCE_TYPES
	private Map<String, List<ToolCategoryViewBean>> categoryViewBeansByResourceType;
	private List<ToolCategoryViewBean> allCategoryViewBeans;

	private Long selectedResourceId;
	private Map<Long, ResourceViewBean> resourceViewBeans;
	private ResourceViewBean selectedResourceViewBean;

	// @Autowired
	private EditableTreeBean pedagogicalUsesTreeBean;

	public ResourcesController() {
		resourceViewBeans = new HashMap<Long, ResourceViewBean>();
	}

	@PostConstruct
	public void initResourcesController() {
		logger.info("entering initResourcesController");
		pedagogicalUsesTreeBean = needsAndAnswersTreeController.getNeedsAndAnswersTreeBean();

		categoryViewBeansByResourceType = new HashMap<String, List<ToolCategoryViewBean>>();
		for (String resourceType : RESOURCE_TYPES) {
			categoryViewBeansByResourceType.put(resourceType, new ArrayList<ToolCategoryViewBean>());
			for (ResourceCategory resourceCategory : resourcesService.retrieveCategoriesForResourceType(resourceType)) {
				categoryViewBeansByResourceType.get(resourceType).add(new ToolCategoryViewBean(resourceCategory));
			}
			Collections.sort(categoryViewBeansByResourceType.get(resourceType));
		}

		allCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
		for (ResourceCategory resourceCategory : resourcesService.retrieveAllCategories()) {
			allCategoryViewBeans.add(new ToolCategoryViewBean(resourceCategory));
		}
		Collections.sort(allCategoryViewBeans);

		logger.info("leaving initResourcesController");
		logger.info("------");
	}

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public String getTreeNodeType_RESOURCE() {
		return TREE_NODE_TYPE_RESOURCE;
	}

	@SuppressWarnings("unchecked")
	public void prepareTree(String resourceType) {
		logger.info("prepareTree for resourceType={}", resourceType);
		if (selectedResourceType != resourceType) {
			selectedResourceType = resourceType;
			resourcesTreeBean.reset();
			switch (resourceType) {
			case "software":
				resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL"));
				break;
			case "softwareDocumentation":
				resourcesTreeBean
						.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL"));
				break;
			case "equipment":
				resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL"));
				break;
			case "pedagogicalAndDocumentaryResources":
				resourcesTreeBean
						.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL"));
				break;
			case "professionalTraining":
				resourcesTreeBean
						.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL"));
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
					entriesForCategory = (Collection<Ressource>) resourcesService
							.retrieveSoftwaresWithCategory(category.getDomainBean());
					break;
				case "softwareDocumentation":
					entriesForCategory = (Collection<Ressource>) resourcesService
							.retrieveSoftwareDocumentationsWithCategory(category.getDomainBean());
					break;
				case "equipment":
					entriesForCategory = (Collection<Ressource>) resourcesService
							.retrieveEquipmentWithCategory(category.getDomainBean());
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
				for (Ressource entity : entriesForCategory) {
					resourcesTreeBean.addChild(getTreeNodeType_RESOURCE(), categoryNode, entity.getId(),
							entity.getName(), resourceType);
				}
			}
			resourcesTreeBean.getVisibleRoots().get(0).setExpanded(true);
		}
	}

	public Long getSelectedResourceId() {
		return selectedResourceId;
	}

	public void setSelectedResourceId(Long resourceId) {
		logger.info("Entering setSelectedResourceId, resourceId = {}", resourceId);
		ResourceViewBean resourceViewBean = getResourceViewBean(resourceId);
		if (resourceViewBean != null) {
			selectedResourceId = resourceId;

			// We prepare the tree according to the selected resource type
			if (resourceViewBean.getDomainBean() instanceof Applicatif) {
				prepareTree("software");
			}
			else if (resourceViewBean.getDomainBean() instanceof DocumentationApplicatif) {
				prepareTree("softwareDocumentation");
			}
			else if (resourceViewBean.getDomainBean() instanceof Equipement) {
				prepareTree("equipment");
			}
			else if (resourceViewBean.getDomainBean() instanceof RessourcePedagogiqueEtDocumentaire) {
				prepareTree("pedagogicalAndDocumentaryResources");
			}
			else if (resourceViewBean.getDomainBean() instanceof FormationProfessionnelle) {
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
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean)getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteResourceViewBeans().contains(resourceViewBean)) {
					resourceViewBean.setFavoriteResource(true);
				}
				else {
					resourceViewBean.setFavoriteResource(false);
				}
			}
			selectedResourceViewBean = resourceViewBean;
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
		setSelectedResourceId(((TreeNodeData) resourcesTreeSelectedNode.getData()).getId());
		logger.info("Leaving onNodeSelect");
	}

	public TreeNode getPedagogicalUsesTreeRoot() {
		// TODO : trouver comment faire la requête :
		// MATCH
		// (r:Resource)<-[:categoryContains]-()<-[:answeredUsingResourceCategory]-(answer)<-[*]-(need)
		// where id(r)=151 RETURN r,answer,need;
		// pedagogicalUsesTreeBean.retainAll(selectedResourceId);
		return pedagogicalUsesTreeBean.getRoot();
	}

	public List<Scenario> getScenariosUsingSelectedTool() {
		return new ArrayList<Scenario>(resourcesService.retrieveScenariosAssociatedWithRessource(selectedResourceId));
	}

	public ResourceViewBean getResourceViewBean(Long id) {
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
				logger.error("Unknown resource with id={}", id);
			}
		}
		return resourceViewBean;
	}

}