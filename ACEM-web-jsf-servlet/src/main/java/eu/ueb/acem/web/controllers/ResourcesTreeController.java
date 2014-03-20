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

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private ResourcesSelectedResourceController resourcesSelectedResourceController;

	@Autowired
	private EditableTreeBean editableTreeBean;

	private TreeNode selectedNode;

	private String selectedResourceType;
	
	private Long selectedResourceId;
	
	private List<String> categoriesForSelectedResourceType;

	//private Map<Long, ResourceViewBean> resourceViewBeans;

	public ResourcesTreeController() {
		//resourceViewBeans = new HashMap<Long, ResourceViewBean>();
	}

	/*-
	@PostConstruct
	public void initResourcesTreeController() {
		Collection<Ressource> resources = resourcesService.g 
		resourceViewBeans.put(key, value)
	}
	*/

	public void prepareTree(String resourceType) {
		logger.info("prepareTree for resourceType={}", resourceType);
		selectedResourceType = resourceType;
		setCategoriesForSelectedResourceType(resourcesService.getCategoriesForResourceType(resourceType));
		editableTreeBean.reset();
		switch (resourceType) {
		case "software":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL"));
			for (String category : categoriesForSelectedResourceType) {
				TreeNode categoryNode = editableTreeBean.addChild(editableTreeBean.getVisibleRoots().get(0), -1L,
						category, "category");
				Collection<Applicatif> entities = resourcesService.getSoftwaresWithCategory(category);
				for (Ressource entity : entities) {
					editableTreeBean.addChild(categoryNode, entity.getId(), entity.getName(), resourceType);
				}
			}
			break;
		case "softwareDocumentation":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL"));
			for (String category : categoriesForSelectedResourceType) {
				TreeNode categoryNode = editableTreeBean.addChild(editableTreeBean.getVisibleRoots().get(0), -1L,
						category, "category");
				Collection<DocumentationApplicatif> entities = resourcesService
						.getSoftwareDocumentationsWithCategory(category);
				for (Ressource entity : entities) {
					editableTreeBean.addChild(categoryNode, entity.getId(), entity.getName(), resourceType);
				}
			}
			break;
		case "equipment":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL"));
			for (String category : categoriesForSelectedResourceType) {
				TreeNode categoryNode = editableTreeBean.addChild(editableTreeBean.getVisibleRoots().get(0), -1L,
						category, "category");
				Collection<Equipement> entities = resourcesService.getEquipmentWithCategory(category);
				for (Ressource entity : entities) {
					editableTreeBean.addChild(categoryNode, entity.getId(), entity.getName(), resourceType);
				}
			}
			break;
		case "pedagogicalAndDocumentaryResources":
			editableTreeBean
					.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL"));
			for (String category : categoriesForSelectedResourceType) {
				TreeNode categoryNode = editableTreeBean.addChild(editableTreeBean.getVisibleRoots().get(0), -1L,
						category, "category");
				Collection<RessourcePedagogiqueEtDocumentaire> entities = resourcesService
						.getPedagogicalAndDocumentaryResourcesWithCategory(category);
				for (Ressource entity : entities) {
					editableTreeBean.addChild(categoryNode, entity.getId(), entity.getName(), resourceType);
				}
			}
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
		editableTreeBean.getVisibleRoots().get(0).setExpanded(true);
	}

	public Long getSelectedResourceId() {
		return selectedResourceId;
	}
	
	public void setSelectedResourceId(Long resourceId) {
		this.selectedResourceId = resourceId;
		TreeNode node = editableTreeBean.getNodeWithId(resourceId);
		if (node != null) {
			node.setSelected(true);
			editableTreeBean.expandOnlyOneNode(node);
			
			resourcesSelectedResourceController.setSelectedResourceId(resourceId);
		}
	}
	
	public TreeNode getTreeRoot() {
		return editableTreeBean.getRoot();
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
		}
		editableTreeBean.expandOnlyOneNode(selectedNode);
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
		resourcesSelectedResourceController.setSelectedResourceId(((TreeNodeData) selectedNode.getData()).getId());
	}

}