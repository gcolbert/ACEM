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

import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;

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

	private Long selectedResourceId;

	public ResourcesSelectedResourceController() {
	}

	public void setSelectedResourceId(Long resourceId) {
		logger.info("resourceId={}", resourceId);
		this.selectedResourceId = resourceId;
		Ressource resource = resourcesService.getResource(resourceId);
		if (resource != null) {
			if (resource instanceof Applicatif) {
				selectedResourceViewBean = new SoftwareViewBean();
			}
			else if (resource instanceof DocumentationApplicatif) {
				selectedResourceViewBean = new SoftwareDocumentationViewBean();
			}
			else if (resource instanceof Equipement) {
				selectedResourceViewBean = new EquipmentViewBean();
			}
			else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
				selectedResourceViewBean = new DocumentaryAndPedagogicalResourceViewBean();
			}
			selectedResourceViewBean.setDomainBean(resource);
		}
	}

	public ResourceViewBean getSelectedResourceViewBean() {
		return selectedResourceViewBean;
	}

}
