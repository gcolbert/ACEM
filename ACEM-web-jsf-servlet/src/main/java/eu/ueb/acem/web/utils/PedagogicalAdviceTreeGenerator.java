/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.utils;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-26
 * 
 */
@Component("pedagogicalAdviceTreeGenerator")
@Scope("singleton")
public class PedagogicalAdviceTreeGenerator {

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalAdviceTreeGenerator.class);

	private static final String TREE_NODE_TYPE_NEED_LEAF = "NeedLeaf";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS = "NeedWithAssociatedNeeds";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS = "NeedWithAssociatedAnswers";
	private static final String TREE_NODE_TYPE_ANSWER_LEAF = "AnswerLeaf";

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;

	public static final String getTreeNodeType_NEED_LEAF() {
		return TREE_NODE_TYPE_NEED_LEAF;
	}

	public static final String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS;
	}

	public static final String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS;
	}

	public static final String getTreeNodeType_ANSWER_LEAF() {
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
		TreeNode currentVisibleRoot = null;
		Collection<PedagogicalNeed> needs = needsAndAnswersService.retrieveNeedsAtRoot();
		if (singleVisibleTreeRootLabel != null) {
			currentVisibleRoot = treeBean.addVisibleRoot(singleVisibleTreeRootLabel);
		}

		for (PedagogicalNeed need : needs) {
			// It's not a problem if currentVisibleRoot is null
			TreeNode newNode = createChild(treeBean, need, currentVisibleRoot);
			if (singleVisibleTreeRootLabel == null) {
				treeBean.addVisibleRoot(newNode);
			}
			else {
				currentVisibleRoot.getChildren().add(newNode);
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
	private TreeNode createChild(EditableTreeBean treeBean, PedagogicalNeed need, TreeNode parentNode) {
		// We create the root node for this branch
		need = needsAndAnswersService.retrievePedagogicalNeed(need.getId(), true);
		TreeNode newNode = treeBean.addChild(getTreeNodeType_NEED_LEAF(), parentNode, need.getId(), need.getName(),
				"PedagogicalNeed");

		// We look for children (needs) and recursively create them too
		Collection<PedagogicalNeed> associatedPedagogicalNeeds = need.getChildren();
		if (!associatedPedagogicalNeeds.isEmpty()) {
			newNode.setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
			for (PedagogicalNeed needChild : associatedPedagogicalNeeds) {
				createChild(treeBean, needChild, newNode);
			}
		}

		Collection<PedagogicalAnswer> associatedPedagogicalAnswers = need.getAnswers();
		if (!associatedPedagogicalAnswers.isEmpty()) {
			newNode.setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
			need.setAnswers((Set<PedagogicalAnswer>) associatedPedagogicalAnswers);
			for (PedagogicalAnswer answer : associatedPedagogicalAnswers) {
				treeBean.addChild(getTreeNodeType_ANSWER_LEAF(), newNode, answer.getId(), answer.getName(),
						"PedagogicalAnswer");
			}
		}
		
		return newNode;
	}
	
}
