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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.OrganisationViewBeanGenerator;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-01-30
 * 
 */
@Controller("myServicesController")
@Scope("view")
public class MyServicesController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7481218140550087553L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyServicesController.class);

	@Inject
	private OrganisationsService organisationsService;
	
	private Long selectedServiceId;
	private OrganisationViewBean selectedServiceViewBean;

	@Override
	public String getPageTitle() {
		StringBuffer sb = new StringBuffer();
		sb.append(msgs.getMessage("MENU.MY_SERVICES",null,getCurrentUserLocale()));
		if (getSelectedServiceViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedServiceViewBean().getName());
		}
		return sb.toString();
	}

	public Long getSelectedServiceId() {
		return selectedServiceId;
	}

	public void setSelectedServiceId(Long serviceId) {
		logger.info("Entering setSelectedServiceId, serviceId = {}", serviceId);
		selectedServiceId = serviceId;

		Organisation service = organisationsService.retrieveOrganisation(serviceId, true);
		if (service != null) {
			setSelectedServiceViewBean(OrganisationViewBeanGenerator.getViewBean(service));
		}
		else {
			selectedServiceId = null;
			selectedServiceViewBean = null;
		}
		logger.info("Leaving setSelectedToolCategoryId, serviceId = {}", serviceId);
	}

	public OrganisationViewBean getSelectedServiceViewBean() {
		return selectedServiceViewBean;
	}

	public void setSelectedServiceViewBean(OrganisationViewBean organisationViewBean) {
		this.selectedServiceViewBean = organisationViewBean;
	}
	
}
