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

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ResourcesService extends Serializable {

	String getResourceType_RESOURCE_TYPE_SOFTWARE();

	String getResourceType_RESOURCE_TYPE_DOCUMENTATION();

	String getResourceType_RESOURCE_TYPE_EQUIPMENT();

	String getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE();

	String getResourceType_RESOURCE_TYPE_PROFESSIONAL_TRAINING();

	ResourceCategory createResourceCategory(String name, String description, String iconFileName);

	ResourceCategory retrieveResourceCategory(Long id, boolean initialize);

	ResourceCategory updateResourceCategory(ResourceCategory resourceCategory);

	Boolean deleteResourceCategory(Long id);

	Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType);

	Collection<ResourceCategory> retrieveAllCategories();

	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(ResourceCategory category);

	Resource createResource(ResourceCategory category, Organisation ownerOrganisation,
			Organisation supportOrganisation, String resourceType, String name, String iconFileName);

	Resource updateResource(Resource resource);

	Resource retrieveResource(Long id, boolean initialize);

	Boolean deleteResource(Long id);

	Collection<Software> retrieveSoftwaresWithCategory(ResourceCategory category);

	Collection<Documentation> retrieveSoftwareDocumentationsWithCategory(ResourceCategory category);

	Collection<Equipment> retrieveEquipmentWithCategory(ResourceCategory category);

	Collection<ProfessionalTraining> retrieveProfessionalTrainingsWithCategory(ResourceCategory category);

	Collection<PedagogicalAndDocumentaryResource> retrievePedagogicalAndDocumentaryResourcesWithCategory(
			ResourceCategory category);

	UseMode createUseMode(String name, Organisation referredOrganisation);

	UseMode updateUseMode(UseMode resource);

	UseMode retrieveUseMode(Long idUseMode, boolean initialize);

	Boolean deleteUseMode(Long id);

	Boolean associateUseModeAndResource(UseMode useMode, Resource resource);

	Boolean dissociateUseModeAndResource(UseMode useMode, Resource resource);

	Collection<Resource> getResourcesInCategoryForPerson(ResourceCategory category, Person person);

}
