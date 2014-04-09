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
import java.util.List;
import java.util.Set;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("resourcesTreeController")
@Scope("view")
public class ResourcesTreeController extends AbstractContextAwareController {

	private static final long serialVersionUID = -5663154564837226988L;

	private static final Logger logger = LoggerFactory.getLogger(ResourcesTreeController.class);

	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private static final String TREE_NODE_TYPE_RESOURCE = "ResourceNode";

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private ResourcesSelectedResourceController resourcesSelectedResourceController;

	@Autowired
	private EditableTreeBean resourcesTreeBean;

	private TreeNode selectedNode;

	private String selectedResourceType;

	private Long selectedResourceId;

	private List<String> categoriesForSelectedResourceType;

	// private Map<Long, ResourceViewBean> resourceViewBeans;

	public ResourcesTreeController() {
		// resourceViewBeans = new HashMap<Long, ResourceViewBean>();
	}

	/*-
	@PostConstruct
	public void initResourcesTreeController() {
		Collection<Ressource> resources = resourcesService.g 
		resourceViewBeans.put(key, value)
	}
	 */

	@SuppressWarnings("unchecked")
	public void prepareTree(String resourceType) {
		logger.info("prepareTree for resourceType={}", resourceType);
		selectedResourceType = resourceType;
		setCategoriesForSelectedResourceType(resourcesService.getCategoriesForResourceType(resourceType));
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
		for (String category : categoriesForSelectedResourceType) {
			TreeNode categoryNode = resourcesTreeBean.addChild(getTreeNodeType_CATEGORY(), resourcesTreeBean
					.getVisibleRoots().get(0), -1L, category, "category");
			Collection<Ressource> entriesForCategory = null;
			switch (resourceType) {
			case "software":
				entriesForCategory = (Collection<Ressource>) resourcesService.getSoftwaresWithCategory(category);
				break;
			case "softwareDocumentation":
				entriesForCategory = (Collection<Ressource>) resourcesService.getSoftwareDocumentationsWithCategory(category);
				break;
			case "equipment":
				entriesForCategory = (Collection<Ressource>) resourcesService.getEquipmentWithCategory(category);
				break;
			case "pedagogicalAndDocumentaryResources":
				entriesForCategory = (Collection<Ressource>) resourcesService.getPedagogicalAndDocumentaryResourcesWithCategory(category);
				break;
			case "professionalTraining":
				entriesForCategory = (Collection<Ressource>) resourcesService.getProfessionalTrainingsWithCategory(category);
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

	public Long getSelectedResourceId() {
		return selectedResourceId;
	}

	public void setSelectedResourceId(Long resourceId) {
		logger.info("setSelectedResourceId({})", resourceId);
		this.selectedResourceId = resourceId;
		Ressource resource = resourcesService.getResource(resourceId);
		if (resource instanceof Applicatif) {
			prepareTree("software");
		}
		else if (resource instanceof DocumentationApplicatif) {
			prepareTree("softwareDocumentation");
		}
		else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
			prepareTree("pedagogicalAndDocumentaryResources");
		}
		else if (resource instanceof Equipement) {
			prepareTree("equipment");
		}
		else if (resource instanceof FormationProfessionnelle) {
			prepareTree("professionalTraining");
		}
		TreeNode node = resourcesTreeBean.getNodeWithId(resourceId);
		if (node != null) {
			node.setSelected(true);
			resourcesTreeBean.expandOnlyOneNode(node);

			resourcesSelectedResourceController.setSelectedResourceId(resourceId);
		}
		else {
			logger.info("setSelectedResourceId - no TreeNode found for id={}", resourceId);
		}
	}

	public TreeNode getTreeRoot() {
		return resourcesTreeBean.getRoot();
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
		}
		resourcesTreeBean.expandOnlyOneNode(selectedNode);
		this.selectedNode = selectedNode;
	}

	public List<String> getCategoriesForSelectedResourceType() {
		return categoriesForSelectedResourceType;
	}

	public void setCategoriesForSelectedResourceType(Collection<String> categoriesForSelectedResourceType) {
		if (categoriesForSelectedResourceType instanceof Set) {
			this.categoriesForSelectedResourceType = new ArrayList<String>(categoriesForSelectedResourceType);
		}
		else {
			this.categoriesForSelectedResourceType = (List<String>) categoriesForSelectedResourceType;
		}
		Collections.sort(this.categoriesForSelectedResourceType);
	}

	public void createResourceForSelectedResourceType(String category, String name) {
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
		setSelectedResourceId(((TreeNodeData) selectedNode.getData()).getId());
	}

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public String getTreeNodeType_RESOURCE() {
		return TREE_NODE_TYPE_RESOURCE;
	}

}