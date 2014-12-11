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

import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;

/**
 * @author Grégoire Colbert
 * @since 2014-05-26
 * 
 */
@Component("needsAndAnswersTreeGenerator")
@Scope("singleton")
class NeedsAndAnswersTreeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTreeGenerator.class);
	
	private static final String TREE_NODE_TYPE_NEED_LEAF = "NeedLeaf";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS = "NeedWithAssociatedNeeds";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS = "NeedWithAssociatedAnswers";
	private static final String TREE_NODE_TYPE_ANSWER_LEAF = "AnswerLeaf";

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;
	
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
	
	/**
	 * Returns a new {@link EditableTreeBean} containing the Pedagogical Advice
	 * nodes returned by the {@link NeedsAndAnswersService}.
	 * 
	 * @param singleVisibleTreeRootLabel
	 *            is an optional string, that, if not null, will be the label of
	 *            a special unique node at the root of the tree. Even if the
	 *            data returned from the service have multiple roots, it can be
	 *            useful to force the creation of an ancestor node, for example
	 *            if the creation of a child node requires the user to
	 *            right-click on an existing node. That way, the user will be
	 *            able to start creating nodes even if there is no node returned
	 *            from the service.
	 */
	public EditableTreeBean createNeedAndAnswersTree(String singleVisibleTreeRootLabel) {
		EditableTreeBean treeBean = new EditableTreeBean();
		if (singleVisibleTreeRootLabel != null) {
			treeBean.addVisibleRoot(singleVisibleTreeRootLabel);
		}
		Collection<PedagogicalNeed> needs = needsAndAnswersService.retrieveNeedsAtRoot();
		logger.info("Found {} needs at root of tree.", needs.size());
		for (PedagogicalNeed need : needs) {
			logger.info("need = {}", need.getName());
			TreeNode currentVisibleRoot = null;
			if (singleVisibleTreeRootLabel != null) {
				// If the function was called with the
				// "singleVisibleTreeRootLabel" set,
				// we add the real roots of the tree as children of this
				// given "artificial" root.
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
				// otherwise, we add the current Need as a visible root by
				// itself (thus allowing many visible roots, but it may lead
				// to an invisible tree if the service returns 0 node)
				currentVisibleRoot = treeBean.addVisibleRoot(need.getName());
			}
			for (PedagogicalNeed child : need.getChildren()) {
				createChild(treeBean, child, currentVisibleRoot);
			}
		}
		if (singleVisibleTreeRootLabel != null) {
			treeBean.getVisibleRoots().get(0).setExpanded(true);
		}
		return treeBean;
	}

	/**
	 * Recursive function to construct Tree
	 */
	private void createChild(EditableTreeBean treeBean, PedagogicalNeed need, TreeNode parentNode) {
		// We create the root node for this branch
		//TreeNode newNode = new DefaultTreeNode(getTreeNodeType_NEED_LEAF(), new TreeNodeData(need.getId(), need.getName(), "Need"), rootNode);
		TreeNode newNode = treeBean.addChild(getTreeNodeType_NEED_LEAF(), parentNode, need.getId(), need.getName(), "Need");
		// We look for children and recursively create them too
		Collection<PedagogicalNeed> associatedNeeds = need.getChildren();
		if (associatedNeeds.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
			for (PedagogicalNeed besoinChild : associatedNeeds) {
				createChild(treeBean, besoinChild, newNode);
			}
		}

		Collection<PedagogicalAnswer> answers = need.getAnswers();
		if (answers.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
			need.setAnswers((Set<PedagogicalAnswer>) answers);
			for (PedagogicalAnswer answer : answers) {
				//new DefaultTreeNode(getTreeNodeType_ANSWER_LEAF(), new TreeNodeData(answer.getId(), answer.getName(), "Answer"), newNode);
				treeBean.addChild(getTreeNodeType_ANSWER_LEAF(), newNode, answer.getId(), answer.getName(), "Answer");
			}
		}
	}

}
