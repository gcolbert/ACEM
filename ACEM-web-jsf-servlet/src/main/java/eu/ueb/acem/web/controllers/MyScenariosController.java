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
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalActivityViewBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 2943632466935430900L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyScenariosController.class);

	private PedagogicalScenarioViewBean selectedScenarioViewBean;

	private PedagogicalActivityViewBean selectedPedagogicalActivityViewBean;
	
	@Inject
	private ScenariosService scenariosService;

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private SortableTableBean<PedagogicalScenarioViewBean> pedagogicalScenarioViewBeans;

	@Inject
	private PickListBean pickListBean;
	
	public MyScenariosController() {
		pedagogicalScenarioViewBeans = new SortableTableBean<PedagogicalScenarioViewBean>();
	}

	@PostConstruct
	public void init() {
		logger.info("Entering init()");
		try {
			Collection<PedagogicalScenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(getCurrentUser());
			pedagogicalScenarioViewBeans.getTableEntries().clear();
			for (PedagogicalScenario scenario : scenariosOfCurrentUser) {
				pedagogicalScenarioViewBeans.getTableEntries().add(new PedagogicalScenarioViewBean(scenario));
			}
			pedagogicalScenarioViewBeans.sortReverseOrder();
		}
		catch (Exception e) {
			logger.error("Exception in init");
			e.printStackTrace();
		}
		logger.debug("Leaving init()");
	}

	@Override
	public String getPageTitle() {
		StringBuffer sb = new StringBuffer();
		sb.append(msgs.getMessage("MENU.MY_SCENARIOS",null,getCurrentUserLocale()));
		if (getSelectedScenarioViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedScenarioViewBean().getName());
		}
		return sb.toString();
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public void onCreatePedagogicalScenario(String name, String objective) {
		PedagogicalScenario scenario;
		try {
			scenario = scenariosService.createScenario((Teacher)getCurrentUser(), name, objective);
			if (scenario != null) {
				PedagogicalScenarioViewBean pedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(scenario);
				pedagogicalScenarioViewBeans.getTableEntries().add(pedagogicalScenarioViewBean);
				pedagogicalScenarioViewBeans.sortReverseOrder();
				setSelectedScenarioViewBean(pedagogicalScenarioViewBean);
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.DETAILS",null,getCurrentUserLocale()),
						logger);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSelectedScenario() {
		if (selectedScenarioViewBean != null) {
			try {
				if (scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(selectedScenarioViewBean.getId(), getCurrentUser().getId())) {
					pedagogicalScenarioViewBeans.getTableEntries().remove(selectedScenarioViewBean);
					MessageDisplayer.info(
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
				}
				else {
					MessageDisplayer.error(
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()),
							logger);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			selectedScenarioViewBean = null;
		}
	}

	public Long getSelectedScenarioId() {
		if (getSelectedScenarioViewBean() != null) {
			return getSelectedScenarioViewBean().getId();
		}
		else {
			return null;
		}
	}

	public void setSelectedScenarioId(Long scenarioId) {
// TODO : méthode qui pourrait servir à avoir l'id du scénario sélectionné dans l'URL
//		logger.info("Entering setSelectedScenarioId, scenarioId = {}", scenarioId);
//		PedagogicalScenarioViewBean scenarioViewBean = this.scenarioViewBeans.getTableEntries().get(scenarioId.intValue());
//		setSelectedScenarioViewBean(scenarioViewBean);
//		logger.info("Leaving setSelectedScenarioId, scenarioId = {}", scenarioId);
	}

	public List<PedagogicalScenarioViewBean> getScenarioViewBeans() {
		return pedagogicalScenarioViewBeans.getTableEntries();
	}

	public PedagogicalScenarioViewBean getSelectedScenarioViewBean() {
		return selectedScenarioViewBean;
	}

	public void setSelectedScenarioViewBean(PedagogicalScenarioViewBean selectedScenarioViewBean) {
		this.selectedScenarioViewBean = selectedScenarioViewBean;
		if (this.selectedScenarioViewBean != null) {
			this.selectedScenarioViewBean.getPedagogicalActivityViewBeans().clear();
			for (PedagogicalActivity pedagogicalActivity : this.selectedScenarioViewBean.getScenario().getPedagogicalActivities()) {
				pedagogicalActivity = scenariosService.retrievePedagogicalActivity(pedagogicalActivity.getId(), true);
				PedagogicalActivityViewBean pedagogicalActivityViewBean = new PedagogicalActivityViewBean(pedagogicalActivity);
				this.selectedScenarioViewBean.getPedagogicalActivityViewBeans().add(pedagogicalActivityViewBean);
				for (ResourceCategory resourceCategory : pedagogicalActivityViewBean.getPedagogicalActivity().getResourceCategories()) {
					ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(resourceCategory);
					pedagogicalActivityViewBean.getToolCategoryViewBeans().add(toolCategoryViewBean);
				}
				Collections.sort(pedagogicalActivityViewBean.getToolCategoryViewBeans());
			}
			Collections.sort(this.selectedScenarioViewBean.getPedagogicalActivityViewBeans());
		}
	}

	public PedagogicalActivityViewBean getSelectedPedagogicalActivityViewBean() {
		return selectedPedagogicalActivityViewBean;
	}

	public void setSelectedPedagogicalActivityViewBean(PedagogicalActivityViewBean selectedActivityViewBean) {
		this.selectedPedagogicalActivityViewBean = selectedActivityViewBean;
	}

	public void onScenarioRowSelect(SelectEvent event) {
		setSelectedScenarioViewBean((PedagogicalScenarioViewBean) event.getObject());
	}

	public void onSave() {
		logger.debug("onSave");
		selectedScenarioViewBean.setScenario(scenariosService.updateScenario(selectedScenarioViewBean.getScenario()));
		pedagogicalScenarioViewBeans.sortReverseOrder();
		MessageDisplayer.info(
				msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
				msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
	}

	public void onSaveSelectedPedagogicalActivity() {
		if (selectedPedagogicalActivityViewBean.getPedagogicalActivity()==null) {
			// Create
			PedagogicalActivity newPedagogicalActivity = scenariosService.createPedagogicalActivity(selectedPedagogicalActivityViewBean.getName());
			newPedagogicalActivity.setObjective(selectedPedagogicalActivityViewBean.getObjective());
			newPedagogicalActivity.setPositionInScenario(new Long(selectedScenarioViewBean.getPedagogicalActivityViewBeans().size()+1));
			newPedagogicalActivity.setDuration(selectedPedagogicalActivityViewBean.getDuration());
			newPedagogicalActivity.setInstructions(selectedPedagogicalActivityViewBean.getInstructions());
			newPedagogicalActivity.getScenarios().add(selectedScenarioViewBean.getScenario());
			for (ToolCategoryViewBean toolCategoryViewBean : selectedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				newPedagogicalActivity.getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(newPedagogicalActivity);
			}
			selectedPedagogicalActivityViewBean.setPedagogicalActivity(scenariosService.updatePedagogicalActivity(newPedagogicalActivity));
			selectedScenarioViewBean.getPedagogicalActivityViewBeans().add(selectedPedagogicalActivityViewBean);
		}
		else {
			// Update
			selectedPedagogicalActivityViewBean.getPedagogicalActivity().getResourceCategories().clear();
			for (ToolCategoryViewBean toolCategoryViewBean : selectedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				selectedPedagogicalActivityViewBean.getPedagogicalActivity().getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(selectedPedagogicalActivityViewBean.getPedagogicalActivity());
			}
			selectedPedagogicalActivityViewBean.setPedagogicalActivity(scenariosService.updatePedagogicalActivity(selectedPedagogicalActivityViewBean.getPedagogicalActivity()));
		}
		MessageDisplayer.info(
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
	}

	public void prepareCreatePedagogicalActivity() {
		selectedPedagogicalActivityViewBean = new PedagogicalActivityViewBean();
		preparePicklistToolCategoryViewBeansForPedagogicalActivity(null);
	}

	public void preparePicklistToolCategoryViewBeansForPedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		List<ToolCategoryViewBean> allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
		for (ResourceCategory toolCategory : resourcesService.retrieveAllCategories()) {
			allToolCategoryViewBeans.add(new ToolCategoryViewBean(toolCategory));
		}
		pickListBean.getPickListEntities().getSource().clear();
		pickListBean.getPickListEntities().getSource().addAll(allToolCategoryViewBeans);
		pickListBean.getPickListEntities().getTarget().clear();
		if (pedagogicalActivityViewBean != null) {
			for (ToolCategoryViewBean toolCategoryViewBean : pedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				pickListBean.getPickListEntities().getSource().remove(toolCategoryViewBean);
				pickListBean.getPickListEntities().getTarget().add(toolCategoryViewBean);
			}
		}
	}

	public void onDeletePedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		if (pedagogicalActivityViewBean != null) {
			if (scenariosService.deletePedagogicalActivity(pedagogicalActivityViewBean.getPedagogicalActivity().getId())) {
				int positionOfDeletedActivity = selectedScenarioViewBean.getPedagogicalActivityViewBeans().indexOf(pedagogicalActivityViewBean);
				selectedScenarioViewBean.getPedagogicalActivityViewBeans().remove(pedagogicalActivityViewBean);
				// We renumber the remaining pedagogical activities of the selected scenario
				for (int i = positionOfDeletedActivity; i < selectedScenarioViewBean.getPedagogicalActivityViewBeans().size(); i++) {
					PedagogicalActivityViewBean pedagogicalActivityViewBeanNeedingANewPosition = selectedScenarioViewBean.getPedagogicalActivityViewBeans().get(i);
					PedagogicalActivity pedagogicalActivity = pedagogicalActivityViewBeanNeedingANewPosition.getPedagogicalActivity();
					pedagogicalActivity.setPositionInScenario(new Long(i+1));
					pedagogicalActivity = scenariosService.updatePedagogicalActivity(pedagogicalActivity);
					pedagogicalActivityViewBeanNeedingANewPosition.setPedagogicalActivity(pedagogicalActivity);
				}
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()));
			}
		}
	}

	public void onToolCategoryTransfer(TransferEvent event) {
		@SuppressWarnings("unchecked")
		List<ToolCategoryViewBean> listOfMovedViewBeans = (List<ToolCategoryViewBean>) event.getItems();
		for (ToolCategoryViewBean movedToolCategoryViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				selectedPedagogicalActivityViewBean.getToolCategoryViewBeans().add(movedToolCategoryViewBean);
			}
			else {
				selectedPedagogicalActivityViewBean.getToolCategoryViewBeans().remove(movedToolCategoryViewBean);
			}
		}
	}

}
