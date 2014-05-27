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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.TransferEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.ScenarioViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Controller("needsAndAnswersController")
@Scope("view")
public class NeedsAndAnswersController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersController.class);

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private EditableTreeBean needsAndAnswersTreeBean;

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;

	@Inject
	private PickListBean pickListBean;

	private TreeNode selectedNode;
	
	private Reponse selectedAnswer;

	@Inject
	private SortableTableBean<ScenarioViewBean> scenarioViewBeans;

	//private List<ScenarioViewBean> scenarioViewBeansForSelectedAnswer;

	private SortableTableBean<ToolCategoryViewBean> toolCategoryViewBeans;

	private List<ToolCategoryViewBean> toolCategoryViewBeansForSelectedAnswer;

	private SortableTableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;

	private List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeansForSelectedAnswer;

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

	public NeedsAndAnswersController() {
		toolCategoryViewBeansForSelectedAnswer = new ArrayList<ToolCategoryViewBean>();
		toolCategoryViewBeans = new SortableTableBean<ToolCategoryViewBean>();

		administrativeDepartmentViewBeansForSelectedAnswer = new ArrayList<AdministrativeDepartmentViewBean>();
		administrativeDepartmentViewBeans = new SortableTableBean<AdministrativeDepartmentViewBean>();

		//scenarioViewBeansForSelectedAnswer = new ArrayList<ScenarioViewBean>();
		scenarioViewBeans = new SortableTableBean<ScenarioViewBean>();
	}

	@PostConstruct
	public void initNeedsAndAnswersController() {
		logger.info("entering initNeedsAndAnswersTreeController");
		needsAndAnswersTreeBean = needsAndAnswersTreeGenerator.createNeedAndAnswersTree(getString("NEEDS_AND_ANSWERS.TREE.VISIBLE_ROOT.LABEL"));

		Collection<ResourceCategory> toolCategories = resourcesService.retrieveAllCategories();
		logger.info("found {} tool categories", toolCategories.size());
		toolCategoryViewBeans.getTableEntries().clear();
		for (ResourceCategory toolCategory : toolCategories) {
			logger.info("tool category = {}", toolCategory.getName());
			ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
			toolCategoryViewBeans.getTableEntries().add(toolCategoryViewBean);
		}
		toolCategoryViewBeans.sort();

		Collection<Service> administrativeDepartments = organisationsService.retrieveAllAdministrativeDepartments();
		logger.info("found {} administrative departments", administrativeDepartments.size());
		administrativeDepartmentViewBeans.getTableEntries().clear();
		for (Service administrativeDepartment : administrativeDepartments) {
			logger.info("administrative department = {}", administrativeDepartment.getName());
			AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
					administrativeDepartment);
			administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
		}
		administrativeDepartmentViewBeans.sort();
		
		logger.info("leaving initNeedsAndAnswersTreeController");
		logger.info("------");
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

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (this.selectedNode != null) {
			this.selectedNode.setSelected(false);
			needsAndAnswersTreeBean.expandOnlyOneNode(selectedNode);
		}
		this.selectedNode = selectedNode;

		selectedAnswer = null;
		if ((this.selectedNode != null) && (this.selectedNode.getType().equals(getTreeNodeType_ANSWER_LEAF()))) {
			selectedAnswer = needsAndAnswersService.retrieveAnswer(((TreeNodeData) selectedNode.getData()).getId());
			setToolCategoryViewBeansForSelectedAnswer();
			setAdministrativeDepartmentViewBeansForSelectedAnswer();
			setScenarioViewBeansRelatedToSelectedAnswer();
		}
	}

	public void displaySelectedNodeInfo() {
		if (selectedNode != null) {
			MessageDisplayer.showMessageToUserWithSeverityInfo("Selected", selectedNode.getData().toString());
		}
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
					// good ContextMenu will be displayed
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

	public void onNodeSelect() {
		// We need to keep this callback function empty.
		// Its purpose is to bind the Ajax "select" event of treenodes.
		// The real function is "setSelectedNode", which is called
		// by the "selection" attribute of the <p:tree>.
		// Thus, this callback does nothing, but it's necessary to have
		// it so that the tree and the selectedNode variable are
		// synchronized on "select" Ajax event.
	}

	public void onLabelSave(EditableTreeBean.TreeNodeData treeNodeData) {
		if (treeNodeData.getConcept().equals("Answer")) {
			needsAndAnswersService.saveAnswerName(treeNodeData.getId(), treeNodeData.getLabel());
		}
		else if (treeNodeData.getConcept().equals("Need")) {
			needsAndAnswersService.saveNeedName(treeNodeData.getId(), treeNodeData.getLabel());
		}
	}

	public List<ScenarioViewBean> getScenarioViewBeansRelatedToSelectedAnswer() {
		return scenarioViewBeans.getTableEntries();
	}

	private void setScenarioViewBeansRelatedToSelectedAnswer() {
		if ((selectedNode != null) && (selectedNode.getType().equals(getTreeNodeType_ANSWER_LEAF()))) {
			logger.info("entering setScenarioViewBeansRelatedToSelectedAnswer");
			Collection<Scenario> scenarios = needsAndAnswersService.getScenariosRelatedToAnswer(((TreeNodeData) selectedNode.getData()).getId());
			logger.info("Found {} scenarios related to selected answer.", scenarios.size());
			scenarioViewBeans.getTableEntries().clear();
			for (Scenario scenario : scenarios) {
				scenarioViewBeans.getTableEntries().add(new ScenarioViewBean(scenario));
			}
			logger.info("leaving setScenarioViewBeansRelatedToSelectedAnswer");
			logger.info("------");
		}
	}

	public List<ToolCategoryViewBean> getToolCategoryViewBeansForSelectedAnswer() {
		return toolCategoryViewBeansForSelectedAnswer;
	}

	private void setToolCategoryViewBeansForSelectedAnswer() {
		if (selectedAnswer != null) {
			logger.info("setToolCategoryViewBeansForSelectedAnswer");
			toolCategoryViewBeansForSelectedAnswer.clear();
			for (ToolCategoryViewBean toolCategoryViewBean : toolCategoryViewBeans.getTableEntries()) {
				if (selectedAnswer.getResourceCategories().contains(toolCategoryViewBean.getResourceCategory())) {
					logger.info("selectedAnswer is associated with {}", toolCategoryViewBean.getResourceCategory()
							.getName());
					toolCategoryViewBeansForSelectedAnswer.add(toolCategoryViewBean);
				}
			}
		}
	}

	public Collection<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeansForSelectedAnswer() {
		return administrativeDepartmentViewBeansForSelectedAnswer;
	}

	private void setAdministrativeDepartmentViewBeansForSelectedAnswer() {
		if (selectedAnswer != null) {
			administrativeDepartmentViewBeansForSelectedAnswer.clear();
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans.getTableEntries()) {
				if (selectedAnswer.getAdministrativeDepartments().contains(administrativeDepartmentViewBean.getAdministrativeDepartment())) {
					administrativeDepartmentViewBeansForSelectedAnswer.add(administrativeDepartmentViewBean);
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
			Reponse selectedAnswer = needsAndAnswersService.retrieveAnswer((((TreeNodeData) selectedNode.getData())
					.getId()));
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

	public void preparePicklistAdministrativeDepartmentViewBeansForSelectedAnswer() {
		logger.info("preparePicklistAdministrativeDepartmentViewBeansForSelectedAnswer");
		if (selectedAnswer != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(administrativeDepartmentViewBeans.getTableEntries());
			pickListBean.getPickListEntities().getTarget().clear();
			Reponse selectedAnswer = needsAndAnswersService.retrieveAnswer((((TreeNodeData) selectedNode.getData())
					.getId()));
			for (Service administrativeDepartmentForSelectedAnswer : selectedAnswer.getAdministrativeDepartments()) {
				for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
						.getTableEntries()) {
					if (administrativeDepartmentForSelectedAnswer.getId().equals(
							administrativeDepartmentViewBean.getId())) {
						pickListBean.getPickListEntities().getSource().remove(administrativeDepartmentViewBean);
						pickListBean.getPickListEntities().getTarget().add(administrativeDepartmentViewBean);
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
						((TreeNodeData) selectedNode.getData()).getLabel(), movedToolCategoryViewBean.getName());
				if (needsAndAnswersService.associateAnswerWithToolCategory(((TreeNodeData) selectedNode.getData())
						.getId(), movedToolCategoryViewBean.getDomainBean().getId())) {
					toolCategoryViewBeansForSelectedAnswer.add(movedToolCategoryViewBean);
					logger.info("association successful");
				}
				else {
					logger.info("association failed");
				}
				movedToolCategoryViewBean.setDomainBean(resourcesService
						.retrieveResourceCategory(movedToolCategoryViewBean.getId()));
			}
			else {
				logger.info("We should dissociate answer {} and tool category {}",
						((TreeNodeData) selectedNode.getData()).getLabel(), movedToolCategoryViewBean.getName());
				if (needsAndAnswersService.dissociateAnswerWithToolCategory(((TreeNodeData) selectedNode.getData())
						.getId(), movedToolCategoryViewBean.getDomainBean().getId())) {
					toolCategoryViewBeansForSelectedAnswer.remove(movedToolCategoryViewBean);
					logger.info("dissociation successful");
				}
				else {
					logger.info("dissociation failed");
				}
				movedToolCategoryViewBean.setDomainBean(resourcesService
						.retrieveResourceCategory(movedToolCategoryViewBean.getId()));
			}
		}
	}

	public void onTransferAdministrativeDepartment(TransferEvent event) {
		logger.info("onTransferAdministrativeDepartment");
		@SuppressWarnings("unchecked")
		List<AdministrativeDepartmentViewBean> listOfMovedViewBeans = (List<AdministrativeDepartmentViewBean>) event
				.getItems();
		for (AdministrativeDepartmentViewBean movedAdministrativeDepartmentViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				logger.info("We should associate answer {} and administrative department {}",
						((TreeNodeData) selectedNode.getData()).getLabel(),
						movedAdministrativeDepartmentViewBean.getName());
				if (needsAndAnswersService.associateAnswerWithAdministrativeDepartment(((TreeNodeData) selectedNode
						.getData()).getId(), movedAdministrativeDepartmentViewBean.getDomainBean().getId())) {
					administrativeDepartmentViewBeansForSelectedAnswer.add(movedAdministrativeDepartmentViewBean);
					logger.info("association successful");
				}
				else {
					logger.info("association failed");
				}
				movedAdministrativeDepartmentViewBean.setDomainBean(organisationsService
						.retrieveOrganisation(movedAdministrativeDepartmentViewBean.getId()));
			}
			else {
				logger.info("We should dissociate answer {} and administrative department {}",
						((TreeNodeData) selectedNode.getData()).getLabel(),
						movedAdministrativeDepartmentViewBean.getName());
				if (needsAndAnswersService.dissociateAnswerWithAdministrativeDepartment(((TreeNodeData) selectedNode
						.getData()).getId(), movedAdministrativeDepartmentViewBean.getDomainBean().getId())) {
					administrativeDepartmentViewBeansForSelectedAnswer.remove(movedAdministrativeDepartmentViewBean);
					logger.info("dissociation successful");
				}
				else {
					logger.info("dissociation failed");
				}
				movedAdministrativeDepartmentViewBean.setDomainBean(organisationsService
						.retrieveOrganisation(movedAdministrativeDepartmentViewBean.getId()));
			}
		}
	}

}
