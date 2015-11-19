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
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalActivityViewBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalSequenceViewBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalSessionViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * Controller for the "My Scenarios" page.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-19
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

	private PedagogicalScenarioViewBean selectedPedagogicalScenarioViewBean;

	private PedagogicalScenarioViewBean objectEditedPedagogicalScenarioViewBean;
	
	private PedagogicalActivityViewBean objectEditedPedagogicalActivityViewBean;

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
		Collection<PedagogicalScenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(getCurrentUser());
		pedagogicalScenarioViewBeans.getTableEntries().clear();
		for (PedagogicalScenario scenario : scenariosOfCurrentUser) {
			pedagogicalScenarioViewBeans.getTableEntries().add(new PedagogicalScenarioViewBean(scenario));
		}
		pedagogicalScenarioViewBeans.sortReverseOrder();
		logger.debug("Leaving init()");
	}

	@Override
	public String getPageTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(msgs.getMessage("MENU.MY_SCENARIOS", null, getSessionController().getCurrentUserLocale()));
		if (getSelectedPedagogicalScenarioViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedPedagogicalScenarioViewBean().getName());
		}
		return sb.toString();
	}

	/*
	 * ****************** Pedagogical scenarios ********************
	 */
	public Long getSelectedScenarioId() {
		if (getSelectedPedagogicalScenarioViewBean() != null) {
			return getSelectedPedagogicalScenarioViewBean().getId();
		}
		else {
			return null;
		}
	}

	public void setSelectedScenarioId(Long scenarioId) {
		logger.info("Entering setSelectedScenarioId, scenarioId = {}", scenarioId);
		PedagogicalScenario scenario = scenariosService.retrievePedagogicalScenario(scenarioId,  true);
		if (scenario != null) {
			setSelectedPedagogicalScenarioViewBean(new PedagogicalScenarioViewBean(scenario));
		}
		logger.info("Leaving setSelectedScenarioId, scenarioId = {}", scenarioId);
	}

	public List<PedagogicalScenarioViewBean> getScenarioViewBeans() {
		return pedagogicalScenarioViewBeans.getTableEntries();
	}

	public PedagogicalScenarioViewBean getSelectedPedagogicalScenarioViewBean() {
		return selectedPedagogicalScenarioViewBean;
	}

	public void setSelectedPedagogicalScenarioViewBean(PedagogicalScenarioViewBean selectedScenarioViewBean) {
		this.selectedPedagogicalScenarioViewBean = selectedScenarioViewBean;
		if (this.selectedPedagogicalScenarioViewBean != null) {

			// We load/reload the pedagogicalSequenceViewBeans associated with the selected scenario
			this.selectedPedagogicalScenarioViewBean.getPedagogicalSequenceViewBeans().clear();

			// We find the first parallel sequences of the scenario
			Collection<PedagogicalSequence> firstSequences = scenariosService.getFirstPedagogicalSequencesOfScenario(selectedScenarioViewBean.getDomainBean());
			for (PedagogicalSequence pedagogicalSequence : firstSequences) {

				while (pedagogicalSequence != null) {
					// We reload the PedagogicalSequence from database to prevent potential obsolete data
					pedagogicalSequence = scenariosService.retrievePedagogicalSequence(pedagogicalSequence.getId(), true);

					// We add a new PedagogicalSequenceViewBean to the selectedPedagogicalScenarioViewBean
					PedagogicalSequenceViewBean pedagogicalSequenceViewBean = new PedagogicalSequenceViewBean(pedagogicalSequence);
					this.selectedPedagogicalScenarioViewBean.getPedagogicalSequenceViewBeans().add(pedagogicalSequenceViewBean);

					// We find the first parallel sessions of the sequence
					Collection<PedagogicalSession> firstSessions = scenariosService.getFirstPedagogicalSessionsOfSequence(pedagogicalSequence); 
					for (PedagogicalSession pedagogicalSession : firstSessions) {

						while (pedagogicalSession != null) {
							// We reload the PedagogicalSession from database to prevent potential obsolete data
							pedagogicalSession = scenariosService.retrievePedagogicalSession(pedagogicalSession.getId(), true);

							// We add a new PedagogicalSessionViewBean to pedagogicalSequenceViewBean
							PedagogicalSessionViewBean pedagogicalSessionViewBean = new PedagogicalSessionViewBean(pedagogicalSession);
							pedagogicalSequenceViewBean.getPedagogicalSessionViewBeans().add(pedagogicalSessionViewBean);

							// We find the first parallel sessions of the sequence
							Collection<PedagogicalActivity> firstActivities = scenariosService.getFirstPedagogicalActivitiesOfSession(pedagogicalSession);
							for (PedagogicalActivity pedagogicalActivity : firstActivities) {

								while (pedagogicalActivity != null) {
									// We reload the PedagogicalActivity from database to prevent potential obsolete data
									pedagogicalActivity = scenariosService.retrievePedagogicalActivity(pedagogicalActivity.getId(), true);

									// We add a new PedagogicalActivityViewBean to pedagogicalSessionViewBean
									PedagogicalActivityViewBean pedagogicalActivityViewBean = new PedagogicalActivityViewBean(pedagogicalActivity);
									pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().add(pedagogicalActivityViewBean);

									// PedagogicalActivity <-> ResourceCategory
									for (ResourceCategory resourceCategory : pedagogicalActivity.getResourceCategories()) {
										ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(resourceCategory);
										pedagogicalActivityViewBean.getToolCategoryViewBeans().add(toolCategoryViewBean);
									}
									Collections.sort(pedagogicalActivityViewBean.getToolCategoryViewBeans());

									pedagogicalActivity = pedagogicalActivity.getNextPedagogicalActivity();
								}
							}

							pedagogicalSession = pedagogicalSession.getNextPedagogicalSession();
						}
					}

					pedagogicalSequence = pedagogicalSequence.getNextPedagogicalSequence();
				}
			}
		}
	}

	public void onScenarioRowSelect(SelectEvent event) {
		setSelectedPedagogicalScenarioViewBean((PedagogicalScenarioViewBean) event.getObject());
	}

	public void deleteSelectedScenario() {
		if (selectedPedagogicalScenarioViewBean != null) {
			if (scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(selectedPedagogicalScenarioViewBean.getId(), getCurrentUser().getId())) {
				((Teacher)getSessionController().getCurrentUserViewBean().getDomainBean()).getPedagogicalScenarios().remove(selectedPedagogicalScenarioViewBean);
				pedagogicalScenarioViewBeans.getTableEntries().remove(selectedPedagogicalScenarioViewBean);
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE",null,getSessionController().getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS",null,getSessionController().getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE",null,getSessionController().getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS",null,getSessionController().getCurrentUserLocale()),
						logger);
			}
			selectedPedagogicalScenarioViewBean = null;
		}
	}

	public void prepareCreatePedagogicalScenario() {
		objectEditedPedagogicalScenarioViewBean = new PedagogicalScenarioViewBean();
		selectedPedagogicalScenarioViewBean = null;
	}

	public void prepareModifyPedagogicalScenario() {
		objectEditedPedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(scenariosService.retrievePedagogicalScenario(
				selectedPedagogicalScenarioViewBean.getId(), true));
	}

	/*
	 * ****************** Create/modify scenario dialogs ***********
	 */
	public PedagogicalScenarioViewBean getObjectEditedPedagogicalScenarioViewBean() {
		return objectEditedPedagogicalScenarioViewBean;
	}

	public void onSavePedagogicalScenario() {
		if (objectEditedPedagogicalScenarioViewBean.getDomainBean()==null) {
			// Create
			PedagogicalScenario scenario = scenariosService.createPedagogicalScenario((Teacher) getCurrentUser(),
					objectEditedPedagogicalScenarioViewBean.getName(), objectEditedPedagogicalScenarioViewBean.getObjective());

			// TODO : for now, we create an invisible PedagogicalSequence in the scenario
			// but we will later need to modify the JSF to display sequences
			PedagogicalSequence pedagogicalSequence1 = scenariosService.createPedagogicalSequence("Invisible sequence");
			pedagogicalSequence1.setPedagogicalScenario(scenario);
			scenario.getPedagogicalSequences().add(pedagogicalSequence1);
			pedagogicalSequence1 = scenariosService.updatePedagogicalSequence(pedagogicalSequence1);
			scenario = scenariosService.updatePedagogicalScenario(scenario);

			// TODO : for now, we create an invisible PedagogicalSession in the scenario
			// but we will later need to modify the JSF to display sessions
			PedagogicalSession pedagogicalSession1 = scenariosService.createPedagogicalSession("Invisible session");
			pedagogicalSession1.setPedagogicalSequence(pedagogicalSequence1);
			pedagogicalSequence1.getPedagogicalSessions().add(pedagogicalSession1);
			pedagogicalSequence1 = scenariosService.updatePedagogicalSequence(pedagogicalSequence1);
			pedagogicalSession1 = scenariosService.updatePedagogicalSession(pedagogicalSession1);

			if (scenario != null) {
				PedagogicalScenarioViewBean pedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(scenario);
				pedagogicalScenarioViewBeans.getTableEntries().add(pedagogicalScenarioViewBean);
				setSelectedPedagogicalScenarioViewBean(pedagogicalScenarioViewBean);
				MessageDisplayer.info(msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.TITLE",
						null, getSessionController().getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.DETAILS", null, getSessionController().getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.TITLE", null,
						getSessionController().getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.DETAILS", null, getSessionController().getCurrentUserLocale()),
						logger);
			}
		}
		else {
			// Update
			selectedPedagogicalScenarioViewBean.setName(objectEditedPedagogicalScenarioViewBean.getName());
			selectedPedagogicalScenarioViewBean.setObjective(objectEditedPedagogicalScenarioViewBean.getObjective());
			selectedPedagogicalScenarioViewBean.setEvaluationModes(objectEditedPedagogicalScenarioViewBean.getEvaluationModes());
			selectedPedagogicalScenarioViewBean.setScenario(scenariosService.updatePedagogicalScenario(selectedPedagogicalScenarioViewBean.getDomainBean()));
			MessageDisplayer.info(
					msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.TITLE",null,getSessionController().getCurrentUserLocale()),
					msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.DETAILS",null,getSessionController().getCurrentUserLocale()));
		}
		pedagogicalScenarioViewBeans.sortReverseOrder();
	}

	/*
	 * ****************** Pedagogical activities *******************
	 */
	public PedagogicalActivityViewBean getObjectEditedPedagogicalActivityViewBean() {
		return objectEditedPedagogicalActivityViewBean;
	}

	public void prepareCreatePedagogicalActivity() {
		objectEditedPedagogicalActivityViewBean = new PedagogicalActivityViewBean();
		preparePicklistToolCategoryViewBeansForPedagogicalActivity(null);
	}

	public void prepareModifyPedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		objectEditedPedagogicalActivityViewBean = new PedagogicalActivityViewBean(scenariosService.retrievePedagogicalActivity(
				pedagogicalActivityViewBean.getId(), true));
		preparePicklistToolCategoryViewBeansForPedagogicalActivity(pedagogicalActivityViewBean);
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
			if (scenariosService.deletePedagogicalActivity(pedagogicalActivityViewBean.getDomainBean().getId())) {
				// TODO : deal with real sequence and session (not necessarily at index 0)
				selectedPedagogicalScenarioViewBean.getPedagogicalSequenceViewBeans().get(0)
						.getPedagogicalSessionViewBeans().get(0).getPedagogicalActivityViewBeans()
						.remove(pedagogicalActivityViewBean);
				MessageDisplayer.info(msgs.getMessage(
						"MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.TITLE", null,
						getSessionController().getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.DETAILS", null,
						getSessionController().getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.info(msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.TITLE",
						null, getSessionController().getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.DETAILS", null,
						getSessionController().getCurrentUserLocale()));
			}
		}
	}

	public void onToolCategoryTransfer(TransferEvent event) {
		@SuppressWarnings("unchecked")
		List<ToolCategoryViewBean> listOfMovedViewBeans = (List<ToolCategoryViewBean>) event.getItems();
		for (ToolCategoryViewBean movedToolCategoryViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans().add(movedToolCategoryViewBean);
			}
			else {
				objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans().remove(movedToolCategoryViewBean);
			}
		}
	}

	/*
	 * ****************** Create/modify activity dialog ************
	 */
	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public void onSavePedagogicalActivity() {
		if (objectEditedPedagogicalActivityViewBean.getDomainBean()==null) {
			// Create
			PedagogicalActivity newPedagogicalActivity = scenariosService.createPedagogicalActivity(objectEditedPedagogicalActivityViewBean.getName());
			newPedagogicalActivity.setObjective(objectEditedPedagogicalActivityViewBean.getObjective());
			try {
				newPedagogicalActivity.setDuration(new Long(objectEditedPedagogicalActivityViewBean.getDuration()));
			}
			catch (NumberFormatException e) {
				newPedagogicalActivity.setDuration(0L);
			}
			newPedagogicalActivity.setInstructions(objectEditedPedagogicalActivityViewBean.getInstructions());

			// TODO : generalize these calls to deal with many sequences and many sessions
			PedagogicalSessionViewBean pedagogicalSessionViewBean = selectedPedagogicalScenarioViewBean.getPedagogicalSequenceViewBeans().get(0).getPedagogicalSessionViewBeans().get(0);
			PedagogicalSession pedagogicalSession = pedagogicalSessionViewBean.getDomainBean();
			PedagogicalActivity currentLastActivity = null;
			if (pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().size() > 0) {
				PedagogicalActivityViewBean currentLastActivityViewBean = pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().get(pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().size()-1);
				currentLastActivity = currentLastActivityViewBean.getDomainBean();
			}
			if (scenariosService.addActivityToSession(newPedagogicalActivity, pedagogicalSession, currentLastActivity, null)) {
				PedagogicalActivityViewBean pedagogicalActivityViewBean = new PedagogicalActivityViewBean(newPedagogicalActivity);
				pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().add(pedagogicalActivityViewBean);
			}

			for (ToolCategoryViewBean toolCategoryViewBean : objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				newPedagogicalActivity.getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(newPedagogicalActivity);
			}
			objectEditedPedagogicalActivityViewBean.setDomainBean(scenariosService.updatePedagogicalActivity(newPedagogicalActivity));
		}
		else {
			// Update
			objectEditedPedagogicalActivityViewBean.getDomainBean().getResourceCategories().clear();
			for (ToolCategoryViewBean toolCategoryViewBean : objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				objectEditedPedagogicalActivityViewBean.getDomainBean().getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(objectEditedPedagogicalActivityViewBean.getDomainBean());
			}
			objectEditedPedagogicalActivityViewBean.setDomainBean(scenariosService.updatePedagogicalActivity(objectEditedPedagogicalActivityViewBean.getDomainBean()));

			// TODO : generalize these calls to deal with many sequences and many sessions
			PedagogicalSessionViewBean pedagogicalSessionViewBean = selectedPedagogicalScenarioViewBean.getPedagogicalSequenceViewBeans().get(0).getPedagogicalSessionViewBeans().get(0);

			pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().set(pedagogicalSessionViewBean.getPedagogicalActivityViewBeans().indexOf(objectEditedPedagogicalActivityViewBean),  objectEditedPedagogicalActivityViewBean);
		}
		MessageDisplayer.info(
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.TITLE",null,getSessionController().getCurrentUserLocale()),
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.DETAILS",null,getSessionController().getCurrentUserLocale()));
	}

}
