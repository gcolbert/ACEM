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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.ResourceDAO;
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
public class ResourcesServiceImpl implements ResourcesService, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7146622478579687474L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Inject
	private OrganisationsService organisationsService;
	
	private static final String RESOURCE_TYPE_SOFTWARE = "RESOURCE_TYPE_SOFTWARE";
	private static final String RESOURCE_TYPE_SOFTWARE_DOCUMENTATION = "RESOURCE_TYPE_SOFTWARE_DOCUMENTATION";
	private static final String RESOURCE_TYPE_EQUIPMENT = "RESOURCE_TYPE_EQUIPMENT";
	private static final String RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE = "RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE";
	private static final String RESOURCE_TYPE_PROFESSIONAL_TRAINING = "RESOURCE_TYPE_PROFESSIONAL_TRAINING";

	@Inject
	private DAO<Long, ResourceCategory> resourceCategoryDAO;

	@Inject
	private DAO<Long, UseMode> useModeDAO;

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
	public final String getResourceType_RESOURCE_TYPE_SOFTWARE() {
		return RESOURCE_TYPE_SOFTWARE;
	}

	@Override
	public final String getResourceType_RESOURCE_TYPE_SOFTWARE_DOCUMENTATION() {
		return RESOURCE_TYPE_SOFTWARE_DOCUMENTATION;
	}

	@Override
	public final String getResourceType_RESOURCE_TYPE_EQUIPMENT() {
		return RESOURCE_TYPE_EQUIPMENT;
	}

	@Override
	public final String getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE() {
		return RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE;
	}

	@Override
	public final String getResourceType_RESOURCE_TYPE_PROFESSIONAL_TRAINING() {
		return RESOURCE_TYPE_PROFESSIONAL_TRAINING;
	}

	@Override
	public Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType) {
		Set<ResourceCategory> categories = new HashSet<ResourceCategory>();
		ResourceDAO<Long, ? extends Resource> resourceDAO = null;
		switch (resourceType) {
		case RESOURCE_TYPE_SOFTWARE:
			resourceDAO = softwareDAO;
			break;
		case RESOURCE_TYPE_SOFTWARE_DOCUMENTATION:
			resourceDAO = softwareDocumentationDAO;
			break;
		case RESOURCE_TYPE_EQUIPMENT:
			resourceDAO = equipmentDAO;
			break;
		case RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE:
			resourceDAO = pedagogicalAndDocumentaryResourcesDAO;
			break;
		case RESOURCE_TYPE_PROFESSIONAL_TRAINING:
			resourceDAO = professionalTrainingDAO;
			break;
		default:
			logger.error("retrieveCategoriesForResourceType, don't know which DAO to call for resourceType '{}'.",
					resourceType);
		}
		if (resourceDAO != null) {
			categories.addAll(resourceDAO.getCategories());
		}
		return categories;
	}

	@Override
	public Resource createResource(ResourceCategory resourceCategory, Organisation ownerOrganisation, Organisation supportOrganisation,
			String resourceType, String name, String iconFileName) {
		Resource resource = null;
		if ((ownerOrganisation != null) && (supportOrganisation != null) && (resourceCategory != null)) {
			switch (resourceType) {
			case RESOURCE_TYPE_SOFTWARE:
				resource = new SoftwareNode(name, iconFileName);
				break;
			case RESOURCE_TYPE_SOFTWARE_DOCUMENTATION:
				resource = new SoftwareDocumentationNode(name, iconFileName);
				break;
			case RESOURCE_TYPE_EQUIPMENT:
				resource = new EquipmentNode(name, iconFileName);
				break;
			case RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE:
				resource = new PedagogicalAndDocumentaryResourceNode(name, iconFileName);
				break;
			case RESOURCE_TYPE_PROFESSIONAL_TRAINING:
				resource = new ProfessionalTrainingNode(name, iconFileName);
				break;
			default:
				logger.error("createResource, don't know how to instanciate a domain bean having resourceType '{}'.",
						resourceType);
			}

			if (resource != null) {
				resource.getCategories().add(resourceCategory);
				resourceCategory.getResources().add(resource);
				resourceCategory = resourceCategoryDAO.update(resourceCategory);

				resource.setOrganisationPossessingResource(ownerOrganisation);
				ownerOrganisation.getPossessedResources().add(resource);
				ownerOrganisation = organisationsService.updateOrganisation(ownerOrganisation);

				resource.setOrganisationSupportingResource(supportOrganisation);
				supportOrganisation.getSupportedResources().add(resource);
				supportOrganisation = organisationsService.updateOrganisation(supportOrganisation);

				switch (resourceType) {
				case RESOURCE_TYPE_SOFTWARE:
					resource = softwareDAO.create((Software) resource);
					break;
				case RESOURCE_TYPE_SOFTWARE_DOCUMENTATION:
					resource = softwareDocumentationDAO.create((SoftwareDocumentation) resource);
					break;
				case RESOURCE_TYPE_EQUIPMENT:
					resource = equipmentDAO.create((Equipment) resource);
					break;
				case RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE:
					resource = pedagogicalAndDocumentaryResourcesDAO.create((PedagogicalAndDocumentaryResource) resource);
					break;
				case RESOURCE_TYPE_PROFESSIONAL_TRAINING:
					resource = professionalTrainingDAO.create((ProfessionalTraining) resource);
					break;
				default:
				}
			}
		}
		return resource;
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
		Boolean success = false;
		if (softwareDAO.exists(id)) {
			Software entity = softwareDAO.retrieveById(id);
			softwareDAO.delete(entity);
			success = !softwareDAO.exists(id);
		}
		else if (softwareDocumentationDAO.exists(id)) {
			SoftwareDocumentation entity = softwareDocumentationDAO.retrieveById(id);
			softwareDocumentationDAO.delete(entity);
			success = !softwareDocumentationDAO.exists(id);
		}
		else if (equipmentDAO.exists(id)) {
			Equipment entity = equipmentDAO.retrieveById(id);
			equipmentDAO.delete(entity);
			success = !equipmentDAO.exists(id);
		}
		else if (pedagogicalAndDocumentaryResourcesDAO.exists(id)) {
			PedagogicalAndDocumentaryResource entity = pedagogicalAndDocumentaryResourcesDAO.retrieveById(id);
			pedagogicalAndDocumentaryResourcesDAO.delete(entity);
			success = !pedagogicalAndDocumentaryResourcesDAO.exists(id);
		}
		else if (professionalTrainingDAO.exists(id)) {
			ProfessionalTraining entity = professionalTrainingDAO.retrieveById(id);
			professionalTrainingDAO.delete(entity);
			success = !professionalTrainingDAO.exists(id);
		}
		else {
			logger.error("There is no resource with id='{}'", id);
		}
		return success;
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
		return resourceCategoryDAO.update(resourceCategory);
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
	public Collection<Software> retrieveSoftwaresWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDAO.retrieveAll();
		}
	}

	@Override
	public Collection<SoftwareDocumentation> retrieveSoftwareDocumentationsWithCategory(ResourceCategory category) {
		if (category != null) {
			return softwareDocumentationDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDocumentationDAO.retrieveAll();
		}
	}

	@Override
	public Collection<Equipment> retrieveEquipmentWithCategory(ResourceCategory category) {
		if (category != null) {
			return equipmentDAO.retrieveAllWithCategory(category);
		}
		else {
			return equipmentDAO.retrieveAll();
		}
	}

	@Override
	public Collection<ProfessionalTraining> retrieveProfessionalTrainingsWithCategory(ResourceCategory category) {
		if (category != null) {
			return professionalTrainingDAO.retrieveAllWithCategory(category);
		}
		else {
			return professionalTrainingDAO.retrieveAll();
		}
	}

	@Override
	public Collection<PedagogicalAndDocumentaryResource> retrievePedagogicalAndDocumentaryResourcesWithCategory(
			ResourceCategory category) {
		if (category != null) {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAllWithCategory(category);
		}
		else {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAll();
		}
	}

	@Override
	public UseMode createUseMode(String name, Organisation referredOrganisation) {
		UseMode entity = new UseModeNode(name);
		entity.setReferredOrganisation(referredOrganisation);
		referredOrganisation.getUseModes().add(entity);
		return useModeDAO.create(entity);
	}

	@Override
	public UseMode updateUseMode(UseMode resource) {
		return useModeDAO.update(resource);
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
	public Boolean associateUseModeAndResource(UseMode useMode, Resource resource) {
		if ((useMode != null) && (resource != null)) {
			useMode.getResources().add(resource);
			resource.getUseModes().add(useMode);

			useMode = updateUseMode(useMode);
			resource = updateResource(resource);

			return useMode.getResources().contains(resource) && resource.getUseModes().contains(useMode);
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean dissociateUseModeAndResource(UseMode useMode, Resource resource) {
		if ((useMode != null) && (resource != null)) {
			useMode.getResources().remove(resource);
			resource.getUseModes().remove(useMode);
	
			useMode = updateUseMode(useMode);
			resource = updateResource(resource);
	
			return !useMode.getResources().contains(resource) && !resource.getUseModes().contains(useMode);
		}
		else {
			return false;
		}
	}

}
