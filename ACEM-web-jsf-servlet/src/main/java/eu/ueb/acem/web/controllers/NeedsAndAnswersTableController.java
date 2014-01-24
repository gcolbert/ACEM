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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.TableBean;

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
	private final static Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTableController.class);

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;

	@Autowired
	private TableBean tableBean;

	private Reponse selectedAnswer;

	public NeedsAndAnswersTableController() {
		tableBean = new TableBean();
		selectedAnswer = null;
	}

	public List<TableBean.TableEntry> getScenariosRelatedToSelectedAnswer() {
		logger.info("entering getScenariosRelatedToSelectedAnswer");
		/*
		Set<Besoin> needs = needsAndAnswersService.getScenariosRelatedToAnswer(answerId);
		logger.info("Found {} needs at root of tree.", needs.size());
		for (Besoin need : needs) {
			logger.info("need = {}", need.getName());
			createTree(need, editableTreeBean.getVisibleRoot());
		}
		*/
		logger.info("leaving getScenariosRelatedToSelectedAnswer");
		logger.info("------");
		return tableBean.getTableEntries();
	}

	public void setSelectedAnswer(Long id) {
		selectedAnswer = needsAndAnswersService.getReponseDAO().retrieveById(id);
		logger.info("setSelectedAnswer({})", selectedAnswer.getName());
	}

	public Reponse getSelectedAnswer() {
		return selectedAnswer;
	}

}
