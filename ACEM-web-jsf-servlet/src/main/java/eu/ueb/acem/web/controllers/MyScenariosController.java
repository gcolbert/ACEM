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

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalActivityViewBean;
import eu.ueb.acem.web.viewbeans.bleu.ScenarioViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-19
 * 
 */
@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	private static final Logger logger = LoggerFactory.getLogger(MyScenariosController.class);

	private ScenarioViewBean selectedScenarioViewBean;

	private PedagogicalActivityViewBean selectedPedagogicalActivityViewBean;

	@Autowired
	ScenariosService scenariosService;

	@Autowired
	TableBean<ScenarioViewBean> tableBean;

	public MyScenariosController() {
	}

	@PostConstruct
	public void initScenariosController() {
		try {
			logger.info("initScenariosController, currentUser={}", getCurrentUser());

			Collection<Scenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(getCurrentUser());
			logger.info("found {} scenarios for author {}", scenariosOfCurrentUser.size(), getCurrentUser().getName());
			tableBean.getTableEntries().clear();
			for (Scenario scenario : scenariosOfCurrentUser) {
				logger.info("scenario = {}", scenario.getName());
				tableBean.getTableEntries().add(new ScenarioViewBean(scenario));
			}
			tableBean.sortReverseOrder();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createScenario(String name, String objective) {
		Scenario scenario;
		try {
			scenario = scenariosService.createScenario((Enseignant)getCurrentUser(), name, objective);
			if (scenario != null) {
				ScenarioViewBean scenarioViewBean = new ScenarioViewBean(scenario);
				tableBean.getTableEntries().add(scenarioViewBean);
				tableBean.sortReverseOrder();
				setSelectedScenarioViewBean(scenarioViewBean);
				MessageDisplayer.showMessageToUserWithSeverityInfo(
						getString("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.TITLE"),
						getString("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.DETAILS"));
			}
			else {
				MessageDisplayer.showMessageToUserWithSeverityError(
						getString("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.TITLE"),
						getString("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.DETAILS"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSelectedScenario() {
		if (selectedScenarioViewBean != null) {
			logger.info("deleteSelectedScenario, id={}", selectedScenarioViewBean.getId());
			if (scenariosService.deleteScenario(selectedScenarioViewBean.getId())) {
				tableBean.getTableEntries().remove(selectedScenarioViewBean);
				MessageDisplayer.showMessageToUserWithSeverityInfo(
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE"),
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS"));
			}
			else {
				MessageDisplayer.showMessageToUserWithSeverityError(
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE"),
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS"));
			}
			selectedScenarioViewBean = null;
		}
	}

	public List<ScenarioViewBean> getScenarioViewBeans() {
		return tableBean.getTableEntries();
	}

	public ScenarioViewBean getSelectedScenarioViewBean() {
		return selectedScenarioViewBean;
	}

	public void setSelectedScenarioViewBean(ScenarioViewBean selectedScenarioViewBean) {
		this.selectedScenarioViewBean = selectedScenarioViewBean;
	}

	public PedagogicalActivityViewBean getSelectedActivityViewBean() {
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
				getString("MY_SCENARIOS.SELECTED_SCENARIO.LIST.ACTIVITY_EDIT.TITLE"),
				((PedagogicalActivityViewBean) event.getObject()).getName());
		scenariosService.updatePedagogicalActivity(((PedagogicalActivityViewBean) event.getObject()).getPedagogicalActivity());
	}

	public void onSave() {
		selectedScenarioViewBean.setScenario(scenariosService.updateScenario(selectedScenarioViewBean.getScenario()));
		tableBean.sortReverseOrder();
		MessageDisplayer.showMessageToUserWithSeverityInfo(
				getString("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.TITLE"),
				getString("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.DETAILS"));
	}

	public void onCreateActivity() {
		ActivitePedagogique pedagogicalActivity = scenariosService
				.createPedagogicalActivity(getString("MY_SCENARIOS.SELECTED_SCENARIO.NEW_ACTIVITY_DEFAULT_NAME"));
		selectedScenarioViewBean.getScenario().addPedagogicalActivity(pedagogicalActivity);
		pedagogicalActivity = scenariosService.updatePedagogicalActivity(pedagogicalActivity);
		Collections.sort(selectedScenarioViewBean.getPedagogicalActivityViewBeans());
		selectedScenarioViewBean.setScenario(scenariosService.updateScenario(selectedScenarioViewBean.getScenario()));
	}

}
