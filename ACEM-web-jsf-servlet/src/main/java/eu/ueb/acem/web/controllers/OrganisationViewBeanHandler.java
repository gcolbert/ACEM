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

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-28
 * 
 */
@Component("organisationViewBeanHandler")
@Scope("singleton")
public class OrganisationViewBeanHandler {

	private static final Logger logger = LoggerFactory.getLogger(OrganisationViewBeanHandler.class);

	private Map<Long, OrganisationViewBean> organisationViewBeans;

	@Inject
	private OrganisationsService organisationsService;

	public OrganisationViewBeanHandler() {
		organisationViewBeans = new HashMap<Long, OrganisationViewBean>();
	}
	
	public OrganisationViewBean getOrganisationViewBean(Long id) {
		OrganisationViewBean viewBean = null;
		if (organisationViewBeans.containsKey(id)) {
			logger.info("organisationViewBean found in organisationViewBeans map, so we don't reload it.");
			viewBean = organisationViewBeans.get(id);
		}
		else {
			logger.info("organisationViewBean not found in organisationViewBeans map, we load it with OrganisationsService.");
			Organisation organisation = organisationsService.retrieveOrganisation(id);
			if (organisation != null) {
				if (organisation instanceof Communaute) {
					viewBean = new CommunityViewBean((Communaute) organisation);
				}
				else if (organisation instanceof Etablissement) {
					viewBean = new InstitutionViewBean((Etablissement) organisation);
				}
				else if (organisation instanceof Service) {
					viewBean = new AdministrativeDepartmentViewBean((Service) organisation);
				}
				else if (organisation instanceof Composante) {
					viewBean = new TeachingDepartmentViewBean((Composante) organisation);
				}
				organisationViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no organisation with id={} according to OrganisationsService", id);
			}
		}
		return viewBean;
	}
	
}
