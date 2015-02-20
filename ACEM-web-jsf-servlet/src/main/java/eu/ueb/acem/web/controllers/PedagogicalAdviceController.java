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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.utils.NeedsAndAnswersTreeGenerator;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Controller("pedagogicalAdviceController")
@Scope("view")
public class PedagogicalAdviceController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3305497053688875560L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalAdviceController.class);

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private EditableTreeBean needsAndAnswersTreeBean;

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;

	@Inject
	private PickListBean pickListBean;

	private TreeNode selectedNode;

	private PedagogicalNeed selectedPedagogicalNeed;
	private PedagogicalAnswer selectedPedagogicalAnswer;

	@Inject
	private SortableTableBean<PedagogicalScenarioViewBean> pedagogicalScenarioViewBeans;

	private SortableTableBean<ToolCategoryViewBean> toolCategoryViewBeans;

	private List<ToolCategoryViewBean> toolCategoryViewBeansForSelectedAnswer;

	public String getTreeNodeType_NEED_LEAF() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_LEAF();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS();
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS();
	}

	public String getTreeNodeType_ANSWER_LEAF() {
		return needsAndAnswersTreeGenerator.getTreeNodeType_ANSWER_LEAF();
	}

	public PedagogicalAdviceController() {
		toolCategoryViewBeansForSelectedAnswer = new ArrayList<ToolCategoryViewBean>();
		toolCategoryViewBeans = new SortableTableBean<ToolCategoryViewBean>();

		pedagogicalScenarioViewBeans = new SortableTableBean<PedagogicalScenarioViewBean>();
	}

	@PostConstruct
	public void init() {
		logger.info("entering init");
		needsAndAnswersTreeBean = needsAndAnswersTreeGenerator.createNeedAndAnswersTree(msgs.getMessage(
				"PEDAGOGICAL_ADVICE.TREE.VISIBLE_ROOT.LABEL", null, getCurrentUserLocale()));

		Collection<ResourceCategory> toolCategories = resourcesService.retrieveAllCategories();
		logger.info("found {} tool categories", toolCategories.size());
		toolCategoryViewBeans.getTableEntries().clear();
		for (ResourceCategory toolCategory : toolCategories) {
			logger.info("tool category = {}", toolCategory.getName());
			ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
			toolCategoryViewBeans.getTableEntries().add(toolCategoryViewBean);
		}
		toolCategoryViewBeans.sort();

		logger.info("leaving init");
		logger.info("------");
	}

	@Override
	public String getPageTitle() {
		StringBuffer sb = new StringBuffer();
		sb.append(msgs.getMessage("MENU.PEDAGOGICAL_ADVICE",null,getCurrentUserLocale()));
		if (getSelectedAnswer() != null) {
			sb.append(" - ");
			sb.append(getSelectedAnswer().getName());
		}
		return sb.toString();
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public EditableTreeBean getNeedsAndAnswersTreeBean() {
		return needsAndAnswersTreeBean;
	}

	public TreeNode getTreeRoot() {
		return needsAndAnswersTreeBean.getRoot();
	}

	private void setSelectedNode(TreeNode selectedNode) {
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
		}
		this.selectedNode = selectedNode;
	}

	/**
	 * This function binds the Ajax "expand" event of TreeNodes. It means a
	 * click on the little triangle to expand/collapse a node.
	 * 
	 * @param event
	 *            The event that the user has expanded a TreeNode.
	 */
	public void onNodeExpand(NodeExpandEvent event) {
		setSelectedNode(event.getTreeNode());
		needsAndAnswersTreeBean.expandOnlyOneNode(event.getTreeNode());
	}

	/**
	 * This function binds the Ajax "select" event of TreeNodes. It means a
	 * click on the label of a node.
	 * 
	 * @param event
	 *            The event that the user has clicked on the label of a
	 *            TreeNode.
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		setSelectedNode(event.getTreeNode());

		needsAndAnswersTreeBean.expandOnlyOneNode(event.getTreeNode());

		// We set the selectedPedagogicalAnswer, because answers don't have children and
		// there is no onNodeExpand generated by the JavaScript onclick event
		// handler.
		if (event.getTreeNode().getType().equals(getTreeNodeType_ANSWER_LEAF())) {
			selectedPedagogicalAnswer = needsAndAnswersService.retrievePedagogicalAnswer(((TreeNodeData) event.getTreeNode()
					.getData()).getId(), true);
			setToolCategoryViewBeansForSelectedAnswer();
			setScenarioViewBeansRelatedToSelectedAnswer();
		}
		else {
			selectedPedagogicalAnswer = null;
		}
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public PedagogicalAnswer getSelectedAnswer() {
		return selectedPedagogicalAnswer;
	}

	public void deleteSelectedNode() {
		logger.debug("entering deleteSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		if (selectedNode != null) {
			// Business-level constraint : we don't make possible to recursively
			// delete nodes
			if (selectedNode.isLeaf()) {
				if (needsAndAnswersService.deleteNode(((TreeNodeData) selectedNode.getData()).getId())) {
					// If the selectedNode was the only child, we must change
					// back the parent's type to be a "Need leaf"
					if (selectedNode.getParent().getChildCount() == 1) {
						if ((selectedNode.getParent().getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS()))
								|| (selectedNode.getParent().getType()
										.equals(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS()))) {
							((DefaultTreeNode) selectedNode.getParent()).setType(getTreeNodeType_NEED_LEAF());
						}
					}
					selectedNode.getParent().getChildren().remove(selectedNode);
					selectedNode.setParent(null);
					setSelectedNode(null);
				}
				else {
					MessageDisplayer.error(msgs.getMessage(
							"PEDAGOGICAL_ADVICE.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.TITLE", null,
							getCurrentUserLocale()), msgs.getMessage(
							"PEDAGOGICAL_ADVICE.TREE.CONTEXT_MENU.DELETE_NODE.DELETION_FAILED.DETAILS", null,
							getCurrentUserLocale()), logger);
					logger.info("The service failed to delete the node.");
				}
			}
			else {
				MessageDisplayer.error(msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.TITLE", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.CONTEXT_MENU.DELETE_NODE.HAS_CHILDREN_ERROR.DETAILS", null,
						getCurrentUserLocale()),
						logger);
				logger.info("The selected node has children, cannot delete!");
			}
		}
		logger.debug("leaving deleteSelectedNode");
		logger.debug("------");
	}

	public void associateNeedWithSelectedNode() {
		logger.debug("entering associateNeedWithSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());

		PedagogicalNeed newNeed = needsAndAnswersService.createOrUpdateNeed(null,
				msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.NEW_NEED_LABEL", null, getCurrentUserLocale()),
				((TreeNodeData) selectedNode.getData()).getId());

		TreeNode newNode = needsAndAnswersTreeBean.addChild(getTreeNodeType_NEED_LEAF(), selectedNode, newNeed.getId(),
				msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.NEW_NEED_LABEL", null, getCurrentUserLocale()), "Need");
		((DefaultTreeNode) selectedNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
		setSelectedNode(newNode);
		needsAndAnswersTreeBean.expandOnlyOneNode(newNode);
		logger.debug("leaving associateNeedWithSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		logger.debug("------");
	}

	public void associateAnswerWithSelectedNode() {
		logger.debug("entering associateAnswerWithSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());

		PedagogicalAnswer newAnswer = needsAndAnswersService.createOrUpdateAnswer(null,
				msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.NEW_ANSWER_LABEL", null, getCurrentUserLocale()),
				((TreeNodeData) selectedNode.getData()).getId());

		TreeNode newNode = needsAndAnswersTreeBean.addChild(getTreeNodeType_ANSWER_LEAF(), selectedNode,
				newAnswer.getId(),
				msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.NEW_ANSWER_LABEL", null, getCurrentUserLocale()), "Answer");
		((DefaultTreeNode) selectedNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
		setSelectedNode(newNode);
		needsAndAnswersTreeBean.expandOnlyOneNode(newNode);
		logger.debug("leaving associateAnswerWithSelectedNode, selectedNode={}", (TreeNodeData) selectedNode.getData());
		logger.debug("------");
	}

	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();
		TreeNodeData dragNodeData = (TreeNodeData) dragNode.getData();
		TreeNodeData dropNodeData = (TreeNodeData) dropNode.getData();
		boolean revertDragDrop = false;

		if (dragNode.getType().equals(getTreeNodeType_ANSWER_LEAF())) {
			if (dropNode.getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS())
					|| (dropNode.getType().equals(getTreeNodeType_NEED_LEAF()))) {
				needsAndAnswersService.changeParentOfNeedOrAnswer(dragNodeData.getId(), dropNodeData.getId());

				// If the dropNode was a NEED_LEAF, we change its type to
				// getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS
				if (dropNode.getType().equals(getTreeNodeType_NEED_LEAF())) {
					((DefaultTreeNode) dropNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
				}
				// If the original parent was a NEED_WITH_ASSOCIATED_ANSWERS
				// with exactly one child -> NEED_LEAF
				if (((TreeNodeData) dragNode.getData()).getParentBackup().getType()
						.equals(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS())
						&& (((TreeNodeData) dragNode.getData()).getParentBackup().getChildCount() == 0)) {
					((DefaultTreeNode) ((TreeNodeData) dragNode.getData()).getParentBackup())
							.setType(getTreeNodeType_NEED_LEAF());
				}

				MessageDisplayer.info("Dragged " + dragNodeData.getLabel(), "Dropped on " + dropNodeData.getLabel()
						+ " at " + dropIndex);
			}
			else if (dropNode.getType().equals("default")) {
				revertDragDrop = true;
				MessageDisplayer.error(msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.ERROR.FORBIDDEN_DRAG_AND_DROP", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.ERROR.CANNOT_DROP_ANSWER_ON_ROOT", null, getCurrentUserLocale()),
						logger);
			}
			else if (dropNode.getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS())) {
				revertDragDrop = true;
				MessageDisplayer.error(msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.ERROR.FORBIDDEN_DRAG_AND_DROP", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.ERROR.CANNOT_DROP_ANSWER_ON_NEED_WITH_ASSOCIATED_NEEDS", null,
						getCurrentUserLocale()), logger);
			}
			else if (dropNode.getType().equals(getTreeNodeType_ANSWER_LEAF())) {
				revertDragDrop = true;
				MessageDisplayer.error(msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.ERROR.FORBIDDEN_DRAG_AND_DROP", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.ERROR.CANNOT_DROP_ANSWER_ON_ANSWER", null, getCurrentUserLocale()),
						logger);
			}
		}
		else {
			// Here we know that dragNode is NEED_LEAF,
			// NEED_WITH_ASSOCIATED_NEEDS or NEED_WITH_ASSOCIATED_ANSWERS
			if (dropNode.getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS())
					|| (dropNode.getType().equals(getTreeNodeType_NEED_LEAF()) || (dropNode.getType().equals("default")))) {
				needsAndAnswersService.changeParentOfNeedOrAnswer(dragNodeData.getId(), dropNodeData.getId());

				// If the dropNode was a NEED_LEAF, we change its type to
				// getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS
				if (dropNode.getType().equals(getTreeNodeType_NEED_LEAF())) {
					((DefaultTreeNode) dropNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
				}
				// If the original parent was a NEED_WITH_ASSOCIATED_NEEDS with
				// exactly one child -> NEED_LEAF
				if (((TreeNodeData) dragNode.getData()).getParentBackup().getType()
						.equals(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS())
						&& (((TreeNodeData) dragNode.getData()).getParentBackup().getChildCount() == 0)) {
					((DefaultTreeNode) ((TreeNodeData) dragNode.getData()).getParentBackup())
							.setType(getTreeNodeType_NEED_LEAF());
				}

				MessageDisplayer.info("Dragged " + dragNodeData, "Dropped on " + dropNodeData + " at " + dropIndex);
			}
			else if (dropNode.getType().equals(getTreeNodeType_ANSWER_LEAF())) {
				revertDragDrop = true;
				MessageDisplayer.error(msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.ERROR.FORBIDDEN_DRAG_AND_DROP", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.ERROR.CANNOT_DROP_NEED_ON_ANSWER", null, getCurrentUserLocale()),
						logger);
			}
			else if (dropNode.getType().equals(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS())) {
				revertDragDrop = true;
				MessageDisplayer.error(msgs.getMessage("PEDAGOGICAL_ADVICE.TREE.ERROR.FORBIDDEN_DRAG_AND_DROP", null,
						getCurrentUserLocale()), msgs.getMessage(
						"PEDAGOGICAL_ADVICE.TREE.ERROR.CANNOT_DROP_NEED_ON_NEED_WITH_ASSOCIATED_ANSWERS", null,
						getCurrentUserLocale()), logger);
			}
		}

		if (revertDragDrop) {
			// If the drag and drop isn't allowed, we have to restore the
			// original parent for the dragNode
			dragNode.setParent(((TreeNodeData) dragNode.getData()).getParentBackup());

			// We delete the dragNode from the new parent's children
			dropNode.getChildren().remove(dragNode);

			// We add the dragNode to the backup parent's children
			((TreeNodeData) dragNode.getData()).getParentBackup().getChildren().add(dragNode);
		}
		else {
			// Otherwise we update the "parentBackup" in the node data to
			// reflect the new parent
			((TreeNodeData) dragNode.getData()).setParentBackup(dragNode.getParent());
		}
	}

	public void onSaveSelectedPedagogicalNeed() {
		needsAndAnswersService.updatePedagogicalNeed(selectedPedagogicalNeed);
		MessageDisplayer.info(msgs.getMessage("PEDAGOGICAL_ADVICE.SELECTED_PEDAGOGICAL_ADVICE.SAVE_SUCCESSFUL.TITLE",
				null, getCurrentUserLocale()), msgs.getMessage(
				"PEDAGOGICAL_ADVICE.SELECTED_PEDAGOGICAL_ADVICE.SAVE_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
	}

	public void onSaveSelectedPedagogicalAnswer() {
		needsAndAnswersService.updatePedagogicalAnswer(selectedPedagogicalAnswer);
		MessageDisplayer.info(msgs.getMessage("PEDAGOGICAL_ADVICE.SELECTED_PEDAGOGICAL_ADVICE.SAVE_SUCCESSFUL.TITLE",
				null, getCurrentUserLocale()), msgs.getMessage(
				"PEDAGOGICAL_ADVICE.SELECTED_PEDAGOGICAL_ADVICE.SAVE_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
	}

	public List<PedagogicalScenarioViewBean> getScenarioViewBeansRelatedToSelectedAnswer() {
		return pedagogicalScenarioViewBeans.getTableEntries();
	}

	public void setScenarioViewBeansRelatedToSelectedAnswer() {
		if (selectedPedagogicalAnswer != null) {
			PedagogicalAnswer pedagogicalAnswer = needsAndAnswersService.retrievePedagogicalAnswer(selectedPedagogicalAnswer.getId(), true);
			pedagogicalScenarioViewBeans.getTableEntries().clear();
			for (PedagogicalScenario scenario : needsAndAnswersService.getScenariosRelatedToAnswer(pedagogicalAnswer.getId())) {
				pedagogicalScenarioViewBeans.getTableEntries().add(new PedagogicalScenarioViewBean(scenario));
			}
		}
	}

	public List<ToolCategoryViewBean> getToolCategoryViewBeansForSelectedAnswer() {
		return toolCategoryViewBeansForSelectedAnswer;
	}

	private void setToolCategoryViewBeansForSelectedAnswer() {
		if (selectedPedagogicalAnswer != null) {
			toolCategoryViewBeansForSelectedAnswer.clear();
			for (ToolCategoryViewBean toolCategoryViewBean : toolCategoryViewBeans.getTableEntries()) {
				if (selectedPedagogicalAnswer.getResourceCategories().contains(toolCategoryViewBean.getDomainBean())) {
					toolCategoryViewBeansForSelectedAnswer.add(toolCategoryViewBean);
				}
			}
		}
	}

	public void preparePicklistToolCategoryViewBeansForSelectedAnswer() {
		logger.info("preparePicklistToolCategoryViewBeansForSelectedAnswer");
		if ((selectedNode != null) && (selectedNode.getType().equals(getTreeNodeType_ANSWER_LEAF()))) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(toolCategoryViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			PedagogicalAnswer selectedAnswer = needsAndAnswersService.retrievePedagogicalAnswer(((TreeNodeData) selectedNode.getData()).getId(), true);
			for (ResourceCategory toolCategoryForSelectedAnswer : selectedAnswer.getResourceCategories()) {
				for (ToolCategoryViewBean toolCategoryViewBean : toolCategoryViewBeans.getTableEntries()) {
					if (toolCategoryForSelectedAnswer.getId().equals(toolCategoryViewBean.getId())) {
						pickListBean.getPickListEntities().getSource().remove(toolCategoryViewBean);
						pickListBean.getPickListEntities().getTarget().add(toolCategoryViewBean);
					}
				}
			}
		}
	}

	public void onTransferToolCategory(TransferEvent event) {
		logger.info("onTransferToolCategory");
		@SuppressWarnings("unchecked")
		List<ToolCategoryViewBean> listOfMovedViewBeans = (List<ToolCategoryViewBean>) event.getItems();
		for (ToolCategoryViewBean movedToolCategoryViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				logger.info("We should associate answer {} and tool category {}",
						((TreeNodeData) selectedNode.getData()).getId(), movedToolCategoryViewBean.getId());
				if (needsAndAnswersService.associateAnswerWithResourceCategory(((TreeNodeData) selectedNode.getData())
						.getId(), movedToolCategoryViewBean.getDomainBean().getId())) {
					toolCategoryViewBeansForSelectedAnswer.add(movedToolCategoryViewBean);
					logger.info("association successful");
				}
				else {
					logger.info("association failed");
				}
				movedToolCategoryViewBean.setDomainBean(resourcesService
						.retrieveResourceCategory(movedToolCategoryViewBean.getId(), true));
			}
			else {
				logger.info("We should dissociate answer {} and tool category {}",
						((TreeNodeData) selectedNode.getData()).getLabel(), movedToolCategoryViewBean.getName());
				if (needsAndAnswersService.dissociateAnswerWithResourceCategory(((TreeNodeData) selectedNode.getData())
						.getId(), movedToolCategoryViewBean.getDomainBean().getId())) {
					toolCategoryViewBeansForSelectedAnswer.remove(movedToolCategoryViewBean);
					logger.info("dissociation successful");
				}
				else {
					logger.info("dissociation failed");
				}
				movedToolCategoryViewBean.setDomainBean(resourcesService
						.retrieveResourceCategory(movedToolCategoryViewBean.getId(), true));
			}
		}
	}

}
