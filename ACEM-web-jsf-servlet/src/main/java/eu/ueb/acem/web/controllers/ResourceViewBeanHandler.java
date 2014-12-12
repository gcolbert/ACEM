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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-28
 * 
 */
@Component("resourceViewBeanHandler")
@Scope("singleton")
class ResourceViewBeanHandler {

	private static final Logger logger = LoggerFactory.getLogger(ResourceViewBeanHandler.class);

	private Map<Long, ResourceViewBean> resourceViewBeans;

	@Inject
	private OrganisationViewBeanHandler organisationViewBeanHandler;
	
	@Inject
	private ResourcesService resourcesService;

	public ResourceViewBeanHandler() {
		resourceViewBeans = new HashMap<Long, ResourceViewBean>();
	}

	public ResourceViewBean getResourceViewBean(Long id) {
		ResourceViewBean viewBean = null;
		if (resourceViewBeans.containsKey(id)) {
			logger.info("resourceViewBean found in resourceViewBeans map, so we don't reload it.");
			viewBean = resourceViewBeans.get(id);
		}
		else {
			logger.info("resourceViewBean not found in resourceViewBeans map, we load it with ResourcesService.");
			Resource tool = resourcesService.retrieveResource(id);
			if (tool != null) {
				if (tool instanceof Software) {
					viewBean = new SoftwareViewBean((Software) tool);
				}
				else if (tool instanceof PedagogicalAndDocumentaryResource) {
					viewBean = new DocumentaryAndPedagogicalResourceViewBean((PedagogicalAndDocumentaryResource) tool);
				}
				else if (tool instanceof Equipment) {
					viewBean = new EquipmentViewBean((Equipment) tool);
				}
				else if (tool instanceof SoftwareDocumentation) {
					viewBean = new SoftwareDocumentationViewBean((SoftwareDocumentation) tool);
				}
				else if (tool instanceof ProfessionalTraining) {
					viewBean = new ProfessionalTrainingViewBean((ProfessionalTraining) tool);
				}

				viewBean.setOrganisationPossessingResourceViewBean(organisationViewBeanHandler
						.getOrganisationViewBean(tool.getOrganisationPossessingResource().getId()));

				for (Organisation organisation : tool.getOrganisationsHavingAccessToResource()) {
					viewBean.addOrganisationViewingResourceViewBean(organisationViewBeanHandler
							.getOrganisationViewBean(organisation.getId()));
				}

				resourceViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no tool with id={} according to ResourcesService", id);
			}
		}
		return viewBean;
	}

	public void removeResourceViewBean(Long id) {
		resourceViewBeans.remove(id);
	}
}