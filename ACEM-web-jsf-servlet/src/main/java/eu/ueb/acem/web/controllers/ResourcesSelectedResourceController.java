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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-03-17
 * 
 */
@Controller("resourcesSelectedResourceController")
@Scope("view")
public class ResourcesSelectedResourceController extends AbstractContextAwareController {

	private static final long serialVersionUID = -9206373933131626589L;

	private static final Logger logger = LoggerFactory.getLogger(ResourcesSelectedResourceController.class);

	@Autowired
	private ResourcesService resourcesService;

	private ResourceViewBean selectedResourceViewBean;

	public ResourcesSelectedResourceController() {
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

	public void setSelectedResourceViewBean(ResourceViewBean selectedResourceViewBean) {
		this.selectedResourceViewBean = selectedResourceViewBean;
	}

	public void setSelectedResource(String resourceType, Long id) {
		logger.info("resourceType = {}, id = {}", resourceType, id);
		Ressource resource = resourcesService.getResource(resourceType, id);
		switch(resourceType) {
		case "software":
			break;
		case "softwareDocumentation":
			break;
		case "pedagogicalAndDocumentaryResource":
			break;
		case "equipment":
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}

		this.selectedResourceViewBean = selectedResourceViewBean;
	}

}
