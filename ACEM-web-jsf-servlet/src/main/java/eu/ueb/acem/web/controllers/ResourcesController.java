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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("resourcesController")
@Scope("view")
public class ResourcesController extends AbstractContextAwareController {

	private static final long serialVersionUID = -5663154564837226988L;

	private static final Logger logger = LoggerFactory.getLogger(ResourcesController.class);

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private OrganisationsService organisationsService;
	private Map<Long, OrganisationViewBean> organisationViewBeans;
	private List<OrganisationViewBean> allOrganisationViewBeans;

	@Inject
	private NeedsAndAnswersTreeGenerator needsAndAnswersTreeGenerator;
	private EditableTreeBean pedagogicalUsesTreeBean;

	@Inject
	private EditableTreeBean resourcesTreeBean;
	private static final String TREE_NODE_TYPE_CATEGORY = "CategoryNode";
	private TreeNode resourcesTreeSelectedNode;

	private Map<Long, ToolCategoryViewBean> toolCategoryViewBeans;
	private Long selectedToolCategoryId;
	private ToolCategoryViewBean selectedToolCategoryViewBean;

	private Map<Long, ResourceViewBean> resourceViewBeans;
	private ResourceViewBean selectedResourceViewBean;

	private static final String[] RESOURCE_TYPES = { "software", "softwareDocumentation", "equipment",
			"pedagogicalAndDocumentaryResources", "professionalTraining" };
	private String selectedResourceType; // One of RESOURCE_TYPES
	private Map<String, List<ToolCategoryViewBean>> categoryViewBeansByResourceType;

	public ResourcesController() {
		toolCategoryViewBeans = new HashMap<Long, ToolCategoryViewBean>();
		resourceViewBeans = new HashMap<Long, ResourceViewBean>();
		organisationViewBeans = new HashMap<Long, OrganisationViewBean>();
		allOrganisationViewBeans = new ArrayList<OrganisationViewBean>();
		categoryViewBeansByResourceType = new HashMap<String, List<ToolCategoryViewBean>>();
	}

	@PostConstruct
	public void initResourcesController() {
		logger.info("entering initResourcesController");

		for (String resourceType : RESOURCE_TYPES) {
			categoryViewBeansByResourceType.put(resourceType, new ArrayList<ToolCategoryViewBean>());
			for (ResourceCategory resourceCategory : resourcesService.retrieveCategoriesForResourceType(resourceType)) {
				categoryViewBeansByResourceType.get(resourceType).add(new ToolCategoryViewBean(resourceCategory));
			}
			Collections.sort(categoryViewBeansByResourceType.get(resourceType));
		}

		setAllOrganisationViewBeansAsList();

		logger.info("leaving initResourcesController");
		logger.info("------");
	}

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

	public String getTreeNodeType_CATEGORY() {
		return TREE_NODE_TYPE_CATEGORY;
	}

