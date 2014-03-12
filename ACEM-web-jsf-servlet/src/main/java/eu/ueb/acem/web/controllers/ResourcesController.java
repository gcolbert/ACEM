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

import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;

/**
 * @author Grégoire Colbert @since 2014-02-19
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
	private EditableTreeBean editableTreeBean;

	private TreeNode selectedNode;

	private String selectedResourceType;

	private List<String> categoriesForSelectedResourceType;

	public ResourcesController() {
	}

	public void prepareTree(String resourceType) {
		logger.info("prepareTree for resourceType={}", resourceType);
		selectedResourceType = resourceType;
		setCategoriesForSelectedResourceType(resourcesService.getCategoriesForResourceType(resourceType));
		editableTreeBean.reset();
		switch (resourceType) {
		case "software":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL"));
			break;
		case "softwareDocumentation":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL"));
			break;
		case "professionalTraining":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL"));
			break;
		case "equipment":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL"));
			break;
		case "pedagogicalAndDocumentaryResources":
			editableTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL"));
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
		editableTreeBean.getVisibleRoots().get(0).setExpanded(true);
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
			editableTreeBean.expandOnlyOneNode(selectedNode);
		}
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
	
}