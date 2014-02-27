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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
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
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;
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

	private PickListBean pickListCommunityViewBeans;
	private PickListBean pickListInstitutionViewBeans;
	private PickListBean pickListAdministrativeDepartmentViewBeans;
	private PickListBean pickListTeachingDepartmentViewBeans;

	private List<InstitutionViewBean> institutionViewBeansForSelectedCommunity;
	private List<CommunityViewBean> communityViewBeansForSelectedInstitution;
	private List<AdministrativeDepartmentViewBean> administrativeDepartmentViewBeansForSelectedInstitution;
	private List<TeachingDepartmentViewBean> teachingDepartmentViewBeansForSelectedInstitution;
	private List<InstitutionViewBean> institutionViewBeansForSelectedAdministrativeDepartment;
	private List<InstitutionViewBean> institutionViewBeansForSelectedTeachingDepartment;

	private OrganisationViewBean currentOrganisationViewBean;
	
	public OrganisationsController() {
		// TODO : replace those hard-wired instanciations with @Autowired when
		// Spring 4 will be used
		communityViewBeans = new TableBean<CommunityViewBean>();
		institutionViewBeans = new TableBean<InstitutionViewBean>();
		administrativeDepartmentViewBeans = new TableBean<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeans = new TableBean<TeachingDepartmentViewBean>();

		pickListCommunityViewBeans = new PickListBean();
		pickListInstitutionViewBeans = new PickListBean();
		pickListAdministrativeDepartmentViewBeans = new PickListBean();
		pickListTeachingDepartmentViewBeans = new PickListBean();

		institutionViewBeansForSelectedCommunity = new ArrayList<InstitutionViewBean>();
		communityViewBeansForSelectedInstitution = new ArrayList<CommunityViewBean>();
		administrativeDepartmentViewBeansForSelectedInstitution = new ArrayList<AdministrativeDepartmentViewBean>();
		teachingDepartmentViewBeansForSelectedInstitution = new ArrayList<TeachingDepartmentViewBean>();
		institutionViewBeansForSelectedAdministrativeDepartment = new ArrayList<InstitutionViewBean>();
		institutionViewBeansForSelectedTeachingDepartment = new ArrayList<InstitutionViewBean>();
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

	public PickListBean getPickListCommunityViewBeans() {
		return pickListCommunityViewBeans;
	}

	public PickListBean getPickListInstitutionViewBeans() {
		return pickListInstitutionViewBeans;
	}

	public PickListBean getPickListAdministrativeDepartmentViewBeans() {
		return pickListAdministrativeDepartmentViewBeans;
	}

	public PickListBean getPickListTeachingDepartmentViewBeans() {
		return pickListTeachingDepartmentViewBeans;
	}

	public OrganisationViewBean getCurrentOrganisationViewBean() {
		return currentOrganisationViewBean;
	}

	public void setCurrentOrganisationViewBean(OrganisationViewBean currentOrganisationViewBean) {
		this.currentOrganisationViewBean = currentOrganisationViewBean;
	}

	public TableBean<CommunityViewBean> getCommunityViewBeans() {
		return communityViewBeans;
	}

	public CommunityViewBean getSelectedCommunityViewBean() {
		return selectedCommunityViewBean;
	}

	public void setSelectedCommunityViewBean(CommunityViewBean communityViewBean) {
		this.selectedCommunityViewBean = communityViewBean;
		setCurrentOrganisationViewBean(communityViewBean);
		setInstitutionViewBeansForSelectedCommunity();
		preparePicklistInstitutionViewBeansForSelectedCommunity();
	}

	public List<InstitutionViewBean> getInstitutionViewBeansForSelectedCommunity() {
		return institutionViewBeansForSelectedCommunity;
	}

	private void setInstitutionViewBeansForSelectedCommunity() {
		logger.info("setInstitutionViewBeansForSelectedCommunity - selectedCommunity = {}",
				selectedCommunityViewBean.getName());
		if (selectedCommunityViewBean != null) {
			institutionViewBeansForSelectedCommunity.clear();
			for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
				if (selectedCommunityViewBean.getCommunity().getInstitutions()
						.contains(institutionViewBean.getInstitution())) {
					logger.info("selectedCommunity is associated with {}", institutionViewBean.getInstitution()
							.getName());
					institutionViewBeansForSelectedCommunity.add(institutionViewBean);
				}
			}
		}
	}

	public void preparePicklistInstitutionViewBeansForSelectedCommunity() {
		logger.info("preparePicklistInstitutionViewBeansForSelectedCommunity - selectedCommunity = {}",
				selectedCommunityViewBean.getName());
		if (selectedCommunityViewBean != null) {
			pickListInstitutionViewBeans.getPickListEntities().getSource().clear();
			pickListInstitutionViewBeans.getPickListEntities().getSource()
					.addAll(institutionViewBeans.getTableEntries());
			pickListInstitutionViewBeans.getPickListEntities().getTarget().clear();
			for (Etablissement institutionAssociatedWithSelectedCommunity : selectedCommunityViewBean.getCommunity()
					.getInstitutions()) {
				for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
					if (institutionAssociatedWithSelectedCommunity.getId().equals(institutionViewBean.getId())) {
						pickListInstitutionViewBeans.getPickListEntities().getSource().remove(institutionViewBean);
						pickListInstitutionViewBeans.getPickListEntities().getTarget().add(institutionViewBean);
					}
				}
			}
		}
	}

	public TableBean<InstitutionViewBean> getInstitutionViewBeans() {
		return institutionViewBeans;
	}

	public InstitutionViewBean getSelectedInstitutionViewBean() {
		return selectedInstitutionViewBean;
	}

	public void setSelectedInstitutionViewBean(InstitutionViewBean institutionViewBean) {
		this.selectedInstitutionViewBean = institutionViewBean;
		setCurrentOrganisationViewBean(institutionViewBean);
		setCommunityViewBeansForSelectedInstitution();
		preparePicklistCommunityViewBeansForSelectedInstitution();
		setAdministrativeDepartmentViewBeansForSelectedInstitution();
		preparePicklistAdministrativeDepartmentViewBeansForSelectedInstitution();
		setTeachingDepartmentViewBeansForSelectedInstitution();
		preparePicklistTeachingDepartmentViewBeansForSelectedInstitution();
	}

	public List<CommunityViewBean> getCommunityViewBeansForSelectedInstitution() {
		return communityViewBeansForSelectedInstitution;
	}

	private void setCommunityViewBeansForSelectedInstitution() {
		logger.info("setCommunityViewBeansForSelectedInstitution");
		if (selectedInstitutionViewBean != null) {
			communityViewBeansForSelectedInstitution.clear();
			for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
				if (selectedInstitutionViewBean.getInstitution().getCommunities()
						.contains(communityViewBean.getCommunity())) {
					communityViewBeansForSelectedInstitution.add(communityViewBean);
				}
			}
		}
	}

	public void preparePicklistCommunityViewBeansForSelectedInstitution() {
		logger.info("preparePicklistCommunityViewBeansForSelectedInstitution - selectedInstitution = {}",
				selectedInstitutionViewBean.getName());
		if (selectedInstitutionViewBean != null) {
			pickListCommunityViewBeans.getPickListEntities().getSource().clear();
			pickListCommunityViewBeans.getPickListEntities().getSource().addAll(communityViewBeans.getTableEntries());
			pickListCommunityViewBeans.getPickListEntities().getTarget().clear();
			for (Communaute communityAssociatedWithSelectedInstitution : selectedInstitutionViewBean.getInstitution()
					.getCommunities()) {
				for (CommunityViewBean communityViewBean : communityViewBeans.getTableEntries()) {
					if (communityAssociatedWithSelectedInstitution.getId().equals(communityViewBean.getId())) {
						pickListCommunityViewBeans.getPickListEntities().getSource().remove(communityViewBean);
						pickListCommunityViewBeans.getPickListEntities().getTarget().add(communityViewBean);
					}
				}
			}
		}
	}

	public List<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeansForSelectedInstitution() {
		return administrativeDepartmentViewBeansForSelectedInstitution;
	}

	private void setAdministrativeDepartmentViewBeansForSelectedInstitution() {
		logger.info("setAdministrativeDepartmentViewBeansForSelectedInstitution");
		if (selectedInstitutionViewBean != null) {
			administrativeDepartmentViewBeansForSelectedInstitution.clear();
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
					.getTableEntries()) {
				if (selectedInstitutionViewBean.getInstitution().getAdministrativeDepartments()
						.contains(administrativeDepartmentViewBean.getAdministrativeDepartment())) {
					administrativeDepartmentViewBeansForSelectedInstitution.add(administrativeDepartmentViewBean);
				}
			}
		}
	}

	public void preparePicklistAdministrativeDepartmentViewBeansForSelectedInstitution() {
		logger.info("preparePicklistAdministrativeDepartmentViewBeansForSelectedInstitution - selectedInstitution = {}",
				selectedInstitutionViewBean.getName());
		if (selectedInstitutionViewBean != null) {
			pickListAdministrativeDepartmentViewBeans.getPickListEntities().getSource().clear();
			pickListAdministrativeDepartmentViewBeans.getPickListEntities().getSource()
					.addAll(administrativeDepartmentViewBeans.getTableEntries());
			pickListAdministrativeDepartmentViewBeans.getPickListEntities().getTarget().clear();
			for (Service administrativeDepartmentAssociatedWithSelectedInstitution : selectedInstitutionViewBean
					.getInstitution().getAdministrativeDepartments()) {
				for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : administrativeDepartmentViewBeans
						.getTableEntries()) {
					if (administrativeDepartmentAssociatedWithSelectedInstitution.getId().equals(
							administrativeDepartmentViewBean.getId())) {
						pickListAdministrativeDepartmentViewBeans.getPickListEntities().getSource()
								.remove(administrativeDepartmentViewBean);
						pickListAdministrativeDepartmentViewBeans.getPickListEntities().getTarget()
								.add(administrativeDepartmentViewBean);
					}
				}
			}
		}
	}

	public List<TeachingDepartmentViewBean> getTeachingDepartmentViewBeansForSelectedInstitution() {
		return teachingDepartmentViewBeansForSelectedInstitution;
	}

	private void setTeachingDepartmentViewBeansForSelectedInstitution() {
		logger.info("setTeachingDepartmentViewBeansForSelectedInstitution");
		if (selectedInstitutionViewBean != null) {
			teachingDepartmentViewBeansForSelectedInstitution.clear();
			for (TeachingDepartmentViewBean teachingDepartmentViewBean : teachingDepartmentViewBeans.getTableEntries()) {
				if (selectedInstitutionViewBean.getInstitution().getTeachingDepartments()
						.contains(teachingDepartmentViewBean.getTeachingDepartment())) {
					teachingDepartmentViewBeansForSelectedInstitution.add(teachingDepartmentViewBean);
				}
			}
		}
	}

	public void preparePicklistTeachingDepartmentViewBeansForSelectedInstitution() {
		logger.info("preparePicklistTeachingDepartmentViewBeansForSelectedInstitution - selectedInstitution = {}",
				selectedInstitutionViewBean.getName());
		if (selectedInstitutionViewBean != null) {
			pickListTeachingDepartmentViewBeans.getPickListEntities().getSource().clear();
			pickListTeachingDepartmentViewBeans.getPickListEntities().getSource()
					.addAll(teachingDepartmentViewBeans.getTableEntries());
			pickListTeachingDepartmentViewBeans.getPickListEntities().getTarget().clear();
			for (Composante teachingDepartmentAssociatedWithSelectedInstitution : selectedInstitutionViewBean
					.getInstitution().getTeachingDepartments()) {
				for (TeachingDepartmentViewBean teachingDepartmentViewBean : teachingDepartmentViewBeans
						.getTableEntries()) {
					if (teachingDepartmentAssociatedWithSelectedInstitution.getId().equals(
							teachingDepartmentViewBean.getId())) {
						pickListTeachingDepartmentViewBeans.getPickListEntities().getSource()
								.remove(teachingDepartmentViewBean);
						pickListTeachingDepartmentViewBeans.getPickListEntities().getTarget()
								.add(teachingDepartmentViewBean);
					}
				}
			}
		}
	}

	public TableBean<AdministrativeDepartmentViewBean> getAdministrativeDepartmentViewBeans() {
		return administrativeDepartmentViewBeans;
	}

	public AdministrativeDepartmentViewBean getSelectedAdministrativeDepartmentViewBean() {
		return selectedAdministrativeDepartmentViewBean;
	}

	public void setSelectedAdministrativeDepartmentViewBean(
			AdministrativeDepartmentViewBean administrativeDepartmentViewBean) {
		this.selectedAdministrativeDepartmentViewBean = administrativeDepartmentViewBean;
		setCurrentOrganisationViewBean(administrativeDepartmentViewBean);
		setInstitutionViewBeansForSelectedAdministrativeDepartment();
		preparePicklistInstitutionViewBeansForSelectedAdministrativeDepartment();
	}

	public List<InstitutionViewBean> getInstitutionViewBeansForSelectedAdministrativeDepartment() {
		return institutionViewBeansForSelectedAdministrativeDepartment;
	}

	private void setInstitutionViewBeansForSelectedAdministrativeDepartment() {
		logger.info("setInstitutionViewBeansForSelectedAdministrativeDepartment");
		if (selectedAdministrativeDepartmentViewBean != null) {
			institutionViewBeansForSelectedAdministrativeDepartment.clear();
			for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
				if (selectedAdministrativeDepartmentViewBean.getAdministrativeDepartment().getInstitutions()
						.contains(institutionViewBean.getInstitution())) {
					institutionViewBeansForSelectedAdministrativeDepartment.add(institutionViewBean);
				}
			}
		}
	}

	public void preparePicklistInstitutionViewBeansForSelectedAdministrativeDepartment() {
		logger.info("preparePicklistInstitutionViewBeansForSelectedAdministrativeDepartment - selectedAdministrativeDepartment = {}",
				selectedAdministrativeDepartmentViewBean.getName());
		if (selectedAdministrativeDepartmentViewBean != null) {
			pickListInstitutionViewBeans.getPickListEntities().getSource().clear();
			pickListInstitutionViewBeans.getPickListEntities().getSource()
					.addAll(institutionViewBeans.getTableEntries());
			pickListInstitutionViewBeans.getPickListEntities().getTarget().clear();
			for (Etablissement institutionAssociatedWithSelectedAdministrativeDepartment : selectedAdministrativeDepartmentViewBean
					.getAdministrativeDepartment().getInstitutions()) {
				for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
					if (institutionAssociatedWithSelectedAdministrativeDepartment.getId().equals(
							institutionViewBean.getId())) {
						pickListInstitutionViewBeans.getPickListEntities().getSource().remove(institutionViewBean);
						pickListInstitutionViewBeans.getPickListEntities().getTarget().add(institutionViewBean);
					}
				}
			}
		}
	}

	public TableBean<TeachingDepartmentViewBean> getTeachingDepartmentViewBeans() {
		return teachingDepartmentViewBeans;
	}

	public TeachingDepartmentViewBean getSelectedTeachingDepartmentViewBean() {
		return selectedTeachingDepartmentViewBean;
	}

	public void setSelectedTeachingDepartmentViewBean(TeachingDepartmentViewBean teachingDepartmentViewBean) {
		this.selectedTeachingDepartmentViewBean = teachingDepartmentViewBean;
		setCurrentOrganisationViewBean(teachingDepartmentViewBean);
		setInstitutionViewBeansForSelectedTeachingDepartment();
		preparePicklistInstitutionViewBeansForSelectedTeachingDepartment();
	}

	public List<InstitutionViewBean> getInstitutionViewBeansForSelectedTeachingDepartment() {
		return institutionViewBeansForSelectedTeachingDepartment;
	}

	private void setInstitutionViewBeansForSelectedTeachingDepartment() {
		logger.info("setInstitutionViewBeansForSelectedTeachingDepartment");
		if (selectedTeachingDepartmentViewBean != null) {
			institutionViewBeansForSelectedTeachingDepartment.clear();
			for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
				if (selectedTeachingDepartmentViewBean.getTeachingDepartment().getInstitutions()
						.contains(institutionViewBean.getInstitution())) {
					institutionViewBeansForSelectedTeachingDepartment.add(institutionViewBean);
				}
			}
		}
	}

	public void preparePicklistInstitutionViewBeansForSelectedTeachingDepartment() {
		logger.info("preparePicklistInstitutionViewBeansForSelectedTeachingDepartment - selectedTeachingDepartment = {}",
				selectedTeachingDepartmentViewBean.getName());
		if (selectedTeachingDepartmentViewBean != null) {
			pickListInstitutionViewBeans.getPickListEntities().getSource().clear();
			pickListInstitutionViewBeans.getPickListEntities().getSource()
					.addAll(institutionViewBeans.getTableEntries());
			pickListInstitutionViewBeans.getPickListEntities().getTarget().clear();
			for (Etablissement institutionAssociatedWithSelectedTeachingDepartment : selectedTeachingDepartmentViewBean
					.getTeachingDepartment().getInstitutions()) {
				for (InstitutionViewBean institutionViewBean : institutionViewBeans.getTableEntries()) {
					if (institutionAssociatedWithSelectedTeachingDepartment.getId().equals(institutionViewBean.getId())) {
						pickListInstitutionViewBeans.getPickListEntities().getSource().remove(institutionViewBean);
						pickListInstitutionViewBeans.getPickListEntities().getTarget().add(institutionViewBean);
					}
				}
			}
		}
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
	
	public void onTransferCommunity(TransferEvent event) {
		logger.info("onTransferCommunity");
		@SuppressWarnings("unchecked")
		List<CommunityViewBean> listOfMovedViewBeans = (List<CommunityViewBean>) event.getItems();
		if (event.isAdd()) {
			for (CommunityViewBean communityViewBean : listOfMovedViewBeans) {
				logger.info("We should associate {} and {}",
						communityViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.associateCommunityAndInstitution(
						communityViewBean.getCommunity().getId(),
						selectedInstitutionViewBean.getInstitution().getId())) {
					communityViewBeansForSelectedInstitution.add(communityViewBean);
					logger.info("associated");
				}
			}
		}
		else {
			for (CommunityViewBean communityViewBean : listOfMovedViewBeans) {
				logger.info("We should dissociate {} and {}",
						communityViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.dissociateCommunityAndInstitution(
						communityViewBean.getCommunity().getId(),
						selectedInstitutionViewBean.getInstitution().getId())) {
					communityViewBeansForSelectedInstitution.remove(communityViewBean);
					logger.info("dissociated");
				}
			}
		}
		selectedInstitutionViewBean.setInstitution(
				organisationsService.retrieveInstitution(selectedInstitutionViewBean.getInstitution().getId()));
		setInstitutionViewBeansForSelectedCommunity(); // TODO remove this
	}

	public void onTransferAdministrativeDepartment(TransferEvent event) {
		logger.info("onTransferAdministrativeDepartment");
		@SuppressWarnings("unchecked")
		List<AdministrativeDepartmentViewBean> listOfMovedViewBeans = (List<AdministrativeDepartmentViewBean>) event.getItems();
		if (event.isAdd()) {
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : listOfMovedViewBeans) {
				logger.info("We should associate {} and {}",
						administrativeDepartmentViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.associateInstitutionAndAdministrativeDepartment(
						selectedInstitutionViewBean.getInstitution().getId(),
						administrativeDepartmentViewBean.getAdministrativeDepartment().getId())) {
					administrativeDepartmentViewBeansForSelectedInstitution.add(administrativeDepartmentViewBean);
					logger.info("associated");
				}
			}
		}
		else {
			for (AdministrativeDepartmentViewBean administrativeDepartmentViewBean : listOfMovedViewBeans) {
				logger.info("We should dissociate {} and {}",
						administrativeDepartmentViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.dissociateInstitutionAndAdministrativeDepartment(
						selectedInstitutionViewBean.getInstitution().getId(),
						administrativeDepartmentViewBean.getAdministrativeDepartment().getId())) {
					administrativeDepartmentViewBeansForSelectedInstitution.remove(administrativeDepartmentViewBean);
					logger.info("dissociated");
				}
			}
		}
		selectedInstitutionViewBean.setInstitution(
				organisationsService.retrieveInstitution(selectedInstitutionViewBean.getInstitution().getId()));
		setAdministrativeDepartmentViewBeansForSelectedInstitution(); // TODO remove this
	}

	public void onTransferTeachingDepartment(TransferEvent event) {
		logger.info("onTransferTeachingDepartment");
		@SuppressWarnings("unchecked")
		List<TeachingDepartmentViewBean> listOfMovedViewBeans = (List<TeachingDepartmentViewBean>) event.getItems();
		if (event.isAdd()) {
			for (TeachingDepartmentViewBean teachingDepartmentViewBean : listOfMovedViewBeans) {
				logger.info("We should associate {} and {}",
						teachingDepartmentViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.associateInstitutionAndTeachingDepartment(
						selectedInstitutionViewBean.getInstitution().getId(),
						teachingDepartmentViewBean.getTeachingDepartment().getId())) {
					teachingDepartmentViewBeansForSelectedInstitution.add(teachingDepartmentViewBean);
					logger.info("associated");
				}
			}
		}
		else {
			for (TeachingDepartmentViewBean teachingDepartmentViewBean : listOfMovedViewBeans) {
				logger.info("We should dissociate {} and {}",
						teachingDepartmentViewBean.getName(),
						selectedInstitutionViewBean.getName());
				if (organisationsService.dissociateInstitutionAndTeachingDepartment(
						selectedInstitutionViewBean.getInstitution().getId(),
						teachingDepartmentViewBean.getTeachingDepartment().getId())) {
					teachingDepartmentViewBeansForSelectedInstitution.remove(teachingDepartmentViewBean);
					logger.info("dissociated");
				}
			}
		}
		selectedInstitutionViewBean.setInstitution(
				organisationsService.retrieveInstitution(selectedInstitutionViewBean.getInstitution().getId()));
		setTeachingDepartmentViewBeansForSelectedInstitution(); // TODO remove this
	}
	
	public void onTransferInstitution(TransferEvent event) {
		logger.info("onTransferInstitution");
		@SuppressWarnings("unchecked")
		List<InstitutionViewBean> listOfMovedViewBeans = (List<InstitutionViewBean>) event.getItems();
		if (event.isAdd()) {
			for (InstitutionViewBean institutionViewBean : listOfMovedViewBeans) {
				if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
					logger.info("We should associate {} and {}",
							institutionViewBean.getName(),
							selectedCommunityViewBean.getName());
					if (organisationsService.associateCommunityAndInstitution(
							selectedCommunityViewBean.getCommunity().getId(),
							institutionViewBean.getInstitution().getId())) {
						institutionViewBeansForSelectedCommunity.add(institutionViewBean);
						logger.info("associated");
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
					logger.info("We should associate {} and {}",
							institutionViewBean.getName(),
							selectedAdministrativeDepartmentViewBean.getName());
					if (organisationsService.associateInstitutionAndAdministrativeDepartment(
							institutionViewBean.getInstitution().getId(),
							selectedAdministrativeDepartmentViewBean.getAdministrativeDepartment().getId())) {
						institutionViewBeansForSelectedAdministrativeDepartment.add(institutionViewBean);
						logger.info("associated");
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
					logger.info("We should associate {} and {}",
							institutionViewBean.getName(),
							selectedTeachingDepartmentViewBean.getName());
					if (organisationsService.associateInstitutionAndTeachingDepartment(
							institutionViewBean.getInstitution().getId(),
							selectedTeachingDepartmentViewBean.getTeachingDepartment().getId())) {
						institutionViewBeansForSelectedTeachingDepartment.add(institutionViewBean);
						logger.info("associated");
					}
				}
				else {
					logger.info("currentOrganisationViewBean instanceof {}", currentOrganisationViewBean.getClass());
				}
			}
		}
		else {
			for (InstitutionViewBean institutionViewBean : listOfMovedViewBeans) {
				if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
					logger.info("We should dissociate {} and {}",
							institutionViewBean.getName(),
							selectedCommunityViewBean.getName());
					if (organisationsService.dissociateCommunityAndInstitution(
							selectedCommunityViewBean.getCommunity().getId(),
							institutionViewBean.getInstitution().getId())) {
						institutionViewBeansForSelectedCommunity.remove(institutionViewBean);
						logger.info("dissociated");
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
					logger.info("We should dissociate {} and {}",
							institutionViewBean.getName(),
							selectedAdministrativeDepartmentViewBean.getName());
					if (organisationsService.dissociateInstitutionAndAdministrativeDepartment(
							institutionViewBean.getInstitution().getId(),
							selectedAdministrativeDepartmentViewBean.getAdministrativeDepartment().getId())) {
						institutionViewBeansForSelectedAdministrativeDepartment.remove(institutionViewBean);
						logger.info("dissociated");
					}
				}
				else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
					logger.info("We should dissociate {} and {}",
							institutionViewBean.getName(),
							selectedTeachingDepartmentViewBean.getName());
					if (organisationsService.dissociateInstitutionAndTeachingDepartment(
							institutionViewBean.getInstitution().getId(),
							selectedTeachingDepartmentViewBean.getTeachingDepartment().getId())) {
						institutionViewBeansForSelectedTeachingDepartment.remove(institutionViewBean);
						logger.info("dissociated");						
					}
				}
				else {
					logger.info("currentOrganisationViewBean instanceof {}", currentOrganisationViewBean.getClass());
				}
			}
		}
		if (getCurrentOrganisationViewBean() instanceof CommunityViewBean) {
			selectedCommunityViewBean.setCommunity(
					organisationsService.retrieveCommunity(selectedCommunityViewBean.getCommunity().getId()));
			setInstitutionViewBeansForSelectedCommunity(); // TODO remove this
		}
		else if (getCurrentOrganisationViewBean() instanceof AdministrativeDepartmentViewBean) {
			selectedAdministrativeDepartmentViewBean.setAdministrativeDepartment(
					organisationsService.retrieveAdministrativeDepartment(selectedAdministrativeDepartmentViewBean.getAdministrativeDepartment().getId()));
			setInstitutionViewBeansForSelectedAdministrativeDepartment(); // TODO remove this
		}
		else if (getCurrentOrganisationViewBean() instanceof TeachingDepartmentViewBean) {
			selectedTeachingDepartmentViewBean.setTeachingDepartment(
					organisationsService.retrieveTeachingDepartment(selectedTeachingDepartmentViewBean.getTeachingDepartment().getId()));
			setInstitutionViewBeansForSelectedTeachingDepartment(); // TODO remove this
		}
	}
	
	/*
	 * public void handleNewCommunityIconUpload(FileUploadEvent event) {
	 * UploadedFile file = event.getFile();
	 * MessageDisplayer.showMessageToUserWithSeverityInfo
	 * ("handleNewCommunityIconUpload", file.getFileName()); }
	 */

}