	public void prepareToolCategoryTreeForResourceType(String resourceType) {
		logger.info("Entering prepareToolCategoryTreeForResourceType for resourceType={}", resourceType);
		this.selectedResourceType = resourceType;
		resourcesTreeBean.clear();
		switch (resourceType) {
		case "software":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE.LABEL"));
			break;
		case "softwareDocumentation":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.SOFTWARE_DOCUMENTATION.LABEL"));
			break;
		case "equipment":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.EQUIPMENT.LABEL"));
			break;
		case "pedagogicalAndDocumentaryResources":
			resourcesTreeBean
					.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PEDAGOGICAL_AND_DOCUMENTARY_RESOURCES.LABEL"));
			break;
		case "professionalTraining":
			resourcesTreeBean.addVisibleRoot(getString("RESOURCES.TREE.VISIBLE_ROOTS.PROFESSIONAL_TRAININGS.LABEL"));
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
		for (ToolCategoryViewBean categoryViewBean : categoryViewBeansByResourceType.get(resourceType)) {
			resourcesTreeBean.addChild(getTreeNodeType_CATEGORY(), resourcesTreeBean.getVisibleRoots().get(0),
					categoryViewBean.getId(), categoryViewBean.getName(), "category");
		}
		resourcesTreeBean.getVisibleRoots().get(0).setExpanded(true);
		logger.info("Leaving prepareToolCategoryTreeForResourceType for resourceType={}", resourceType);
	}

	public Long getSelectedToolCategoryId() {
		return selectedToolCategoryId;
	}

	public void setSelectedToolCategoryId(Long toolCategoryId) {
		logger.info("Entering setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
		ToolCategoryViewBean toolCategoryViewBean = getToolCategoryViewBean(toolCategoryId);
		if (toolCategoryViewBean != null) {
			selectedToolCategoryId = toolCategoryId;
			selectedToolCategoryViewBean = toolCategoryViewBean;

			// We initialize the "pedagogical advice" tree for the selected
			// ToolCategoryViewBean
			setPedagogicalUsesTreeRoot(toolCategoryViewBean.getDomainBean());

			// We initialize the "favoriteToolCategory" attribute of the
			// selected ToolCategoryViewBean
			if (getCurrentUserViewBean() instanceof TeacherViewBean) {
				TeacherViewBean teacherViewBean = (TeacherViewBean) getCurrentUserViewBean();
				if (teacherViewBean.getFavoriteToolCategoryViewBeans().contains(selectedToolCategoryViewBean)) {
					selectedToolCategoryViewBean.setFavoriteToolCategory(true);
				}
				else {
					selectedToolCategoryViewBean.setFavoriteToolCategory(false);
				}
			}
		}
		logger.info("Leaving setSelectedToolCategoryId, toolCategoryId = {}", toolCategoryId);
	}

	public ToolCategoryViewBean getSelectedToolCategoryViewBean() {
		return selectedToolCategoryViewBean;
	}

	private ToolCategoryViewBean getToolCategoryViewBean(Long id) {
		ToolCategoryViewBean viewBean = null;
		if (toolCategoryViewBeans.containsKey(id)) {
			logger.info("toolCategoryViewBean found in toolCategoryViewBeans map, so we don't reload it.");
			viewBean = toolCategoryViewBeans.get(id);
		}
		else {
			logger.info("toolCategoryViewBean not found in toolCategoryViewBeans map, we load it with ResourcesService.");
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(id);
			if (toolCategory != null) {
				viewBean = new ToolCategoryViewBean(toolCategory);
				for (Ressource resource : toolCategory.getResources()) {
					viewBean.addResourceViewBean(getResourceViewBean(resource.getId()));
				}
				toolCategoryViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no category with id={} according to ResourcesService", id);
			}
		}
		return viewBean;
	}

	private ResourceViewBean getResourceViewBean(Long id) {
		ResourceViewBean viewBean = null;
		if (resourceViewBeans.containsKey(id)) {
			logger.info("resourceViewBean found in resourceViewBeans map, so we don't reload it.");
			viewBean = resourceViewBeans.get(id);
		}
		else {
			logger.info("resourceViewBean not found in resourceViewBeans map, we load it with ResourcesService.");
			Ressource tool = resourcesService.retrieveResource(id);
			if (tool != null) {
				if (tool instanceof Applicatif) {
					viewBean = new SoftwareViewBean((Applicatif) tool);
				}
				else if (tool instanceof RessourcePedagogiqueEtDocumentaire) {
					viewBean = new DocumentaryAndPedagogicalResourceViewBean((RessourcePedagogiqueEtDocumentaire) tool);
				}
				else if (tool instanceof Equipement) {
					viewBean = new EquipmentViewBean((Equipement) tool);
				}
				else if (tool instanceof DocumentationApplicatif) {
					viewBean = new SoftwareDocumentationViewBean((DocumentationApplicatif) tool);
				}
				else if (tool instanceof FormationProfessionnelle) {
					viewBean = new ProfessionalTrainingViewBean((FormationProfessionnelle) tool);
				}

				viewBean.setOrganisationPossessingResourceViewBean(getOrganisationViewBean(tool
						.getOrganisationPossessingResource().getId()));

				for (Organisation organisation : tool.getOrganisationsHavingAccessToResource()) {
					viewBean.addOrganisationViewingResourceViewBean(getOrganisationViewBean(organisation.getId()));
				}

				resourceViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no tool with id={} according to ResourcesService", id);
			}
		}
		return viewBean;
	}

	private OrganisationViewBean getOrganisationViewBean(Long id) {
		OrganisationViewBean viewBean = null;
		if (organisationViewBeans.containsKey(id)) {
			logger.info("organisationViewBean found in organisationViewBeans map, so we don't reload it.");
			viewBean = organisationViewBeans.get(id);
		}
		else {
			logger.info("organisationViewBean not found in organisationViewBeans map, we load it with OrganisationsService.");
			Organisation organisation = organisationsService.retrieveOrganisation(id);
			if (organisation != null) {
				if (organisation instanceof Communaute) {
					viewBean = new CommunityViewBean((Communaute) organisation);
				}
				else if (organisation instanceof Etablissement) {
					viewBean = new InstitutionViewBean((Etablissement) organisation);
				}
				else if (organisation instanceof Service) {
					viewBean = new AdministrativeDepartmentViewBean((Service) organisation);
				}
				else if (organisation instanceof Composante) {
					viewBean = new TeachingDepartmentViewBean((Composante) organisation);
				}
				organisationViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no organisation with id={} according to OrganisationsService", id);
			}
		}
		return viewBean;
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public void setSelectedResourceViewBean(ResourceViewBean resourceViewBean) {
		selectedResourceViewBean = resourceViewBean;
	}

	public TreeNode getResourcesTreeRoot() {
		return resourcesTreeBean.getRoot();
	}

	public List<ToolCategoryViewBean> getAllCategoryViewBeans() {
		List<ToolCategoryViewBean> allCategoryViewBeans = new ArrayList<ToolCategoryViewBean>(
				toolCategoryViewBeans.values());
		Collections.sort(allCategoryViewBeans);
		return allCategoryViewBeans;
	}

	public List<ToolCategoryViewBean> getCategoryViewBeansForSelectedResourceType() {
		return categoryViewBeansByResourceType.get(selectedResourceType);
	}

	public List<OrganisationViewBean> getAllOrganisationViewBeansAsList() {
		return allOrganisationViewBeans;
	}

	private void setAllOrganisationViewBeansAsList() {
		Collection<Organisation> organisations = organisationsService.retrieveAllOrganisations();
		for (Organisation organisation : organisations) {
			if (organisation instanceof Communaute) {
				allOrganisationViewBeans.add(new CommunityViewBean((Communaute) organisation));
			}
			else if (organisation instanceof Etablissement) {
				allOrganisationViewBeans.add(new InstitutionViewBean((Etablissement) organisation));
			}
			else if (organisation instanceof Service) {
				allOrganisationViewBeans.add(new AdministrativeDepartmentViewBean((Service) organisation));
			}
			else if (organisation instanceof Composante) {
				allOrganisationViewBeans.add(new TeachingDepartmentViewBean((Composante) organisation));
			}
		}
	}

	public TreeNode getPedagogicalUsesTreeRoot() {
		if (pedagogicalUsesTreeBean != null) {
			return pedagogicalUsesTreeBean.getRoot();
		}
		else {
			return null;
		}
	}

	private void setPedagogicalUsesTreeRoot(ResourceCategory resourceCategory) {
		logger.info("Entering setPedagogicalUsesTreeRoot");
		pedagogicalUsesTreeBean = needsAndAnswersTreeGenerator.createNeedAndAnswersTree(null);
		Set<Long> idsOfLeavesToKeep = new HashSet<Long>();
		for (Reponse answer : resourceCategory.getAnswers()) {
			idsOfLeavesToKeep.add(answer.getId());
		}
		pedagogicalUsesTreeBean.retainLeavesAndParents(idsOfLeavesToKeep);
		logger.info("Leaving setPedagogicalUsesTreeRoot");
	}

	public List<Scenario> getScenariosUsingSelectedToolCategory() {
		return new ArrayList<Scenario>(
				resourcesService.retrieveScenariosAssociatedWithResourceCategory(selectedToolCategoryId));
	}

	public TreeNode getSelectedNode() {
		return resourcesTreeSelectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (resourcesTreeSelectedNode != null) {
			resourcesTreeSelectedNode.setSelected(false);
		}
		resourcesTreeSelectedNode = selectedNode;

		resourcesTreeBean.expandOnlyOneNode(resourcesTreeSelectedNode);

		if ((resourcesTreeSelectedNode != null)
				&& (resourcesTreeSelectedNode.getType().equals(getTreeNodeType_CATEGORY()))) {
			setSelectedToolCategoryId(((TreeNodeData) resourcesTreeSelectedNode.getData()).getId());
		}
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

	public void onSelectedToolCategorySave() {
		logger.info("onSelectedToolCategorySave");
		selectedToolCategoryViewBean.setResourceCategory(resourcesService
				.updateResourceCategory(selectedToolCategoryViewBean.getResourceCategory()));
	}

	public void onToolRowSelect(SelectEvent event) {
		logger.info("onToolRowSelect");
		setSelectedResourceViewBean((ResourceViewBean) event.getObject());
	}

	public void onCreateResource(ResourceCategory category, String name, String iconFileName) {
		logger.info("onCreateResource");
		resourcesService.createResource(selectedResourceType, category, name, iconFileName);
	}

	public void onModifySelectedResource(String iconFileName) {
		logger.info("onModifySelectedResource({})", iconFileName);
		selectedResourceViewBean.setIconFileName(iconFileName);
		resourcesService.updateResource(selectedResourceViewBean.getDomainBean());
	}

}