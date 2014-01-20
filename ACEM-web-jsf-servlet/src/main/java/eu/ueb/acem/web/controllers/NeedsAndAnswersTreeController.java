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
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.Menu;

/**
 * @author gcolbert @since 2013-11-20
 * 
 */
@Controller("needsAndAnswersTreeController")
@Scope("view")
public class NeedsAndAnswersTreeController extends
		AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(NeedsAndAnswersTreeController.class);

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
		// editableTreeBean.setVisibleRootLabel(getString("NEEDS_AND_ANSWERS.TREE.ROOT.LABEL"));
		editableTreeBean.setVisibleRootLabel("Besoins");
		Set<Besoin> needs = needsAndAnswersService.getChildrenNeedsOf(null);
		logger.info("Found {} needs at root of tree.", needs.size());
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
	private void createTree(Besoin need, TreeNode rootNode) {
		// We create the root node
		TreeNode newNode = new DefaultTreeNode(new Menu(need.getId(), need.getName(), "Need"), rootNode);
		// We look for children and recursively create them too
		Set<Besoin> childrenNodes = needsAndAnswersService.getChildrenNeedsOf(need);
		if (childrenNodes.size() > 0) {
			for (Besoin besoinChild : childrenNodes) {
				createTree(besoinChild, newNode);
			}
		}
		// We look for answers only if "need" doesn't have any child
		// NOTE : this is a business-level constraint, technically there should
		// no problem with displaying Needs and Answers for the same need.
		else {
			Set<Reponse> answers = needsAndAnswersService.getAnswers(need);
			need.setAnswers(answers);
			for (Reponse answer : answers) {
				new DefaultTreeNode(new Menu(answer.getId(), answer.getName(), "Answer"), newNode);
			}
		}
	}

	public TreeNode getTreeRoot() {
		return editableTreeBean.getRoot();
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/*-
	public void onNodeSelect(NodeSelectEvent event) {
		logger.info("onNodeSelect");
		FacesMessage message = null;
		if (selectedNode != null) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Was selected", selectedNode.toString());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		setSelectedNode(event.getTreeNode());
		if (selectedNode != null) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Is now selected", selectedNode.toString());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	*/

	/*-
	public void setSelectedNode(TreeNode selectedNode) {
		logger.info("entering setSelectedNode({})", selectedNode);
		if (this.selectedNode != null) {
			if (this.selectedNode != selectedNode) {
				if (this.selectedNode.isSelected()) {
					this.selectedNode.setSelected(false);
				}
				if (! this.selectedNode.getChildren().contains(selectedNode)) {
					// We recursively collapse the children of this.selectedNode
					collapseChildrenOf(this.selectedNode);
					// We collapse the currently selected node itself
					this.selectedNode.setExpanded(false);
					// We collect in a Set the parents of the newly selected node
					Set<TreeNode> selectedNodeParents = new HashSet<TreeNode>();
					TreeNode selectedNodeParent = selectedNode.getParent();
					while (selectedNodeParent != null) {
						selectedNodeParents.add(selectedNodeParent);
						selectedNodeParent = selectedNodeParent.getParent();
					}
					// We collapse all of this.selectedNode parents, until we find the grand-parent
					// that has the currently selected node and the newly selected node as grand-children
					TreeNode currentParent = this.selectedNode.getParent();
					while ((currentParent != null)
							&& (! selectedNodeParents.contains(currentParent))) {
						currentParent.setExpanded(false);
						currentParent = currentParent.getParent();
					}
				}
			}
		}
		if (this.selectedNode == selectedNode) {
			if (this.selectedNode.getChildCount() > 0) {
				this.selectedNode.setExpanded(! this.selectedNode.isExpanded());
			}
			else {
				if (! selectedNode.isSelected()) {
					selectedNode.setSelected(true);
					selectedNode.setExpanded(true);
				}
		        this.selectedNode = selectedNode;
			}
			this.selectedNode = selectedNode;
		}
		logger.info("leaving setSelectedNode({})", selectedNode);
		logger.info("------");
	}
	 */
	
	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();
	
		needsAndAnswersService.changeParentOfNeed(
				((Menu) dragNode.getData()).getId(),
				((Menu) dropNode.getData()).getId());
	
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Dragged " + dragNode.getData(), "Dropped on "
						+ dropNode.getData() + " at " + dropIndex);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private void collapseChildrenOf(TreeNode node) {
		logger.info("entering collapseChildrenOf, node={}", (Menu)node.getData());
		for (TreeNode child : node.getChildren()) {
			child.setExpanded(false);
			if (!child.isLeaf()) {
				collapseChildrenOf(child);
			}
		}
		logger.info("leaving collapseChildrenOf, node={}", (Menu)node.getData());
	}

	public void displaySelectedSingle() {
		if (selectedNode != null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Selected", selectedNode.getData().toString());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void associateNeedWithSelectedNode() {
		logger.info("entering addChildToSelectedNode, selectedNode={}",
				(Menu)selectedNode.getData());
		collapseChildrenOf(selectedNode);
		TreeNode newNode = editableTreeBean.addChild(selectedNode, null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_NEED_LABEL"),
				"Need");
		setSelectedNode(newNode);
		saveSelectedNode();
		logger.info("leaving addChildToSelectedNode, selectedNode={}",
				 (Menu)selectedNode.getData());
		logger.info("------");
	}

	public void associateAnswerWithSelectedNode() {
		logger.info("entering associateAnswerToSelectedNode, selectedNode={}",
				(Menu)selectedNode.getData());
		TreeNode newNode = editableTreeBean.addChild(selectedNode, null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_ANSWER_LABEL"),
				"Answer");
		setSelectedNode(newNode);
		saveSelectedNode();
		logger.info("leaving associateAnswerToSelectedNode, selectedNode={}",
				(Menu)selectedNode.getData());
		logger.info("------");
	}

	public void saveSelectedNode() {
		if (selectedNode != null) {
			logger.info("entering saveSelectedNode, selectedNode={}",
					(Menu)selectedNode.getData());
			switch (((Menu) selectedNode.getData()).getConcept()) {
				case "Need" :
					Besoin savedNeed;
					if ((selectedNode.getParent() != null)
							&& (selectedNode.getParent().getData() != null)) {
						savedNeed = needsAndAnswersService.createOrUpdateNeed(
								((Menu) selectedNode.getData()).getId(),
								((Menu) selectedNode.getData()).getLabel(),
								((Menu) selectedNode.getParent().getData()).getId());
					}
					else {
						savedNeed = needsAndAnswersService.createOrUpdateNeed(
								((Menu) selectedNode.getData()).getId(),
								((Menu) selectedNode.getData()).getLabel(), null);
					}
					((Menu) selectedNode.getData()).setId(savedNeed.getId());
				break;
				case "Answer" :
					Reponse savedAnswer;
					if (((Menu) selectedNode.getParent().getData()).getConcept() == "Need") {
						savedAnswer = needsAndAnswersService.createOrUpdateAnswer(
								((Menu) selectedNode.getData()).getId(),
								((Menu) selectedNode.getData()).getLabel(),
								((Menu) selectedNode.getParent().getData()).getId());
						((Menu) selectedNode.getData()).setId(savedAnswer.getId());
					}
					else {
						logger.info("Cannot save selectedNode as Answer : parent node is not a Need");
					}
				break;
				default:
					logger.info("selectedNode has an unknown concept value: {}",
							((Menu) selectedNode.getData()).getConcept());
				break;
			}
		} else {
			logger.info("entering saveSelectedNode, selectedNode=null, we do nothing.");
		}
		logger.info("leaving saveSelectedNode");
		logger.info("------");
	}

	public void deleteSelectedNode() {
		logger.info("entering deleteSelectedNode, selectedNode={}",
				(Menu)selectedNode.getData());
		if (selectedNode != null) {
			if (selectedNode.getChildCount() == 0) {
				needsAndAnswersService.deleteNeed(((Menu) selectedNode
						.getData()).getId());
				selectedNode.getChildren().clear();
				selectedNode.getParent().getChildren().remove(selectedNode);
				selectedNode.setParent(null);
				this.selectedNode = null;
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
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

}
