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

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.TableBean;
import eu.ueb.acem.web.viewbeans.rouge.AdministrativeDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.TeachingDepartmentViewBean;
import eu.ueb.acem.web.viewbeans.rouge.CommunityViewBean;
import eu.ueb.acem.web.viewbeans.rouge.InstitutionViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-19
 * 
 */
@Controller("organisationsController")
@Scope("view")
public class OrganisationsController extends AbstractContextAwareController {

	private static final Logger logger = LoggerFactory.getLogger(OrganisationsController.class);

	private static final long serialVersionUID = 3854588801358138982L;

	@Autowired
	private OrganisationsService organisationsService;

	private TableBean<CommunityViewBean> communityViewBeans;

	private TableBean<InstitutionViewBean> institutionViewBeans;

	private TableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans;

	private TableBean<TeachingDepartmentViewBean> teachingDepartmentViewBeans;

	private CommunityViewBean selectedCommunityViewBean;

	private InstitutionViewBean selectedInstitutionViewBean;

	private AdministrativeDepartmentViewBean selectedAdministrativeDepartmentViewBean;

	private TeachingDepartmentViewBean selectedTeachingDepartmentViewBean;

	public OrganisationsController() {
		// TODO : replace those instanciation with @Autowired when Spring 4 will be used
		communityViewBeans = new TableBean<CommunityViewBean>();
		institutionViewBeans = new TableBean<InstitutionViewBean>();
		administrativeDepartmentViewBeans = new TableBean<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new TableBean<TeachingDepartmentViewBean>();
	}

	@PostConstruct
	public void initOrganisationsController() {
		try {
			Collection<Communaute> communities = organisationsService.retrieveAllCommunities();
			logger.info("found {} communities", communities.size());
			communityViewBeans.getTableEntries().clear();
			for (Communaute community : communities) {
				logger.info("community = {}", community.getName());
				communityViewBeans.getTableEntries().add(new CommunityViewBean(community));
			}
			communityViewBeans.sort();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TableBean<CommunityViewBean> getCommunityViewBeans() {
		return communityViewBeans;
	}

	public void setCommunityViewBeans(TableBean<CommunityViewBean> communityViewBeans) {
		this.communityViewBeans = communityViewBeans;
	}

	public TableBean<InstitutionViewBean> getInstitutionViewBeans() {
		return institutionViewBeans;
	}

	public void setInstitutionViewBeans(TableBean<InstitutionViewBean> institutionViewBeans) {
		this.institutionViewBeans = institutionViewBeans;
	}

	public TableBean<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeans() {
		return administrativeDepartmentViewBeans;
	}

	public void setAdministrativeDepartmentViewBeans(
			TableBean<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeans) {
		this.administrativeDepartmentViewBeans = administrativeDepartmentViewBeans;
	}

	public TableBean<TeachingDepartmentViewBean> getTeachingDepartmentViewBeans() {
		return teachingDepartmentViewBeans;
	}

	public void setTeachingDepartmentViewBeans(TableBean<TeachingDepartmentViewBean> teachingDepartmentViewBeans) {
		this.teachingDepartmentViewBeans = teachingDepartmentViewBeans;
	}

	public CommunityViewBean getSelectedCommunityViewBean() {
		return selectedCommunityViewBean;
	}

	public void setSelectedCommunityViewBean(CommunityViewBean selectedCommunityViewBean) {
		this.selectedCommunityViewBean = selectedCommunityViewBean;
	}

	public InstitutionViewBean getSelectedInstitutionViewBean() {
		return selectedInstitutionViewBean;
	}

	public void setSelectedInstitutionViewBean(InstitutionViewBean selectedInstitutionViewBean) {
		this.selectedInstitutionViewBean = selectedInstitutionViewBean;
	}

	public AdministrativeDepartmentViewBean getSelectedAdministrativeDepartmentViewBean() {
		return selectedAdministrativeDepartmentViewBean;
	}

	public void setSelectedAdministrativeDepartmentViewBean(
			AdministrativeDepartmentViewBean selectedAdministrativeDepartmentViewBean) {
		this.selectedAdministrativeDepartmentViewBean = selectedAdministrativeDepartmentViewBean;
	}

	public TeachingDepartmentViewBean getSelectedTeachingDepartmentViewBean() {
		return selectedTeachingDepartmentViewBean;
	}

	public void setSelectedTeachingDepartmentViewBean(TeachingDepartmentViewBean selectedTeachingDepartmentViewBean) {
		this.selectedTeachingDepartmentViewBean = selectedTeachingDepartmentViewBean;
	}

	public void onCommunityTabChange(TabChangeEvent event) {
		logger.info("onCommunityTabChange");
		setSelectedCommunityViewBean((CommunityViewBean) event.getData());
	}
	
	public void onCommunityRowSelect(SelectEvent event) {
		logger.info("onCommunityRowSelect");
		setSelectedCommunityViewBean((CommunityViewBean) event.getObject());
	}

	public void onInstitutionRowSelect(SelectEvent event) {
		logger.info("onInstitutionRowSelect");
		setSelectedInstitutionViewBean((InstitutionViewBean) event.getObject());
	}

	public void onTeachingDepartmentRowSelect(SelectEvent event) {
		logger.info("onTeachingDepartmentRowSelect");
		setSelectedTeachingDepartmentViewBean((TeachingDepartmentViewBean) event.getObject());
	}

	public void onAdministrativeDepartmentRowSelect(SelectEvent event) {
		logger.info("onAdministrativeDepartmentRowSelect");
		setSelectedAdministrativeDepartmentViewBean((AdministrativeDepartmentViewBean) event.getObject());
	}
	
	public void onCreateCommunity(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateCommunity", name);
		Communaute community = organisationsService.createCommunity(name, shortname);
		communityViewBeans.getTableEntries().add(new CommunityViewBean(community));
	}

	public void onCreateInstitution(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateInstitution", name);
	}

	public void onCreateTeachingDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateTeachingDepartment", name);
	}

	public void onCreateAdministrativeDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateAdministrativeDepartment", name);
	}

	public void handleNewCommunityIconUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		MessageDisplayer.showMessageToUserWithSeverityInfo("handleNewCommunityIconUpload", file.getFileName());
	}
}
