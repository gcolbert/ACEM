/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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

import eu.ueb.acem.dal.bleu.PedagogicalScenarioDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.UseMode;
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
	private static final String RESOURCE_TYPE_DOCUMENTATION = "RESOURCE_TYPE_DOCUMENTATION";
	private static final String RESOURCE_TYPE_EQUIPMENT = "RESOURCE_TYPE_EQUIPMENT";
	private static final String RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE = "RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE";
	private static final String RESOURCE_TYPE_PROFESSIONAL_TRAINING = "RESOURCE_TYPE_PROFESSIONAL_TRAINING";

	@Inject
	private PedagogicalScenarioDAO<Long> pedagogicalScenarioDAO;

	@Inject
	private ResourceCategoryDAO<Long> resourceCategoryDAO;
	
	@Inject
	private UseModeDAO<Long> useModeDAO;

	@Inject
	private ResourceDAO<Long, Equipment> equipmentDAO;

	@Inject
	private ResourceDAO<Long, PedagogicalAndDocumentaryResource> pedagogicalAndDocumentaryResourcesDAO;

	@Inject
	private ResourceDAO<Long, ProfessionalTraining> professionalTrainingDAO;

	@Inject
	private ResourceDAO<Long, Software> softwareDAO;

	@Inject
	private ResourceDAO<Long, Documentation> documentationDAO;

	@Override
	public final String getResourceType_RESOURCE_TYPE_SOFTWARE() {
		return RESOURCE_TYPE_SOFTWARE;
	}

	@Override
	public final String getResourceType_RESOURCE_TYPE_DOCUMENTATION() {
		return RESOURCE_TYPE_DOCUMENTATION;
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
		case RESOURCE_TYPE_DOCUMENTATION:
			resourceDAO = documentationDAO;
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
			categories.addAll(resourceDAO.retrieveCategories());
		}
		return categories;
	}

	@Override
	public Collection<ResourceCategory> retrieveCategoriesForResourceTypeAndPerson(String resourceType, Person person) {
		Set<ResourceCategory> categories = new HashSet<ResourceCategory>();
		ResourceDAO<Long, ? extends Resource> resourceDAO = null;
		switch (resourceType) {
		case RESOURCE_TYPE_SOFTWARE:
			resourceDAO = softwareDAO;
			break;
		case RESOURCE_TYPE_DOCUMENTATION:
			resourceDAO = documentationDAO;
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
			categories.addAll(resourceDAO.retrieveCategoriesForPerson(person));
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
				resource = softwareDAO.create(name, iconFileName);
				break;
			case RESOURCE_TYPE_DOCUMENTATION:
				resource = documentationDAO.create(name, iconFileName);
				break;
			case RESOURCE_TYPE_EQUIPMENT:
				resource = equipmentDAO.create(name, iconFileName);
				break;
			case RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE:
				resource = pedagogicalAndDocumentaryResourcesDAO.create(name, iconFileName);
				break;
			case RESOURCE_TYPE_PROFESSIONAL_TRAINING:
				resource = professionalTrainingDAO.create(name, iconFileName);
				break;
			default:
				logger.error("createResource, don't know how to instanciate a domain bean having resourceType '{}'.",
						resourceType);
			}

			if (resource != null) {
				// Reloading the objects from the database avoids a strange
				// bug that appeared on resource.getCategories().add(resourceCategory)
				ResourceCategory resourceCategoryReloaded = resourceCategoryDAO.retrieveById(resourceCategory.getId(), true);
				resource.getCategories().add(resourceCategoryReloaded);
				resourceCategoryReloaded.getResources().add(resource);
				resourceCategoryReloaded = resourceCategoryDAO.update(resourceCategoryReloaded);

				// Reloading the objects from the database avoids a bug
				Organisation ownerOrganisationReloaded = organisationsService.retrieveOrganisation(ownerOrganisation.getId(), true);
				resource.setOrganisationPossessingResource(ownerOrganisationReloaded);
				ownerOrganisationReloaded.getPossessedResources().add(resource);
				ownerOrganisationReloaded = organisationsService.updateOrganisation(ownerOrganisationReloaded);

				// Reloading the objects from the database avoids a bug
				Organisation supportOrganisationReloaded = organisationsService.retrieveOrganisation(supportOrganisation.getId(), true);
				resource.setOrganisationSupportingResource(supportOrganisationReloaded);
				supportOrganisationReloaded.getSupportedResources().add(resource);
				supportOrganisationReloaded = organisationsService.updateOrganisation(supportOrganisationReloaded);

				switch (resourceType) {
				case RESOURCE_TYPE_SOFTWARE:
					resource = softwareDAO.create((Software) resource);
					break;
				case RESOURCE_TYPE_DOCUMENTATION:
					resource = documentationDAO.create((Documentation) resource);
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
		else if (documentationDAO.exists(id)) {
			entity = documentationDAO.retrieveById(id, initialize);
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
	public Collection<Resource> getResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Collection<Resource> resources = new HashSet<Resource>();
		resources.addAll(softwareDAO.retrieveResourcesInCategoryForPerson(category, person));
		resources.addAll(documentationDAO.retrieveResourcesInCategoryForPerson(category, person));
		resources.addAll(professionalTrainingDAO.retrieveResourcesInCategoryForPerson(category, person));
		resources.addAll(pedagogicalAndDocumentaryResourcesDAO.retrieveResourcesInCategoryForPerson(category, person));
		resources.addAll(equipmentDAO.retrieveResourcesInCategoryForPerson(category, person));
		return resources;
	}

	@Override
	public Resource updateResource(Resource resource) {
		Resource updatedResource = null;
		if (resource instanceof Software) {
			updatedResource = softwareDAO.update((Software) resource);
		}
		else if (resource instanceof Documentation) {
			updatedResource = documentationDAO.update((Documentation) resource);
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
		else if (documentationDAO.exists(id)) {
			Documentation entity = documentationDAO.retrieveById(id);
			documentationDAO.delete(entity);
			success = !documentationDAO.exists(id);
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
		return resourceCategoryDAO.create(name, description, iconFileName);
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
	public Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(ResourceCategory category) {
		Set<PedagogicalScenario> scenarios = new HashSet<PedagogicalScenario>(0);
		if (category != null) {
			scenarios.addAll(pedagogicalScenarioDAO.retrieveScenariosAssociatedWithResourceCategory(category));
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
	public Collection<Documentation> retrieveDocumentationsWithCategory(ResourceCategory category) {
		if (category != null) {
			return documentationDAO.retrieveAllWithCategory(category);
		}
		else {
			return documentationDAO.retrieveAll();
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
		UseMode entity = useModeDAO.create(name);
		entity.setReferredOrganisation(referredOrganisation);
		referredOrganisation.getUseModes().add(entity);
		return useModeDAO.update(entity);
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
