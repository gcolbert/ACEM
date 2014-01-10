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

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.Menu;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Controller("needsAndAnswersTreeController")
@Scope("view")
public class NeedsAndAnswersTreeController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTreeController.class);

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;

	@Autowired
	private EditableTreeBean editableTreeBean;

	private TreeNode selectedNode;
	
	public NeedsAndAnswersTreeController() {
	}

    @PostConstruct
    public void initTree() {
    	logger.info("entering initTree");
    	editableTreeBean.setVisibleRootLabel(getString("NEEDS_AND_ANSWERS.TREE.ROOT.LABEL"));
		Set<Besoin> needs = needsAndAnswersService.getNeedsWithParent(null);
		logger.info("[NeedsAndAnswersTreeController.initTree] Fetched {} pedagogical needs at root of tree.", needs.size());
		for (Besoin need : needs) {
			logger.info("need = {}", need.getName());
	    	createTree(need, editableTreeBean.getVisibleRoot());
		}
		editableTreeBean.getVisibleRoot().setExpanded(true);
    	logger.info("leaving initTree");
		logger.info("------");
    }

    /**
     * Recursive function to construct Tree
     */
    public void createTree(Besoin besoin, TreeNode rootNode) {
        TreeNode newNode = new DefaultTreeNode(new Menu(besoin.getId(), besoin.getName()), rootNode);
        Set<Besoin> childrenNodes = needsAndAnswersService.getNeedsWithParent(besoin);
        for (Besoin besoinChild : childrenNodes) {
            createTree(besoinChild, newNode);
        }
    }
    
	public EditableTreeBean getTreeBean() {
		return editableTreeBean;
	}
	
	public TreeNode getTreeRoot() {
		return editableTreeBean.getRoot();
	}

    public TreeNode getSelectedNode() {  
        return selectedNode;  
    }
  
    public void setSelectedNode(TreeNode selectedNode) {
    	logger.info("entering setSelectedNode({})", selectedNode);
    	if (this.selectedNode != null) {
	        if (this.selectedNode.isSelected()) {
	        	this.selectedNode.setSelected(false);
	        }
    	}
        this.selectedNode = selectedNode;
    	if (this.selectedNode != null) {
	        if (! this.selectedNode.isSelected()) {
	        	this.selectedNode.setSelected(true);
	        }
    	}
    	logger.info("leaving setSelectedNode({})", selectedNode);
		logger.info("------");
    }

    /*
    public void onNodeSelect(NodeSelectEvent event) {
    	logger.info("onNodeSelect");
    	setSelectedNode(event.getTreeNode());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);      	
    }
    */
    
    public void displaySelectedSingle() {  
    	if(selectedNode != null) {  
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
    		FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }

    private void collapseChildrenOf(TreeNode node) {
    	logger.info("entering collapseChildrenOf, node={}", node.getData().toString());
    	for (TreeNode child : node.getChildren()) {
    		child.setExpanded(false);
    		if (! child.isLeaf()) {
    			collapseChildrenOf(child);
    		}
    	}
    	logger.info("leaving collapseChildrenOf, node={}", node.getData().toString());
		logger.info("------");
    }

    public void addChildToSelectedNode() {
    	logger.info("entering addChildToSelectedNode, selectedNode={}", selectedNode.getData().toString());
    	collapseChildrenOf(selectedNode);
    	TreeNode newNode = editableTreeBean.addChild(selectedNode, null, "Nouveau");
    	setSelectedNode(newNode);
    	saveSelectedNode();
    	logger.info("leaving addChildToSelectedNode, selectedNode={}", selectedNode.getData().toString());
		logger.info("------");
    }

    public void addAnswerToSelectedNode() {
    	logger.info("entering addAnswerToSelectedNode, selectedNode={}", selectedNode.getData().toString());
    	
    	logger.info("leaving addAnswerToSelectedNode, selectedNode={}", selectedNode.getData().toString());
		logger.info("------");
    }

	public void saveSelectedNode() {
		if (selectedNode != null) {
			logger.info("entering saveSelectedNode, selectedNode={}", selectedNode.getData().toString());
			// Si selectedNode est un Besoin
			Besoin savedBesoin;
			if ((selectedNode.getParent() != null) && (selectedNode.getParent().getData() != null)) {
				savedBesoin = needsAndAnswersService.createOrUpdateNeed(((Menu)selectedNode.getData()).getId(),
											((Menu) selectedNode.getData()).getLabel(),
											((Menu) selectedNode.getParent().getData()).getId());
			}
			else {
				savedBesoin = needsAndAnswersService.createOrUpdateNeed(((Menu)selectedNode.getData()).getId(),
											((Menu) selectedNode.getData()).getLabel(),
											null);
			}
			((Menu)selectedNode.getData()).setId(savedBesoin.getId());
			// TODO sinon si selectedNode est une Reponse
		}
		else {
			logger.info("entering saveSelectedNode, selectedNode=null, we do nothing.");
		}
		logger.info("leaving saveSelectedNode");
		logger.info("------");
	}

    public void deleteSelectedNode() {
		logger.info("entering deleteSelectedNode, selectedNode={}", selectedNode.getData().toString());
		if (selectedNode != null) {
        	if (selectedNode.getChildCount() == 0) {
        		needsAndAnswersService.deleteNeed(((Menu) selectedNode.getData()).getId());
		    	selectedNode.getChildren().clear();
		    	selectedNode.getParent().getChildren().remove(selectedNode);  
		    	selectedNode.setParent(null);
		    	this.selectedNode = null;
	    	}
	    	else {
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
        				getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.TITLE"),
        				getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.DETAILS"));
        		RequestContext.getCurrentInstance().update("messages");
        		FacesContext.getCurrentInstance().addMessage(null, message);
	    		logger.info("The selected node has children, cannot delete!");
	    	}
    	}
		logger.info("leaving deleteSelectedNode");
		logger.info("------");
    }
    
    public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		String dropNodeData = null;
		if (dropNode != null) {
			dropNodeData = dropNode.getData().toString();
		}
		int dropIndex = event.getDropIndex();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNodeData + " at " + dropIndex);
        FacesContext.getCurrentInstance().addMessage(null, message);
        if (dropNode != null) {
        	needsAndAnswersService.changeParentOfNeed(((Menu)event.getDragNode().getData()).getId(),
        		                                      ((Menu)event.getDropNode().getData()).getId());
        }
        else {
        	//besoinsReponsesService.changePositionOfNeed(((Menu)event.getDragNode().getData()).getId(),null);
        }
    }  

}
