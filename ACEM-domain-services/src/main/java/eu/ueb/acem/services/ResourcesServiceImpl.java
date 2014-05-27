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
package eu.ueb.acem.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.jaune.EquipmentDAO;
import eu.ueb.acem.dal.jaune.PedagogicalAndDocumentaryResourcesDAO;
import eu.ueb.acem.dal.jaune.ProfessionalTrainingDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.SoftwareDAO;
import eu.ueb.acem.dal.jaune.SoftwareDocumentationDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.domain.beans.jaune.neo4j.ApplicatifNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationApplicatifNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipementNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.FormationProfessionnelleNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourcePedagogiqueEtDocumentaireNode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {

	private static final Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Inject
	private ResourceCategoryDAO resourceCategoryDAO;

	@Inject
	private OrganisationsService organisationsService;
	
	@Inject
	private UseModeDAO useModeDAO;

	@Inject
	private EquipmentDAO equipmentDAO;

	@Inject
	private PedagogicalAndDocumentaryResourcesDAO pedagogicalAndDocumentaryResourcesDAO;

	@Inject
	private ProfessionalTrainingDAO professionalTrainingDAO;

	@Inject
	private SoftwareDAO softwareDAO;

	@Inject
	private SoftwareDocumentationDAO softwareDocumentationDAO;

	@Override
	public Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType) {
		Set<ResourceCategory> categories = new HashSet<ResourceCategory>();
		if (resourceType.equals("software")) {
			categories.addAll(softwareDAO.getCategories());
		}
		else if (resourceType.equals("softwareDocumentation")) {
			categories.addAll(softwareDocumentationDAO.getCategories());
		}
		else if (resourceType.equals("equipment")) {
			categories.addAll(equipmentDAO.getCategories());
		}
		else if (resourceType.equals("pedagogicalAndDocumentaryResources")) {
			categories.addAll(pedagogicalAndDocumentaryResourcesDAO.getCategories());
		}
		else if (resourceType.equals("professionalTraining")) {
			categories.addAll(professionalTrainingDAO.getCategories());
		}
		else {
			logger.error("Unknown resourceType '{}'", resourceType);
		}
		return categories;
	}

	@Override
	public Ressource createResource(Long toolCategoryId, Long supportServiceId, String resourceType, String name, String iconFileName) {
		Organisation supportService = organisationsService.retrieveOrganisation(supportServiceId);
		if (supportService != null) {
			Ressource entity = null;
			if (resourceType.equals("software")) {
				entity = new ApplicatifNode(name, iconFileName);
			}
			else if (resourceType.equals("softwareDocumentation")) {
				entity = new DocumentationApplicatifNode(name, iconFileName);
			}
			else if (resourceType.equals("equipment")) {
				entity = new EquipementNode(name, iconFileName);
			}
			else if (resourceType.equals("pedagogicalAndDocumentaryResources")) {
				entity = new RessourcePedagogiqueEtDocumentaireNode(name, iconFileName);
			}
			else if (resourceType.equals("professionalTraining")) {
				entity = new FormationProfessionnelleNode(name, iconFileName);
			}
			else {
				logger.error("Unknown resourceType '{}'", resourceType);
			}

			ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId);
			entity.addCategory(resourceCategory);
			resourceCategory = resourceCategoryDAO.update(resourceCategory);
			
			entity.setOrganisationPossessingResource(supportService);
			supportService = organisationsService.updateOrganisation(supportService);

			if (resourceType.equals("software")) {
				entity = softwareDAO.create((Applicatif)entity);
			}
			else if (resourceType.equals("softwareDocumentation")) {
				entity = softwareDocumentationDAO.create((DocumentationApplicatif)entity);
			}
			else if (resourceType.equals("equipment")) {
				entity = equipmentDAO.create((Equipement)entity);
			}
			else if (resourceType.equals("pedagogicalAndDocumentaryResources")) {
				entity = pedagogicalAndDocumentaryResourcesDAO.create((RessourcePedagogiqueEtDocumentaire)entity);
			}
			else if (resourceType.equals("professionalTraining")) {
				entity = professionalTrainingDAO.create((FormationProfessionnelle)entity);
			}
			else {
				logger.error("Unknown resourceType '{}'", resourceType);
			}
			return entity;
		}
		else {
			return null;
		}
	}

	@Override
	public Ressource retrieveResource(Long id) {
		Ressource entity = null;
		if (softwareDAO.exists(id)) {
			entity = softwareDAO.retrieveById(id);
		}
		else if (softwareDocumentationDAO.exists(id)) {
			entity = softwareDocumentationDAO.retrieveById(id);
		}
		else if (equipmentDAO.exists(id)) {
			entity = equipmentDAO.retrieveById(id);
		}
		else if (pedagogicalAndDocumentaryResourcesDAO.exists(id)) {
			entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id);
		}
		else if (professionalTrainingDAO.exists(id)) {
			entity = professionalTrainingDAO.retrieveById(id);
		}
		else {
			logger.error("There is no resource with id='{}'", id);
		}
		return entity;
	}

	@Override
	public Ressource updateResource(Ressource resource) {
		Ressource updatedResource = null;
		if (resource instanceof Applicatif) {
			resource = softwareDAO.update((Applicatif) resource);
		}
		else if (resource instanceof DocumentationApplicatif) {
			resource = softwareDocumentationDAO.update((DocumentationApplicatif) resource);
		}
		else if (resource instanceof Equipement) {
			resource = equipmentDAO.update((Equipement) resource);
		}
		else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
			resource = pedagogicalAndDocumentaryResourcesDAO.update((RessourcePedagogiqueEtDocumentaire) resource);
		}
		return updatedResource;
	}

	@Override
	public void saveResourceName(String resourceType, Long id, String label) {
		Ressource entity = null;
		if (resourceType.equals("software")) {
			entity = softwareDAO.retrieveById(id);
		}
		else if (resourceType.equals("softwareDocumentation")) {
			entity = softwareDocumentationDAO.retrieveById(id);
		}
		else if (resourceType.equals("equipment")) {
			entity = equipmentDAO.retrieveById(id);
		}
		else if (resourceType.equals("pedagogicalAndDocumentaryResources")) {
			entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id);
		}
		else if (resourceType.equals("professionalTraining")) {
			entity = professionalTrainingDAO.retrieveById(id);
		}
		else {
			logger.error("Unknown resourceType '{}'", resourceType);
		}
		if (entity != null) {
			entity = updateResource(entity);
		}
	}

	@Override
	public ResourceCategory createResourceCategory(String name, String description, String iconFileName) {
		return resourceCategoryDAO.create(new ResourceCategoryNode(name, description, iconFileName));
	}
	
	@Override
	public ResourceCategory retrieveResourceCategory(Long id) {
		return resourceCategoryDAO.retrieveById(id);
	}

	@Override
	public ResourceCategory updateResourceCategory(ResourceCategory resourceCategory) {
		return resourceCategoryDAO.update((ResourceCategory) resourceCategory);
	}
	
	@Override
	public Collection<ResourceCategory> retrieveAllCategories() {
		return resourceCategoryDAO.retrieveAll();
	}
	
	@Override
	public Collection<Scenario> retrieveScenariosAssociatedWithResourceCategory(Long id) {
		ResourceCategory toolCategory = retrieveResourceCategory(id);
		Set<Scenario> scenarios = new HashSet<Scenario>();
		if (toolCategory != null) {
			for (ActivitePedagogique activity : toolCategory.getPedagogicalActivities()) {
				scenarios.addAll(activity.getScenarios());
			}
		}
		return scenarios;
	}
	
	@Override
	public Collection<? extends Ressource> retrieveSoftwaresWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Ressource> retrieveSoftwareDocumentationsWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDocumentationDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDocumentationDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Ressource> retrieveEquipmentWithCategory(ResourceCategory category) {
		if (category != null) {
			return equipmentDAO.retrieveAllWithCategory(category);
		}
		else {
			return equipmentDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Ressource> retrieveProfessionalTrainingsWithCategory(ResourceCategory category) {
		if (category != null) {
			return professionalTrainingDAO.retrieveAllWithCategory(category);
		}
		else {
			return professionalTrainingDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Ressource> retrievePedagogicalAndDocumentaryResourcesWithCategory(ResourceCategory category) {
		if (category != null) {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAllWithCategory(category);
		}
		else {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAll();
		}
	}

	/*-
	@Override
	public Set<Long> retrievePedagogicalNeedsAndAnswersAssociatedWithResourceCategory(Long resourceCategoryId) {
		Set<Long> idsOfPedagogicalNodes = new HashSet<Long>();
		// TODO : déplacer cette requête dans ResourcesDAO
		// MATCH (r:ResourceCategory)<-[:answeredUsingResourceCategory]-(answer)<-[:needAnsweredBy]-(need)-[:hasParentNeed*]->(need2)
		// WHERE id(r)=41 return r,answer,need,need2;
		return idsOfPedagogicalNodes;
	}
	*/

}
