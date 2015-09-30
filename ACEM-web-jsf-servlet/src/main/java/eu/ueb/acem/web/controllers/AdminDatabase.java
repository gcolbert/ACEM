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

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.services.InitDatabaseService;

/**
 * @author Grégoire Colbert
 * @since 2015-06-17
 */
@Controller("adminDatabaseController")
@Scope("view")
public class AdminDatabase extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3952785741841490379L;

	@Inject
	InitDatabaseService initDatabaseService;
	
	@Override
	public String getPageTitle() {
		return msgs.getMessage("ADMINISTRATION.DATABASE.HEADER", null, getCurrentUserLocale());
	}
	
	public void initDatabase() {
		initDatabaseService.initDatabase();
	}
}
