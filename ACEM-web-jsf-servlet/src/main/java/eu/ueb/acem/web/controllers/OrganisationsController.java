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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
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

	private PickListBean<CommunityViewBean> pickListCommunityViewBeans;

	private PickListBean<InstitutionViewBean> pickListInstitutionViewBeans;

	private PickListBean<AdministrativeDepartmentViewBean> pickListAdministrativeDepartmentViewBeans;

	private PickListBean<TeachingDepartmentViewBean> pickListTeachingDepartmentViewBeans;

	public OrganisationsController() {
		// TODO : replace those hard-wired instanciations with @Autowired when
		// Spring 4 will be used
		communityViewBeans = new TableBean<CommunityViewBean>();
		institutionViewBeans = new TableBean<InstitutionViewBean>();
		administrativeDepartmentViewBeans = new TableBean<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new TableBean<TeachingDepartmentViewBean>();
		pickListCommunityViewBeans = new PickListBean<CommunityViewBean>();
		pickListInstitutionViewBeans = new PickListBean<InstitutionViewBean>();
		pickListAdministrativeDepartmentViewBeans = new PickListBean<AdministrativeDepartmentViewBean>();
		pickListTeachingDepartmentViewBeans = new PickListBean<TeachingDepartmentViewBean>();
	}

	@PostConstruct
	public void initOrganisationsController() {
		try {
			Collection<Communaute> communities = organisationsService.retrieveAllCommunities();
			logger.info("found {} communities", communities.size());
			communityViewBeans.getTableEntries().clear();
			for (Communaute community : communities) {
				logger.info("community = {}", community.getName());
				CommunityViewBean communityViewBean = new CommunityViewBean(community);
				communityViewBeans.getTableEntries().add(communityViewBean);
				pickListCommunityViewBeans.addSourceEntity(communityViewBean);
			}
			communityViewBeans.sort();

			Collection<Etablissement> institutions = organisationsService.retrieveAllInstitutions();
			logger.info("found {} institutions", institutions.size());
			institutionViewBeans.getTableEntries().clear();
			for (Etablissement institution : institutions) {
				logger.info("institution = {}", institution.getName());
				InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
				institutionViewBeans.getTableEntries().add(institutionViewBean);
				pickListInstitutionViewBeans.addSourceEntity(institutionViewBean);
			}
			institutionViewBeans.sort();

			Collection<Service> administrativeDepartments = organisationsService.retrieveAllAdministrativeDepartments();
			logger.info("found {} administrative departments", administrativeDepartments.size());
			administrativeDepartmentViewBeans.getTableEntries().clear();
			for (Service administrativeDepartment : administrativeDepartments) {
				logger.info("administrative department = {}", administrativeDepartment.getName());
				AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
						administrativeDepartment);
				administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
				pickListAdministrativeDepartmentViewBeans.addSourceEntity(administrativeDepartmentViewBean);
			}
			administrativeDepartmentViewBeans.sort();

			Collection<Composante> teachingDepartments = organisationsService.retrieveAllTeachingDepartments();
			logger.info("found {} teaching departments", teachingDepartments.size());
			teachingDepartmentViewBeans.getTableEntries().clear();
			for (Composante teachingDepartment : teachingDepartments) {
				logger.info("teaching department = {}", teachingDepartment.getName());
				TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(
						teachingDepartment);
				teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
				pickListTeachingDepartmentViewBeans.addSourceEntity(teachingDepartmentViewBean);
			}
			teachingDepartmentViewBeans.sort();
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

	public PickListBean<CommunityViewBean> getPickListCommunityViewBeans() {
		return pickListCommunityViewBeans;
	}

	public void setPickListCommunityViewBeans(PickListBean<CommunityViewBean> pickListCommunityViewBeans) {
		this.pickListCommunityViewBeans = pickListCommunityViewBeans;
	}

	public PickListBean<InstitutionViewBean> getPickListInstitutionViewBeans() {
		return pickListInstitutionViewBeans;
	}

	public void setPickListInstitutionViewBeans(PickListBean<InstitutionViewBean> pickListInstitutionViewBeans) {
		this.pickListInstitutionViewBeans = pickListInstitutionViewBeans;
	}

	public PickListBean<AdministrativeDepartmentViewBean> getPickListAdministrativeDepartmentViewBeans() {
		return pickListAdministrativeDepartmentViewBeans;
	}

	public void setPickListAdministrativeDepartmentViewBeans(
			PickListBean<AdministrativeDepartmentViewBean> pickListAdministrativeDepartmentViewBeans) {
		this.pickListAdministrativeDepartmentViewBeans = pickListAdministrativeDepartmentViewBeans;
	}

	public PickListBean<TeachingDepartmentViewBean> getPickListTeachingDepartmentViewBeans() {
		return pickListTeachingDepartmentViewBeans;
	}

	public void setPickListTeachingDepartmentViewBeans(
			PickListBean<TeachingDepartmentViewBean> pickListTeachingDepartmentViewBeans) {
		this.pickListTeachingDepartmentViewBeans = pickListTeachingDepartmentViewBeans;
	}

	public void onCommunityAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onCommunityAccordionPanelTabChange, tab={}", event.getTab());
		setSelectedCommunityViewBean((CommunityViewBean) event.getData());
	}

	public void onInstitutionAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onInstitutionAccordionPanelTabChange, tab={}", event.getTab());
		setSelectedInstitutionViewBean((InstitutionViewBean) event.getData());
	}

	public void onAdministrativeDepartmentAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onAdministrativeDepartmentAccordionPanelTabChange, tab={}", event.getTab());
		setSelectedAdministrativeDepartmentViewBean((AdministrativeDepartmentViewBean) event.getData());
	}

	public void onTeachingDepartmentAccordionPanelTabChange(TabChangeEvent event) {
		logger.info("onTeachingDepartmentAccordionPanelTabChange, tab={}", event.getTab());
		setSelectedTeachingDepartmentViewBean((TeachingDepartmentViewBean) event.getData());
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
		CommunityViewBean communityViewBean = new CommunityViewBean(community);
		pickListCommunityViewBeans.addSourceEntity(communityViewBean);
		communityViewBeans.getTableEntries().add(communityViewBean);
		communityViewBeans.sort();
	}

	public void onCreateInstitution(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateInstitution", name);
		Etablissement institution = organisationsService.createInstitution(name, shortname);
		InstitutionViewBean institutionViewBean = new InstitutionViewBean(institution);
		pickListInstitutionViewBeans.addSourceEntity(institutionViewBean);
		institutionViewBeans.getTableEntries().add(institutionViewBean);
		institutionViewBeans.sort();
	}

	public void onCreateTeachingDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateTeachingDepartment", name);
		Composante teachingDepartment = organisationsService.createTeachingDepartment(name, shortname);
		TeachingDepartmentViewBean teachingDepartmentViewBean = new TeachingDepartmentViewBean(teachingDepartment);
		pickListTeachingDepartmentViewBeans.addSourceEntity(teachingDepartmentViewBean);
		teachingDepartmentViewBeans.getTableEntries().add(teachingDepartmentViewBean);
		teachingDepartmentViewBeans.sort();
	}

	public void onCreateAdministrativeDepartment(String name, String shortname) {
		MessageDisplayer.showMessageToUserWithSeverityInfo("onCreateAdministrativeDepartment", name);
		Service administrativeDepartment = organisationsService.createAdministrativeDepartment(name, shortname);
		AdministrativeDepartmentViewBean administrativeDepartmentViewBean = new AdministrativeDepartmentViewBean(
				administrativeDepartment);
		pickListAdministrativeDepartmentViewBeans.addSourceEntity(administrativeDepartmentViewBean);
		administrativeDepartmentViewBeans.getTableEntries().add(administrativeDepartmentViewBean);
		administrativeDepartmentViewBeans.sort();
	}

	public void onAssociateCommunity(TransferEvent event) {
		for (CommunityViewBean communityViewBean : pickListCommunityViewBeans.getPickListEntities().getSource()) {
			if (selectedInstitutionViewBean.getCommunityViewBeans().contains(communityViewBean)) {
				logger.info("We should dissociate {} and {}", communityViewBean.getName(), selectedInstitutionViewBean.getName());
				if (organisationsService.dissociateCommunityAndInstitution(communityViewBean.getCommunity().getId(), selectedInstitutionViewBean.getInstitution().getId())) {
					logger.info("successfully dissociated, we modify the view beans");
				}
				selectedInstitutionViewBean.getCommunityViewBeans().remove(communityViewBean);
			}
		}
		for (CommunityViewBean communityViewBean : pickListCommunityViewBeans.getPickListEntities().getTarget()) {
			if (!selectedInstitutionViewBean.getCommunityViewBeans().contains(communityViewBean)) {
				logger.info("We should associate {} and {}", communityViewBean.getName(), selectedInstitutionViewBean.getName());
				if (organisationsService.associateCommunityAndInstitution(communityViewBean.getCommunity().getId(), selectedInstitutionViewBean.getInstitution().getId())) {
					logger.info("successfully associated, we modify the view beans");
				}
				selectedInstitutionViewBean.getCommunityViewBeans().add(communityViewBean);
			}
		}
	}

	public void handleNewCommunityIconUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		MessageDisplayer.showMessageToUserWithSeverityInfo("handleNewCommunityIconUpload", file.getFileName());
	}
}
