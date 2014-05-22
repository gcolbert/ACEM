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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.ModaliteUtilisation;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
public class DocumentaryAndPedagogicalResourceViewBean implements ResourceViewBean, Serializable, Comparable<DocumentaryAndPedagogicalResourceViewBean> {

	private static final long serialVersionUID = 2189588973638154852L;

	private static final Logger logger = LoggerFactory.getLogger(DocumentaryAndPedagogicalResourceViewBean.class);

	private RessourcePedagogiqueEtDocumentaire documentaryAndPedagogicalResource;

	private Long id;

	private String name;

	private String description;
	
	private String iconFileName;
	
	private OrganisationViewBean organisationPossessingResourceViewBean;
	
	private List<OrganisationViewBean> organisationsViewingResourceViewBeans;
	
	private List<UseModeViewBean> useModeViewBeans;
	
	public DocumentaryAndPedagogicalResourceViewBean() {
		useModeViewBeans = new ArrayList<UseModeViewBean>();
	}

	public DocumentaryAndPedagogicalResourceViewBean(RessourcePedagogiqueEtDocumentaire documentaryAndPedagogicalResource) {
		this();
		setDocumentaryAndPedagogicalResource(documentaryAndPedagogicalResource);
	}

	@Override
	public RessourcePedagogiqueEtDocumentaire getDomainBean() {
		return documentaryAndPedagogicalResource;
	}
	
	@Override
	public void setDomainBean(Ressource resource) {
		setDocumentaryAndPedagogicalResource((RessourcePedagogiqueEtDocumentaire) resource);
	}

	public RessourcePedagogiqueEtDocumentaire getDocumentaryAndPedagogical() {
		return documentaryAndPedagogicalResource;
	}

	public void setDocumentaryAndPedagogicalResource(RessourcePedagogiqueEtDocumentaire documentaryAndPedagogicalResource) {
		this.documentaryAndPedagogicalResource = documentaryAndPedagogicalResource;
		setId(documentaryAndPedagogicalResource.getId());
		setName(documentaryAndPedagogicalResource.getName());
		setIconFileName(documentaryAndPedagogicalResource.getIconFileName());
		setDescription(documentaryAndPedagogicalResource.getDescription());

		for (ModaliteUtilisation useMode : documentaryAndPedagogicalResource.getUseModes()) {
			logger.info("UseModeViewBean.setDomainBean : we add the useMode '{}' as a UseMode", useMode.getName());
			useModeViewBeans.add(new UseModeViewBean(useMode));
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

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
	public List<OrganisationViewBean> getOrganisationViewingResourceViewBeans() {
		return organisationsViewingResourceViewBeans;
	}
	
	@Override
	public void addOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean) {
		organisationsViewingResourceViewBeans.add(organisationViewBean);
	}

	@Override
	public void removeOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean) {
		organisationsViewingResourceViewBeans.remove(organisationViewBean);
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
	public int compareTo(DocumentaryAndPedagogicalResourceViewBean o) {
		return name.compareTo(o.getName());
	}

}
