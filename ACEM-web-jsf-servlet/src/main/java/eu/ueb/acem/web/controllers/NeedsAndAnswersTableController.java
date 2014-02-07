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
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.TableBean.TableEntry;

/**
 * @author Grégoire Colbert @since 2014-01-10
 * 
 */
@Controller("needsAndAnswersTableController")
@Scope("view")
public class NeedsAndAnswersTableController extends AbstractContextAwareController {

	private static final long serialVersionUID = -4992617416637974921L;

	/**
	 * For Logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTableController.class);

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;

	@Autowired
	private TableBean tableBean;

	private Reponse selectedAnswer;

	public NeedsAndAnswersTableController() {
		tableBean = new TableBean();
		selectedAnswer = null;
	}

	public Collection<TableBean.TableEntry> getScenariosRelatedToSelectedAnswer() {
		logger.info("entering getScenariosRelatedToSelectedAnswer");
		Collection<Scenario> scenarios = needsAndAnswersService.getScenariosRelatedToAnswer(selectedAnswer.getId());
		logger.info("Found {} scenarios related to answer {}.", scenarios.size(), selectedAnswer.getName());
		Collection<TableEntry> tableEntries = new ArrayList<TableEntry>();
		for (Scenario scenario : scenarios) {
			logger.info("scenario = {}", scenario.getName());
			Collection<ActivitePedagogique> steps = scenario.getPedagogicalActivities();
			Collection<Ressource> resources = new HashSet<Ressource>();
			for (ActivitePedagogique step : steps) {
				for (Ressource resource : step.getResources()) {
					resources.add(resource);
				}
			}
			tableEntries.add(new TableEntry(scenario.getName(), scenario.getAuthor().getName(), resources));
		}
		logger.info("leaving getScenariosRelatedToSelectedAnswer");
		logger.info("------");
		return tableEntries;
	}

	public void setSelectedAnswer(Long id) {
		selectedAnswer = needsAndAnswersService.retrieveAnswer(id);
		logger.info("setSelectedAnswer({})", selectedAnswer.getName());
	}

	public Reponse getSelectedAnswer() {
		return selectedAnswer;
	}

}
