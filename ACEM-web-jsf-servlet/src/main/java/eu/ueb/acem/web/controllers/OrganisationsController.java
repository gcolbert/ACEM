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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;

/**
 * @author Grégoire Colbert @since 2014-02-19
 * 
 */
@Controller("organisationsController")
@Scope("view")
public class OrganisationsController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3854588801358138982L;

	@Autowired
	private OrganisationsService organisationsService;

	public void onCreateCommunity(String name) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateCommunity", name);
	}

	public void onCreateInstitution(String name) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateInstitution", name);
	}

	public void onCreateTeachingDepartment(String name) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateTeachingDepartment", name);
	}

	public void onCreateAdministrativeDepartment(String name) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateAdministrativeDepartment", name);
	}

	public void handleNewCommunityIconUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		MessageDisplayer.showMessageToUserWithSeverityInfo("handleNewCommunityIconUpload", file.getFileName());
	}
}
