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

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;

/**
 * @author Grégoire Colbert @since 2014-02-19
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
	private EditableTreeBean editableTreeBean;
	
	private TreeNode selectedNode;
	
	public ResourcesTreeController() {
	}
	
	@PostConstruct
	public void initTree() {
		logger.info("entering initTree");
		editableTreeBean.addVisibleRoot("Applicatifs");
		editableTreeBean.addVisibleRoot("Documentations d'applicatifs");

		/*
		List<Ressource> resources = resourcesService.getResourcesTypes();
		logger.info("Found {} resource types.", resources.size());
		for (Resource resource : resources) {
			logger.info("resource = {}", resource.getName());
			createTree(need, editableTreeBean.getVisibleRoot());
		}
		*/

		//editableTreeBean.getVisibleRoots().get(0).setExpanded(true);
		logger.info("leaving initTree");
		logger.info("------");
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
}