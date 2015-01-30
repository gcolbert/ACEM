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
package eu.ueb.acem.web.viewbeans.jaune;

import java.util.ArrayList;
import java.util.List;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-01-30
 * 
 */
public abstract class AbstractResourceViewBean extends AbstractViewBean implements ResourceViewBean {

	private String name;

	private String description;

	private String iconFileName;

	private OrganisationViewBean organisationPossessingResourceViewBean;
	
	private OrganisationViewBean organisationSupportingResourceViewBean;

	private List<OrganisationViewBean> organisationsViewingResourceViewBeans;

	private List<UseModeViewBean> useModeViewBeans;

	public AbstractResourceViewBean() {
		useModeViewBeans = new ArrayList<UseModeViewBean>();
		organisationsViewingResourceViewBeans = new ArrayList<OrganisationViewBean>();
	}
	
	@Override
	public abstract Resource getDomainBean();
	
	@Override
	public void setDomainBean(Resource resource) {
		setId(resource.getId());
		setName(resource.getName());
		setIconFileName(resource.getIconFileName());
		setDescription(resource.getDescription());

		for (UseMode useMode : resource.getUseModes()) {
			useModeViewBeans.add(new UseModeViewBean(useMode));
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		this.getDomainBean().setName(name);
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
		getDomainBean().setIconFileName(iconFileName);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		getDomainBean().setName(description);
	}

	@Override
	public OrganisationViewBean getOrganisationPossessingResourceViewBean() {
		return organisationPossessingResourceViewBean;
	}

	@Override
	public void setOrganisationPossessingResourceViewBean(OrganisationViewBean organisationViewBean) {
		this.organisationPossessingResourceViewBean = organisationViewBean;
		getDomainBean().setOrganisationPossessingResource(organisationViewBean.getDomainBean());
	}

	@Override
	public OrganisationViewBean getOrganisationSupportingResourceViewBean() {
		return organisationSupportingResourceViewBean;
	}

	@Override
	public void setOrganisationSupportingResourceViewBean(OrganisationViewBean organisationViewBean) {
		this.organisationSupportingResourceViewBean = organisationViewBean;
		getDomainBean().setOrganisationSupportingResource(organisationViewBean.getDomainBean());
	}
	
	@Override
	public List<OrganisationViewBean> getOrganisationViewingResourceViewBeans() {
		return organisationsViewingResourceViewBeans;
	}

	@Override
	public UseModeViewBean getUseModeViewBean() {
		// TODO : deal with many use modes for a given resource
		if (useModeViewBeans.size() > 0) {
			return useModeViewBeans.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public void setUseModeViewBean(UseModeViewBean useModeViewBean) {
		// TODO : deal with many use modes for a given resource
		this.useModeViewBeans.set(0, useModeViewBean);
	}

	@Override
	public List<UseModeViewBean> getUseModeViewBeans() {
		return useModeViewBeans;
	}

	@Override
	public void setUseModeViewBeans(List<UseModeViewBean> useModeViewBeans) {
		this.useModeViewBeans = useModeViewBeans;
	}

	@Override
	public int compareTo(ResourceViewBean o) {
		return name.compareTo(o.getName());
	}

}
