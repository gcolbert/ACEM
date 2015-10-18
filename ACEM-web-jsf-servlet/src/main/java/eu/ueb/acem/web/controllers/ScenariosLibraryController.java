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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;

/**
 * Controller for the "Scenarios library" page.
 * 
 * @author Grégoire Colbert
 * @since 2015-01-06
 */
@Controller("scenariosLibraryController")
@Scope("view")
public class ScenariosLibraryController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 4148386461264162981L;

	@Override
	public String getPageTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(msgs.getMessage("MENU.SCENARIOS_LIBRARY",null,getSessionController().getCurrentUserLocale()));
		if (getSelectedScenarioViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedScenarioViewBean().getName());
		}
		return sb.toString();
	}

	public PedagogicalScenarioViewBean getSelectedScenarioViewBean() {
		return null; // TODO implement the method
	}
}
