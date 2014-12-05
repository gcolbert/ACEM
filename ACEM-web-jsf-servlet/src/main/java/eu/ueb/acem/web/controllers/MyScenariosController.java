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
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalActivityViewBean;
import eu.ueb.acem.web.viewbeans.bleu.ScenarioViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	private static final Logger logger = LoggerFactory.getLogger(MyScenariosController.class);

	private ScenarioViewBean selectedScenarioViewBean;

	private PedagogicalActivityViewBean selectedPedagogicalActivityViewBean;

	@Inject
	ScenariosService scenariosService;

	@Inject
	SortableTableBean<ScenarioViewBean> scenarioViewBeans;

	@Inject
	private PickListBean pickListBean;
	
	@Inject
	private ToolCategoryViewBeanHandler toolCategoryViewBeanHandler;
	
	public MyScenariosController() {
		scenarioViewBeans = new SortableTableBean<ScenarioViewBean>();
	}

	@PostConstruct
	public void initScenariosController() {
		try {
			logger.info("initScenariosController, currentUser={}", getCurrentUser());

			Collection<PedagogicalScenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(getCurrentUser());
			logger.info("found {} scenarios for author {}", scenariosOfCurrentUser.size(), getCurrentUser().getName());
			scenarioViewBeans.getTableEntries().clear();
			for (PedagogicalScenario scenario : scenariosOfCurrentUser) {
				logger.info("scenario = {}", scenario.getName());
				scenarioViewBeans.getTableEntries().add(new ScenarioViewBean(scenario));
			}
			scenarioViewBeans.sortReverseOrder();
		}
		catch (Exception e) {
			logger.error("Exception in initScenariosController!");
			e.printStackTrace();
		}
	}

	public PickListBean getPickListBean() {
		return pickListBean;
	}
	
	public void createScenario(String name, String objective) {
		PedagogicalScenario scenario;
		try {
			scenario = scenariosService.createScenario((Teacher)getCurrentUser(), name, objective);
			if (scenario != null) {
				ScenarioViewBean scenarioViewBean = new ScenarioViewBean(scenario);
				scenarioViewBeans.getTableEntries().add(scenarioViewBean);
				scenarioViewBeans.sortReverseOrder();
				setSelectedScenarioViewBean(scenarioViewBean);
				MessageDisplayer.showMessageToUserWithSeverityInfo(
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.showMessageToUserWithSeverityError(
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.DETAILS",null,getCurrentUserLocale()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSelectedScenario() {
		if (selectedScenarioViewBean != null) {
			logger.info("deleteSelectedScenario, id={}", selectedScenarioViewBean.getId());
			try {
				if (scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(selectedScenarioViewBean.getId(), getCurrentUser().getId())) {
					scenarioViewBeans.getTableEntries().remove(selectedScenarioViewBean);
					MessageDisplayer.showMessageToUserWithSeverityInfo(
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
				}
				else {
					MessageDisplayer.showMessageToUserWithSeverityError(
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
							msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			selectedScenarioViewBean = null;
		}
	}

	public List<ScenarioViewBean> getScenarioViewBeans() {
		return scenarioViewBeans.getTableEntries();
	}

	public ScenarioViewBean getSelectedScenarioViewBean() {
		return selectedScenarioViewBean;
	}

	public void setSelectedScenarioViewBean(ScenarioViewBean selectedScenarioViewBean) {
		this.selectedScenarioViewBean = selectedScenarioViewBean;
	}

	public PedagogicalActivityViewBean getSelectedPedagogicalActivityViewBean() {
		return selectedPedagogicalActivityViewBean;
	}

	public void setSelectedActivityViewBean(PedagogicalActivityViewBean selectedActivityViewBean) {
		this.selectedPedagogicalActivityViewBean = selectedActivityViewBean;
	}

	public void onScenarioRowSelect(SelectEvent event) {
		logger.info("onScenarioRowSelect");
		setSelectedScenarioViewBean((ScenarioViewBean) event.getObject());
	}

	public void onStepRowSelect(SelectEvent event) {
		logger.info("onStepRowSelect");
		setSelectedActivityViewBean((PedagogicalActivityViewBean) event.getObject());
	}

	public void onActivityRowEdit(RowEditEvent event) {
		MessageDisplayer.showMessageToUserWithSeverityInfo(
				msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.LIST.ACTIVITY_EDIT.TITLE",null,getCurrentUserLocale()),
				((PedagogicalActivityViewBean) event.getObject()).getName());
		scenariosService.updatePedagogicalActivity(((PedagogicalActivityViewBean) event.getObject()).getPedagogicalActivity());
	}

	public void onSave() {
		logger.info("onSave");
		selectedScenarioViewBean.setScenario(scenariosService.updateScenario(selectedScenarioViewBean.getScenario()));
		scenarioViewBeans.sortReverseOrder();
		MessageDisplayer.showMessageToUserWithSeverityInfo(
				msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
				msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
	}

	public void onSavePedagogicalActivity() {
		logger.info("onSavePedagogicalActivity");
	}
	
	public void setCurrentPedagogicalActivityViewBean(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		logger.info("setCurrentPedagogicalActivityViewBean, pedagogicalActivityViewBean={}", pedagogicalActivityViewBean);
	}
	
	public void preparePicklistToolCategoryViewBeansForSelectedPedagogicalActivity() {
		logger.info("preparePicklistToolCategoryViewBeansForSelectedPedagogicalActivity");
		if (getSelectedPedagogicalActivityViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(toolCategoryViewBeanHandler.getToolCategoryViewBeansAsList());
			pickListBean.getPickListEntities().getTarget().clear();
			
			for (ResourceCategory toolCategoryAssociatedWithSelectedActivity : getSelectedPedagogicalActivityViewBean().getPedagogicalActivity().getResourceCategories()) {
				ToolCategoryViewBean toolCategoryViewBean = toolCategoryViewBeanHandler.getToolCategoryViewBean(toolCategoryAssociatedWithSelectedActivity.getId());
				pickListBean.getPickListEntities().getSource().remove(toolCategoryViewBean);
				pickListBean.getPickListEntities().getTarget().add(toolCategoryViewBean);
			}
		}
	}
	
	public void onCreateActivity() {
		PedagogicalActivity pedagogicalActivity = scenariosService
				.createPedagogicalActivity(msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.NEW_ACTIVITY_DEFAULT_NAME",null,getCurrentUserLocale()));
		selectedScenarioViewBean.getScenario().addPedagogicalActivity(pedagogicalActivity);
		pedagogicalActivity = scenariosService.updatePedagogicalActivity(pedagogicalActivity);
		Collections.sort(selectedScenarioViewBean.getPedagogicalActivityViewBeans());
		selectedScenarioViewBean.setScenario(scenariosService.updateScenario(selectedScenarioViewBean.getScenario()));
	}

	public void onToolCategoryTransfer() {
		logger.info("onToolCategoryTransfer");
	}
	
}
