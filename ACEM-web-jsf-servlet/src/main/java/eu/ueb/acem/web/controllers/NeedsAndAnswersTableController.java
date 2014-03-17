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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.SortableTableBean;

/**
 * @author Grégoire Colbert
 * @since 2014-01-10
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
	private SortableTableBean<Scenario> sortableTableBean;

	private Reponse selectedAnswer;

	public NeedsAndAnswersTableController() {
		selectedAnswer = null;
	}

	public void setScenariosRelatedToSelectedAnswer() {
		if (selectedAnswer != null) {
			logger.info("entering setScenariosRelatedToSelectedAnswer");
			Collection<Scenario> scenarios = needsAndAnswersService.getScenariosRelatedToAnswer(selectedAnswer.getId());
			logger.info("Found {} scenarios related to answer {}.", scenarios.size(), selectedAnswer.getName());
			sortableTableBean.getTableEntries().clear();
			for (Scenario scenario : scenarios) {
				sortableTableBean.getTableEntries().add(scenario);
			}
			logger.info("leaving setScenariosRelatedToSelectedAnswer");
			logger.info("------");
		}
	}

	public Collection<Scenario> getScenariosRelatedToSelectedAnswer() {
		return sortableTableBean.getTableEntries();
	}

	public void setSelectedAnswer(Long id) {
		selectedAnswer = needsAndAnswersService.retrieveAnswer(id);
		setScenariosRelatedToSelectedAnswer();
		logger.info("setSelectedAnswer({})", selectedAnswer.getName());
	}

	public Reponse getSelectedAnswer() {
		return selectedAnswer;
	}

}
