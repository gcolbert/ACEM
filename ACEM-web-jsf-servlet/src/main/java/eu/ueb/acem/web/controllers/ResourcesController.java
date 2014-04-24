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
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;

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
	private Map<String, List<ResourceCategory>> categoriesByResourceType;
	private List<ResourceCategory> allCategories;
	
	private Long selectedResourceId;
	private Map<Long, ResourceViewBean> resourceViewBeans;
	private ResourceViewBean selectedResourceViewBean;

	//@Autowired
	private EditableTreeBean pedagogicalUsesTreeBean;

	public ResourcesController() {
		resourceViewBeans = new HashMap<Long, ResourceViewBean>();
	}

	@PostConstruct
	public void initResourcesController() {
		logger.info("entering initResourcesController");
		pedagogicalUsesTreeBean = needsAndAnswersTreeController.getNeedsAndAnswersTreeBean();

		categoriesByResourceType = new HashMap<String, List<ResourceCategory>>();
		allCategories = new ArrayList<ResourceCategory>();
		for (String resourceType : RESOURCE_TYPES) {
			categoriesByResourceType.put(resourceType,
					new ArrayList<ResourceCategory>(resourcesService.retrieveCategoriesForResourceType(resourceType)));
			Collections.sort(categoriesByResourceType.get(resourceType));
			allCategories.addAll(categoriesByResourceType.get(resourceType));
		}
		Collections.sort(allCategories);

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
			for (ResourceCategory category : categoriesByResourceType.get(resourceType)) {
				logger.info("category = {}", category.getName());
				TreeNode categoryNode = resourcesTreeBean.addChild(getTreeNodeType_CATEGORY(), resourcesTreeBean
						.getVisibleRoots().get(0), category.getId(), category.getName(), "category");
				Collection<Ressource> entriesForCategory = null;
				switch (resourceType) {
				case "software":
					entriesForCategory = (Collection<Ressource>) resourcesService.retrieveSoftwaresWithCategory(category);
					break;
				case "softwareDocumentation":
					entriesForCategory = (Collection<Ressource>) resourcesService.retrieveSoftwareDocumentationsWithCategory(category);
					break;
				case "equipment":
					entriesForCategory = (Collection<Ressource>) resourcesService.retrieveEquipmentWithCategory(category);
					break;
				case "pedagogicalAndDocumentaryResources":
					entriesForCategory = (Collection<Ressource>) resourcesService.retrievePedagogicalAndDocumentaryResourcesWithCategory(category);
					break;
				case "professionalTraining":
					entriesForCategory = (Collection<Ressource>) resourcesService.retrieveProfessionalTrainingsWithCategory(category);
					break;
				default:
					logger.error("Unknown resourceType '{}'", resourceType);
					break;
				}
				for (Ressource entity : entriesForCategory) {
					resourcesTreeBean.addChild(getTreeNodeType_RESOURCE(), categoryNode, entity.getId(), entity.getName(),
							resourceType);
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
		if (! resourceViewBeans.containsKey(resourceId)) {
			Ressource resource = resourcesService.retrieveResource(resourceId);
			if (resource != null) {
				this.selectedResourceId = resourceId;
				if (resource instanceof Applicatif) {
					prepareTree("software");
					selectedResourceViewBean = new SoftwareViewBean();
				}
				else if (resource instanceof DocumentationApplicatif) {
					prepareTree("softwareDocumentation");
					selectedResourceViewBean = new SoftwareDocumentationViewBean();
				}
				else if (resource instanceof Equipement) {
					prepareTree("equipment");
					selectedResourceViewBean = new EquipmentViewBean();
				}
				else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
					prepareTree("pedagogicalAndDocumentaryResources");
					selectedResourceViewBean = new DocumentaryAndPedagogicalResourceViewBean();
				}
				else if (resource instanceof FormationProfessionnelle) {
					prepareTree("professionalTraining");
					selectedResourceViewBean = new ProfessionalTrainingViewBean();
				}

				selectedResourceViewBean.setDomainBean(resource);
				resourceViewBeans.put(resourceId, selectedResourceViewBean);

				TreeNode node = resourcesTreeBean.getNodeWithId(resourceId);
				if (node != null) {
					node.setSelected(true);
					resourcesTreeBean.expandOnlyOneNode(node);
				}

				// We initialize the "favoriteResource" attribute (the value depends of the current user)
				try {
					if (getCurrentUser() instanceof Enseignant) {
						Enseignant teacher = (Enseignant)getCurrentUser();
						selectedResourceViewBean.setFavoriteResource(teacher.getFavoriteResources().contains(selectedResourceViewBean.getDomainBean()));
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				logger.error("Unknown resource with id={}", resourceId);
			}
		}
		else {
			logger.info("resourceViewBean found in resourceViewBeans map, so we don't reload it.");
			this.selectedResourceId = resourceId;
			selectedResourceViewBean = resourceViewBeans.get(resourceId);
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

	public List<ResourceCategory> getAllCategories() {
		return allCategories;
	}

	public List<ResourceCategory> getCategoriesForSelectedResourceType() {
		return categoriesByResourceType.get(selectedResourceType);
	}

	public void createResourceForSelectedResourceType(ResourceCategory category, String name) {
		logger.info("createResourceForSelectedResourceType");
		resourcesService.createResource(selectedResourceType, category, name);
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

}