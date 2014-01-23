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
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;

/**
 * @author Grégoire Colbert @since 2013-11-20
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

	private static final String TREE_NODE_TYPE_NEED_LEAF = "Need";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS = "NeedWithAssociatedNeeds";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS = "NeedWithAssociatedAnswers";
	private static final String TREE_NODE_TYPE_ANSWER_LEAF = "Answer";

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
		Set<Besoin> needs = needsAndAnswersService.getAssociatedNeedsOf(null);
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
		TreeNode newNode = new DefaultTreeNode(TREE_NODE_TYPE_NEED_LEAF,
				new TreeNodeData(need.getId(), need.getName(), "Need"),
				rootNode);
		// We look for children and recursively create them too
		Set<Besoin> associatedNeeds = needsAndAnswersService
				.getAssociatedNeedsOf(need);
		if (associatedNeeds.size() > 0) {
			((DefaultTreeNode) newNode)
			.setType(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS);
			for (Besoin besoinChild : associatedNeeds) {
				createTree(besoinChild, newNode);
			}
		}
		// We look for answers only if "need" doesn't have any child
		// NOTE : this is a business-level constraint, technically we could
		// display Needs and Answers as children of a Need node.
		else {
			Set<Reponse> answers = needsAndAnswersService
					.getAssociatedAnswersOf(need);
			if (answers.size() > 0) {
				((DefaultTreeNode) newNode)
				.setType(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS);
				need.setAnswers(answers);
				for (Reponse answer : answers) {
					new DefaultTreeNode(TREE_NODE_TYPE_ANSWER_LEAF,
							new TreeNodeData(answer.getId(), answer.getName(),
									"Answer"), newNode);
				}
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
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
			expandOnlyOneNode(selectedNode);
		}
		this.selectedNode = selectedNode;
	}

	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();
		TreeNodeData dragNodeData = (TreeNodeData) dragNode.getData();
		TreeNodeData dropNodeData = (TreeNodeData) dropNode.getData();

		needsAndAnswersService.changeParentOfNeed(dragNodeData.getId(),
				dropNodeData.getId());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Dragged " + dragNodeData, "Dropped on " + dropNodeData
				+ " at " + dropIndex);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onLabelSave(EditableTreeBean.TreeNodeData treeNodeData) {
		if (treeNodeData.getConcept().equals("Answer")) {
			needsAndAnswersService.saveAnswerName(treeNodeData.getId(),
					treeNodeData.getLabel());
		}
		else if (treeNodeData.getConcept().equals("Need")) {
			needsAndAnswersService.saveNeedName(treeNodeData.getId(),
					treeNodeData.getLabel());
		}
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
				(TreeNodeData) selectedNode.getData());
		TreeNode newNode = editableTreeBean.addChild(selectedNode, null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_NEED_LABEL"), "Need");
		((DefaultTreeNode) selectedNode).setType("NeedWithAssociatedNeeds");
		setSelectedNode(newNode);
		saveTreeNode(newNode);
		expandOnlyOneNode(newNode);
		logger.info("leaving addChildToSelectedNode, selectedNode={}",
				(TreeNodeData) selectedNode.getData());
		logger.info("------");
	}

	public void associateAnswerWithSelectedNode() {
		logger.info("entering associateAnswerToSelectedNode, selectedNode={}",
				(TreeNodeData) selectedNode.getData());
		TreeNode newNode = editableTreeBean.addChild(selectedNode, null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_ANSWER_LABEL"), "Answer");
		((DefaultTreeNode) selectedNode)
		.setType(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS);
		setSelectedNode(newNode);
		saveTreeNode(newNode);
		expandOnlyOneNode(newNode);
		logger.info("leaving associateAnswerToSelectedNode, selectedNode={}",
				(TreeNodeData) selectedNode.getData());
		logger.info("------");
	}

	private void saveTreeNode(TreeNode treeNode) {
		if (treeNode != null) {
			TreeNodeData treeNodeData = (TreeNodeData) treeNode.getData();
			logger.info("entering saveTreeNode({})", treeNodeData);
			TreeNodeData parentNodeData = (TreeNodeData) treeNode.getParent()
					.getData();
			switch (treeNodeData.getConcept()) {
			case "Need":
				Besoin savedNeed = needsAndAnswersService.createOrUpdateNeed(
						treeNodeData.getId(), treeNodeData.getLabel(),
						parentNodeData.getId());
				treeNodeData.setId(savedNeed.getId());
				setSelectedNode(treeNode);
				break;
			case "Answer":
				Reponse savedAnswer = needsAndAnswersService
				.createOrUpdateAnswer(treeNodeData.getId(),
						treeNodeData.getLabel(), parentNodeData.getId());
				treeNodeData.setId(savedAnswer.getId());
				setSelectedNode(treeNode);
				break;
			default:
				logger.info("treeNode has an unknown concept value: {}",
						treeNodeData.getConcept());
				break;
			}
		} else {
			logger.info("entering saveTreeNode with null parameter, we do nothing.");
		}
		logger.info("leaving saveTreeNode");
		logger.info("------");
	}

	public void deleteSelectedNode() {
		logger.info("entering deleteSelectedNode, selectedNode={}",
				(TreeNodeData) selectedNode.getData());
		if (selectedNode != null) {
			// Business-level constraint : we don't make possible to recursively
			// delete nodes
			if (selectedNode.getChildCount() == 0) {
				if (needsAndAnswersService
						.deleteNeed(((TreeNodeData) selectedNode.getData())
								.getId())) {
					// If the selectedNode was the only child, we must change
					// back the parent's type to "Need", so that the good 
					// ContextMenu will be displayed
					if (selectedNode.getParent().getChildCount() == 1) {
						if ((selectedNode.getParent().getType()
								.equals(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS))
								|| (selectedNode.getParent().getType()
										.equals(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS))) {
							((DefaultTreeNode) selectedNode.getParent()).setType("Need");
						}
					}
					selectedNode.getParent().getChildren().remove(selectedNode);
					selectedNode.setParent(null);
					this.selectedNode = null;
				} else {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.TITLE"),
							getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.DETAILS"));
					RequestContext.getCurrentInstance().update("messages");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
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

	private void expandParentsOf(TreeNode node) {
		TreeNode parent = node.getParent();
		while (parent != null) {
			parent.setExpanded(true);
			parent = parent.getParent();
		}
	}

	private void collapseChildrenOf(TreeNode node) {
		for (TreeNode child : node.getChildren()) {
			child.setExpanded(false);
			if (!child.isLeaf()) {
				collapseChildrenOf(child);
			}
		}
	}

	private void collapseTree() {
		editableTreeBean.getVisibleRoot().setExpanded(false);
		collapseChildrenOf(editableTreeBean.getVisibleRoot());
	}

	private void expandOnlyOneNode(TreeNode node) {
		if (node != null) {
			collapseTree();
			expandParentsOf(node);
		}
	}

	public String getTreeNodeType_NEED_LEAF() {
		return TREE_NODE_TYPE_NEED_LEAF;
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS;
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS;
	}

	public String getTreeNodeType_ANSWER_LEAF() {
		return TREE_NODE_TYPE_ANSWER_LEAF;
	}

}
