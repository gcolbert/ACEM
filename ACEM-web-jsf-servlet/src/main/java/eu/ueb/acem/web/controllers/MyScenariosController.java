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
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.TableBean;

@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	private static final Logger logger = LoggerFactory.getLogger(MyScenariosController.class);

	private Scenario selectedScenario;
	
	private ActivitePedagogique selectedActivity;

	private Personne currentUser;

	@Autowired
	ScenariosService scenariosService;

	@Autowired
	TableBean<Scenario> tableBean;

	public MyScenariosController() {
	}

	@PostConstruct
	public void initScenariosController() {
		try {
			currentUser = getCurrentUser();
			logger.info("initScenariosController, currentUser={}", currentUser);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Collection<Scenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(currentUser);
		logger.info("found {} scenarios for author {}", scenariosOfCurrentUser.size(), currentUser.getName());
		tableBean.getTableEntries().clear();
		for (Scenario scenario : scenariosOfCurrentUser) {
			logger.info("scenario = {}", scenario.getName());
			tableBean.getTableEntries().add(scenario);
		}
	}

	public void createScenario(String name, String objective) {
		Scenario scenario = scenariosService.createScenario(currentUser, name, objective);
		if (scenario != null) {
			tableBean.getTableEntries().add(scenario);
			setSelectedScenario(scenario);
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

	public void deleteSelectedScenario() {
		if (selectedScenario != null) {
			logger.info("deleteSelectedScenario, id={}", selectedScenario.getId());
			if (scenariosService.deleteScenario(selectedScenario.getId())) {
				tableBean.getTableEntries().remove(selectedScenario);
				MessageDisplayer.showMessageToUserWithSeverityInfo(
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE"),
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS"));
			}
			else {
				MessageDisplayer.showMessageToUserWithSeverityError(
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE"),
						getString("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS"));
			}
			selectedScenario = null;
		}
	}

	public List<Scenario> getScenarios() {
		return tableBean.getTableEntries();
	}

	public Scenario getSelectedScenario() {
		return selectedScenario;
	}

	public void setSelectedScenario(Scenario selectedScenario) {
		this.selectedScenario = selectedScenario;
		List<ActivitePedagogique> orderedActivities = new ArrayList<ActivitePedagogique>(selectedScenario.getPedagogicalActivities());
		Collections.sort(orderedActivities);
		this.selectedScenario.setPedagogicalActivities(orderedActivities);
	}

	public ActivitePedagogique getSelectedActivity() {
		return selectedActivity;
	}

	public void setSelectedActivity(ActivitePedagogique selectedActivity) {
		this.selectedActivity = selectedActivity;
	}

	public void onScenarioRowSelect(org.primefaces.event.SelectEvent event) {
		logger.info("onScenarioRowSelect");
		setSelectedScenario((Scenario) event.getObject());
	}

	public void onStepRowSelect(org.primefaces.event.SelectEvent event) {
		logger.info("onStepRowSelect");
		setSelectedActivity((ActivitePedagogique) event.getObject());
	}

}
