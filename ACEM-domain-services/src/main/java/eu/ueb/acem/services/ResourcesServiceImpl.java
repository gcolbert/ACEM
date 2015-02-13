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

import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipmentNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.PedagogicalAndDocumentaryResourceNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ProfessionalTrainingNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareDocumentationNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.UseModeNode;
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
	private ResourceDAO<Long, Equipment> equipmentDAO;

	@Inject
	private ResourceDAO<Long, PedagogicalAndDocumentaryResource> pedagogicalAndDocumentaryResourcesDAO;

	@Inject
	private ResourceDAO<Long, ProfessionalTraining> professionalTrainingDAO;

	@Inject
	private ResourceDAO<Long, Software> softwareDAO;

	@Inject
	private ResourceDAO<Long, SoftwareDocumentation> softwareDocumentationDAO;

	@Override
	public Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType) {
		Set<ResourceCategory> categories = new HashSet<ResourceCategory>();
		if (resourceType.equals("RESOURCE_TYPE_SOFTWARE")) {
			categories.addAll(softwareDAO.getCategories());
		}
		else if (resourceType.equals("RESOURCE_TYPE_SOFTWARE_DOCUMENTATION")) {
			categories.addAll(softwareDocumentationDAO.getCategories());
		}
		else if (resourceType.equals("RESOURCE_TYPE_EQUIPMENT")) {
			categories.addAll(equipmentDAO.getCategories());
		}
		else if (resourceType.equals("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTATION_RESOURCE")) {
			categories.addAll(pedagogicalAndDocumentaryResourcesDAO.getCategories());
		}
		else if (resourceType.equals("RESOURCE_TYPE_PROFESSIONAL_TRAINING")) {
			categories.addAll(professionalTrainingDAO.getCategories());
		}
		else {
			logger.error("Unknown resourceType '{}'", resourceType);
		}
		return categories;
	}

	@Override
	public Resource createResource(Long toolCategoryId, Long ownerOrganisationId, Long supportOrganisationId,
			String resourceType, String name, String iconFileName) {
		Organisation supportOrganisation = organisationsService.retrieveOrganisation(supportOrganisationId, true);
		Organisation ownerOrganisation = organisationsService.retrieveOrganisation(ownerOrganisationId, true);
		if ((ownerOrganisation != null) && (supportOrganisation != null)) {
			Resource entity = null;
			if (resourceType.equals("RESOURCE_TYPE_SOFTWARE")) {
				entity = new SoftwareNode(name, iconFileName);
			}
			else if (resourceType.equals("RESOURCE_TYPE_SOFTWARE_DOCUMENTATION")) {
				entity = new SoftwareDocumentationNode(name, iconFileName);
			}
			else if (resourceType.equals("RESOURCE_TYPE_EQUIPMENT")) {
				entity = new EquipmentNode(name, iconFileName);
			}
			else if (resourceType.equals("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTATION_RESOURCE")) {
				entity = new PedagogicalAndDocumentaryResourceNode(name, iconFileName);
			}
			else if (resourceType.equals("RESOURCE_TYPE_PROFESSIONAL_TRAINING")) {
				entity = new ProfessionalTrainingNode(name, iconFileName);
			}
			else {
				logger.error("Unknown resourceType '{}'", resourceType);
			}

			ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId, true);
			entity.getCategories().add(resourceCategory);
			resourceCategory.getResources().add(entity);
			resourceCategory = resourceCategoryDAO.update(resourceCategory);

			entity.setOrganisationPossessingResource(ownerOrganisation);
			ownerOrganisation = organisationsService.updateOrganisation(ownerOrganisation);
			entity.setOrganisationSupportingResource(supportOrganisation);
			supportOrganisation = organisationsService.updateOrganisation(supportOrganisation);

			if (resourceType.equals("RESOURCE_TYPE_SOFTWARE")) {
				entity = softwareDAO.create((Software) entity);
			}
			else if (resourceType.equals("RESOURCE_TYPE_SOFTWARE_DOCUMENTATION")) {
				entity = softwareDocumentationDAO.create((SoftwareDocumentation) entity);
			}
			else if (resourceType.equals("RESOURCE_TYPE_EQUIPMENT")) {
				entity = equipmentDAO.create((Equipment) entity);
			}
			else if (resourceType.equals("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTATION_RESOURCE")) {
				entity = pedagogicalAndDocumentaryResourcesDAO.create((PedagogicalAndDocumentaryResource) entity);
			}
			else if (resourceType.equals("RESOURCE_TYPE_PROFESSIONAL_TRAINING")) {
				entity = professionalTrainingDAO.create((ProfessionalTraining) entity);
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
	public Resource retrieveResource(Long id, boolean initialize) {
		Resource entity = null;
		if (softwareDAO.exists(id)) {
			entity = softwareDAO.retrieveById(id, initialize);
		}
		else if (softwareDocumentationDAO.exists(id)) {
			entity = softwareDocumentationDAO.retrieveById(id, initialize);
		}
		else if (equipmentDAO.exists(id)) {
			entity = equipmentDAO.retrieveById(id, initialize);
		}
		else if (pedagogicalAndDocumentaryResourcesDAO.exists(id)) {
			entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id, initialize);
		}
		else if (professionalTrainingDAO.exists(id)) {
			entity = professionalTrainingDAO.retrieveById(id, initialize);
		}
		else {
			logger.error("There is no resource with id='{}'", id);
		}
		return entity;
	}

	@Override
	public Resource updateResource(Resource resource) {
		Resource updatedResource = null;
		if (resource instanceof Software) {
			updatedResource = softwareDAO.update((Software) resource);
		}
		else if (resource instanceof SoftwareDocumentation) {
			updatedResource = softwareDocumentationDAO.update((SoftwareDocumentation) resource);
		}
		else if (resource instanceof Equipment) {
			updatedResource = equipmentDAO.update((Equipment) resource);
		}
		else if (resource instanceof PedagogicalAndDocumentaryResource) {
			updatedResource = pedagogicalAndDocumentaryResourcesDAO
					.update((PedagogicalAndDocumentaryResource) resource);
		}
		else if (resource instanceof ProfessionalTraining) {
			updatedResource = professionalTrainingDAO.update((ProfessionalTraining) resource);
		}
		return updatedResource;
	}

	@Override
	public Boolean deleteResource(Long id) {
		if (softwareDAO.exists(id)) {
			Software entity = softwareDAO.retrieveById(id);
			softwareDAO.delete(entity);
			return !softwareDAO.exists(id);
		}
		else if (softwareDocumentationDAO.exists(id)) {
			SoftwareDocumentation entity = softwareDocumentationDAO.retrieveById(id);
			softwareDocumentationDAO.delete(entity);
			return !softwareDocumentationDAO.exists(id);
		}
		else if (equipmentDAO.exists(id)) {
			Equipment entity = equipmentDAO.retrieveById(id);
			equipmentDAO.delete(entity);
			return !equipmentDAO.exists(id);
		}
		else if (pedagogicalAndDocumentaryResourcesDAO.exists(id)) {
			PedagogicalAndDocumentaryResource entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id);
			pedagogicalAndDocumentaryResourcesDAO.delete(entity);
			return !pedagogicalAndDocumentaryResourcesDAO.exists(id);
		}
		else if (professionalTrainingDAO.exists(id)) {
			ProfessionalTraining entity = professionalTrainingDAO.retrieveById(id);
			professionalTrainingDAO.delete(entity);
			return !professionalTrainingDAO.exists(id);
		}
		else {
			logger.error("There is no resource with id='{}'", id);
			return false;
		}
	}

	@Override
	public void saveResourceName(String resourceType, Long id, String label) {
		Resource entity = null;
		if (resourceType.equals("RESOURCE_TYPE_SOFTWARE")) {
			entity = softwareDAO.retrieveById(id);
		}
		else if (resourceType.equals("RESOURCE_TYPE_SOFTWARE_DOCUMENTATION")) {
			entity = softwareDocumentationDAO.retrieveById(id);
		}
		else if (resourceType.equals("RESOURCE_TYPE_EQUIPMENT")) {
			entity = equipmentDAO.retrieveById(id);
		}
		else if (resourceType.equals("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTATION_RESOURCE")) {
			entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id);
		}
		else if (resourceType.equals("RESOURCE_TYPE_PROFESSIONAL_TRAINING")) {
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
	public ResourceCategory retrieveResourceCategory(Long id, boolean initialize) {
		return resourceCategoryDAO.retrieveById(id, initialize);
	}

	@Override
	public ResourceCategory updateResourceCategory(ResourceCategory resourceCategory) {
		ResourceCategory updatedEntity = resourceCategoryDAO.update(resourceCategory);
		return updatedEntity;
	}

	@Override
	public Boolean deleteResourceCategory(Long id) {
		if (resourceCategoryDAO.exists(id)) {
			ResourceCategory entity = resourceCategoryDAO.retrieveById(id);
			resourceCategoryDAO.delete(entity);
			return !resourceCategoryDAO.exists(id);
		}
		else {
			logger.error("There is no resource category with id='{}'", id);
			return false;
		}
	}

	@Override
	public Collection<ResourceCategory> retrieveAllCategories() {
		return resourceCategoryDAO.retrieveAll();
	}

	@Override
	public Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(Long id) {
		ResourceCategory toolCategory = retrieveResourceCategory(id, true);
		Set<PedagogicalScenario> scenarios = new HashSet<PedagogicalScenario>(0);
		if (toolCategory != null) {
			for (PedagogicalActivity activity : toolCategory.getPedagogicalActivities()) {
				scenarios.addAll(activity.getScenarios());
			}
		}
		return scenarios;
	}

	@Override
	public Collection<? extends Resource> retrieveSoftwaresWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Resource> retrieveSoftwareDocumentationsWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDocumentationDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDocumentationDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Resource> retrieveEquipmentWithCategory(ResourceCategory category) {
		if (category != null) {
			return equipmentDAO.retrieveAllWithCategory(category);
		}
		else {
			return equipmentDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Resource> retrieveProfessionalTrainingsWithCategory(ResourceCategory category) {
		if (category != null) {
			return professionalTrainingDAO.retrieveAllWithCategory(category);
		}
		else {
			return professionalTrainingDAO.retrieveAll();
		}
	}

	@Override
	public Collection<? extends Resource> retrievePedagogicalAndDocumentaryResourcesWithCategory(
			ResourceCategory category) {
		if (category != null) {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAllWithCategory(category);
		}
		else {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAll();
		}
	}

	@Override
	public UseMode createUseMode(String name) {
		return useModeDAO.create(new UseModeNode(name));
	}

	@Override
	public UseMode updateUseMode(UseMode resource) {
		UseMode updatedEntity = useModeDAO.update(resource);
		return updatedEntity;
	}

	@Override
	public UseMode retrieveUseMode(Long idUseMode, boolean initialize) {
		return useModeDAO.retrieveById(idUseMode, initialize);
	}

	@Override
	public Boolean deleteUseMode(Long id) {
		if (useModeDAO.exists(id)) {
			UseMode entity = useModeDAO.retrieveById(id);
			useModeDAO.delete(entity);
		}
		return !useModeDAO.exists(id);
	}

	@Override
	public Boolean associateUseModeAndOrganisation(Long idUseMode, Long idOrganisation) {
		UseMode useMode = retrieveUseMode(idUseMode, true);
		Organisation referredOrganisation = organisationsService.retrieveOrganisation(idOrganisation, true);

		useMode.getReferredOrganisations().add(referredOrganisation);
		referredOrganisation.getUseModes().add(useMode);
		
		useMode = updateUseMode(useMode);
		referredOrganisation = organisationsService.updateOrganisation(referredOrganisation);

		return ((useMode.getReferredOrganisations().contains(referredOrganisation)) && (referredOrganisation.getUseModes().contains(useMode)));
	}

	@Override
	public Boolean dissociateUseModeAndOrganisation(Long idUseMode, Long idOrganisation) {
		UseMode useMode = retrieveUseMode(idUseMode, true);
		Organisation referredOrganisation = organisationsService.retrieveOrganisation(idOrganisation, true);

		useMode.getReferredOrganisations().remove(referredOrganisation);
		referredOrganisation.getUseModes().remove(useMode);

		useMode = updateUseMode(useMode);
		referredOrganisation = organisationsService.updateOrganisation(referredOrganisation);

		return ((!useMode.getReferredOrganisations().contains(referredOrganisation)) && (!referredOrganisation.getUseModes().contains(useMode)));
	}
	
	
}
