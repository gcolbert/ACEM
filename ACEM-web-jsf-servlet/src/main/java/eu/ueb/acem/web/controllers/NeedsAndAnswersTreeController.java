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

import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;

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
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Controller("needsAndAnswersTreeController")
@Scope("view")
public class NeedsAndAnswersTreeController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTreeController.class);

	private static final String TREE_NODE_TYPE_NEED_LEAF = "NeedLeaf";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS = "NeedWithAssociatedNeeds";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS = "NeedWithAssociatedAnswers";
	private static final String TREE_NODE_TYPE_ANSWER_LEAF = "AnswerLeaf";

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;

	@Autowired
	private EditableTreeBean needsAndAnswersTreeBean;

	private TreeNode selectedNode;

	public NeedsAndAnswersTreeController() {
	}

	@PostConstruct
	public void initNeedsAndAnswersTreeController() {
		logger.info("entering initNeedsAndAnswersTreeController");
		initTree(needsAndAnswersTreeBean, getString("NEEDS_AND_ANSWERS.TREE.VISIBLE_ROOT.LABEL"));
		logger.info("leaving initNeedsAndAnswersTreeController");
		logger.info("------");
	}

	/**
	 * Fills the given {@link EditableTreeBean} with the Pedagogical Advice
	 * nodes returned by the {@link NeedsAndAnswersService} implementation
	 * defined in this controller.
	 * 
	 * @param treeBean
	 *            the treeBean to initialize.
	 * @param singleTreeRootLabel
	 *            is an optional string, that, if not null, will be the label of
	 *            a special unique node at the root of the tree. Even if the
	 *            data returned from the service have multiple roots, it can be
	 *            useful to force the creation of an ancestor node, for example
	 *            if the creation of a child node requires the user to
	 *            right-click on an existing node. That way, the user will be
	 *            able to start creating nodes even if there is no node returned
	 *            from the service.
	 */
	public void initTree(EditableTreeBean treeBean, String singleVisibleTreeRootLabel) {
		if (singleVisibleTreeRootLabel != null) {
			treeBean.addVisibleRoot(getString("NEEDS_AND_ANSWERS.TREE.VISIBLE_ROOT.LABEL"));
		}
		Collection<Besoin> needs = needsAndAnswersService.retrieveNeedsAtRoot();
		logger.info("Found {} needs at root of tree.", needs.size());
		for (Besoin need : needs) {
			logger.info("need = {}", need.getName());
			TreeNode currentVisibleRoot = null;
			if (singleVisibleTreeRootLabel != null) {
				// If the function was called with the
				// "singleVisibleTreeRootLabel" set,
				// we add the real roots of the tree as children of this
				// "artificial" root.
				currentVisibleRoot = new DefaultTreeNode(getTreeNodeType_NEED_LEAF(), new TreeNodeData(need.getId(),
						need.getName(), "Need"), treeBean.getVisibleRoots().get(0));
				if (need.getChildren().size() > 0) {
					((DefaultTreeNode) currentVisibleRoot).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
				}
				else if (need.getAnswers().size() > 0) {
					((DefaultTreeNode) currentVisibleRoot).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
				}
			}
			else {
				// otherwise, we add the current node as a visible root by
				// itself (thus allowing many visible roots)
				currentVisibleRoot = treeBean.addVisibleRoot(need.getName());
			}
			for (Besoin child : need.getChildren()) {
				createTree(child, currentVisibleRoot);
			}
		}
		if (singleVisibleTreeRootLabel != null) {
			treeBean.getVisibleRoots().get(0).setExpanded(true);
		}
	}

	/**
	 * Recursive function to construct Tree
	 */
	private void createTree(Besoin need, TreeNode rootNode) {
		// We create the root node
		TreeNode newNode = new DefaultTreeNode(getTreeNodeType_NEED_LEAF(), new TreeNodeData(need.getId(),
				need.getName(), "Need"), rootNode);
		// We look for children and recursively create them too
		@SuppressWarnings("unchecked")
		Collection<Besoin> associatedNeeds = (Collection<Besoin>) need.getChildren();
		if (associatedNeeds.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
			for (Besoin besoinChild : associatedNeeds) {
				createTree(besoinChild, newNode);
			}
		}

		@SuppressWarnings("unchecked")
		Collection<Reponse> answers = (Collection<Reponse>) need.getAnswers();
		if (answers.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
			need.setAnswers((Set<Reponse>) answers);
			for (Reponse answer : answers) {
				new DefaultTreeNode(getTreeNodeType_ANSWER_LEAF(), new TreeNodeData(answer.getId(), answer.getName(),
						"Answer"), newNode);
			}
		}
	}

	public TreeNode getTreeRoot() {
		return needsAndAnswersTreeBean.getRoot();
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
			needsAndAnswersTreeBean.expandOnlyOneNode(selectedNode);
		}
		this.selectedNode = selectedNode;
	}

	public void displaySelectedNodeInfo() {
		if (selectedNode != null) {
			MessageDisplayer.showMessageToUserWithSeverityInfo("Selected", selectedNode.getData().toString());
		}
	}

	public void expandSelectedNodeIncludingChildren() {
		if (selectedNode != null) {
			logger.info("selectedNode = {}", selectedNode.getData().toString());
			needsAndAnswersTreeBean.expandIncludingChildren(selectedNode);
		}
		else {
			logger.info("selectedNode is null");
		}
	}

	public void collapseSelectedNodeIncludingChildren() {
		if (selectedNode != null) {
			logger.info("selectedNode = {}", selectedNode.getData().toString());
			needsAndAnswersTreeBean.collapseIncludingChildren(selectedNode);
		}
		else {
			logger.info("selectedNode is null");
		}
	}

	public void associateNeedWithSelectedNode() {
		logger.info("entering addChildToSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());

		Besoin newNeed = needsAndAnswersService.createOrUpdateNeed(null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_NEED_LABEL"), ((TreeNodeData) selectedNode.getData()).getId());

		TreeNode newNode = needsAndAnswersTreeBean.addChild(getTreeNodeType_NEED_LEAF(), selectedNode, newNeed.getId(),
				getString("NEEDS_AND_ANSWERS.TREE.NEW_NEED_LABEL"), "Need");
		((DefaultTreeNode) selectedNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
		setSelectedNode(newNode);
		needsAndAnswersTreeBean.expandOnlyOneNode(newNode);
		logger.info("leaving addChildToSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		logger.info("------");
	}

	public void associateAnswerWithSelectedNode() {
		logger.info("entering associateAnswerToSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());

		Reponse newAnswer = needsAndAnswersService.createOrUpdateAnswer(null,
				getString("NEEDS_AND_ANSWERS.TREE.NEW_ANSWER_LABEL"), ((TreeNodeData) selectedNode.getData()).getId());

		TreeNode newNode = needsAndAnswersTreeBean.addChild(getTreeNodeType_ANSWER_LEAF(), selectedNode,
				newAnswer.getId(), getString("NEEDS_AND_ANSWERS.TREE.NEW_ANSWER_LABEL"), "Answer");
		((DefaultTreeNode) selectedNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
		setSelectedNode(newNode);
		needsAndAnswersTreeBean.expandOnlyOneNode(newNode);
		logger.info("leaving associateAnswerToSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		logger.info("------");
	}

	public void deleteSelectedNode() {
		logger.info("entering deleteSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		if (selectedNode != null) {
			// Business-level constraint : we don't make possible to recursively
			// delete nodes
			if (selectedNode.isLeaf()) {
				if (needsAndAnswersService.deleteNode(((TreeNodeData) selectedNode.getData()).getId())) {
					// If the selectedNode was the only child, we must change
					// back the parent's type to be a "Need leaf", so that the
					// good
					// ContextMenu will be displayed
					if (selectedNode.getParent().getChildCount() == 1) {
						if ((selectedNode.getParent().getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS()))
								|| (selectedNode.getParent().getType()
										.equals(TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS))) {
							((DefaultTreeNode) selectedNode.getParent()).setType(getTreeNodeType_NEED_LEAF());
						}
					}
					selectedNode.getParent().getChildren().remove(selectedNode);
					selectedNode.setParent(null);
					this.selectedNode = null;
				}
				else {
					MessageDisplayer.showMessageToUserWithSeverityError(
							getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.TITLE"),
							getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.DETAILS"));
					logger.info("The service failed to delete the node.");
				}
			}
			else {
				MessageDisplayer.showMessageToUserWithSeverityError(
						getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.TITLE"),
						getString("NEEDS_AND_ANSWERS.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.DETAILS"));
				logger.info("The selected node has children, cannot delete!");
			}
		}
		logger.info("leaving deleteSelectedNode");
		logger.info("------");
	}

	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();

		TreeNodeData dragNodeData = (TreeNodeData) dragNode.getData();
		TreeNodeData dropNodeData = (TreeNodeData) dropNode.getData();

		needsAndAnswersService.changeParentOfNeed(dragNodeData.getId(), dropNodeData.getId());

		MessageDisplayer.showMessageToUserWithSeverityInfo("Dragged " + dragNodeData, "Dropped on " + dropNodeData
				+ " at " + dropIndex);
	}

	public void onLabelSave(EditableTreeBean.TreeNodeData treeNodeData) {
		if (treeNodeData.getConcept().equals("Answer")) {
			needsAndAnswersService.saveAnswerName(treeNodeData.getId(), treeNodeData.getLabel());
		}
		else if (treeNodeData.getConcept().equals("Need")) {
			needsAndAnswersService.saveNeedName(treeNodeData.getId(), treeNodeData.getLabel());
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
