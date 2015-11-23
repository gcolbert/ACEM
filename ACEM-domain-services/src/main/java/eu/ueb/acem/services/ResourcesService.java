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
 * A service to manage {@link ResourceCategory}, {@link Resource} and
 * {@link UseMode} objects.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface ResourcesService extends Serializable {

	/**
	 * Returns a unique string that can be used to identify {@link Software}s in
	 * methods where the interface {@link Resource} is used.
	 * 
	 * @return a unique string to identify {@link Software}s.
	 */
	String getResourceType_RESOURCE_TYPE_SOFTWARE();

	/**
	 * Returns a unique string that can be used to identify
	 * {@link Documentation}s in methods where the interface {@link Resource} is
	 * used.
	 * 
	 * @return a unique string to identify {@link Documentation}s.
	 */
	String getResourceType_RESOURCE_TYPE_DOCUMENTATION();

	/**
	 * Returns a unique string that can be used to identify {@link Equipment}s
	 * in methods where the interface {@link Resource} is used.
	 * 
	 * @return a unique string to identify {@link Equipment}s.
	 */
	String getResourceType_RESOURCE_TYPE_EQUIPMENT();

	/**
	 * Returns a unique string that can be used to identify
	 * {@link PedagogicalAndDocumentaryResource}s in methods where the interface
	 * {@link Resource} is used.
	 * 
	 * @return a unique string to identify
	 *         {@link PedagogicalAndDocumentaryResource}s.
	 */
	String getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE();

	/**
	 * Returns a unique string that can be used to identify
	 * {@link ProfessionalTraining}s in methods where the interface
	 * {@link Resource} is used.
	 * 
	 * @return a unique string to identify {@link ProfessionalTraining}s.
	 */
	String getResourceType_RESOURCE_TYPE_PROFESSIONAL_TRAINING();

	/**
	 * Creates a new persistent ResourceCategory with the given name,
	 * description and icon.
	 * 
	 * @param name
	 *            The name of the ResourceCategory
	 * @param description
	 *            The description of the ResourceCategory
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The new ResourceCategory
	 */
	ResourceCategory createResourceCategory(String name, String description, String iconFileName);

	/**
	 * Returns the ResourceCategory with the given id.
	 * 
	 * @param id
	 *            The id of the ResourceCategory to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link ResourceCategory} with the given id, or null if it
	 *         doesn't exist
	 */
	ResourceCategory retrieveResourceCategory(Long id, boolean initialize);

	/**
	 * Updates the given ResourceCategory and returns the updated entity.
	 * 
	 * @param resourceCategory
	 *            The ResourceCategory to update
	 * @return the updated entity
	 */
	ResourceCategory updateResourceCategory(ResourceCategory resourceCategory);

	/**
	 * Deletes the ResourceCategory with the given id.
	 * 
	 * @param id
	 *            The id of the ResourceCategory to delete
	 * @return true if the ResourceCategory doesn't exist after the operation,
	 *         false otherwise
	 */
	Boolean deleteResourceCategory(Long id);

	/**
	 * Returns the collection of {@link ResourceCategory} objects that contain
	 * {@link Resource}s of the given type.
	 * 
	 * @param resourceType
	 *            A type of resource (use one of the
	 *            getResourceType_RESOURCE_TYPE_* methods)
	 * @return a collection of ResourceCategory objects
	 */
	Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType);

	/**
	 * Returns the collection of {@link ResourceCategory} objects that contain
	 * {@link Resource}s of the given type visible to the given Person.
	 * 
	 * @param resourceType
	 *            A type of resource (use one of the
	 *            getResourceType_RESOURCE_TYPE_* methods)
	 * @param person
	 *            A Person
	 * @return The collection of matching ResourceCategory objects
	 * @see eu.ueb.acem.dal.common.jaune.ResourceDAO#retrieveCategoriesForPerson(Person)
	 */
	Collection<ResourceCategory> retrieveCategoriesForResourceTypeAndPerson(String resourceType, Person person);

	/**
	 * Returns the collection of {@link ResourceCategory} objects that contain
	 * {@link Resource}s visible to the given Person.
	 * 
	 * @param person
	 *            A Person
	 * @return The collection of matching ResourceCategory objects
	 * @see eu.ueb.acem.dal.common.jaune.ResourceDAO#retrieveCategoriesForPerson(Person)
	 */
	Collection<ResourceCategory> retrieveCategoriesForPerson(Person person);

	/**
	 * Retrieves all existing ResourceCategory objects.
	 * 
	 * @return the collection of all existing resource categories
	 */
	Collection<ResourceCategory> retrieveAllCategories();

	/**
	 * Returns the collection of {@link PedagogicalScenario}s that are
	 * associated with the given ResourceCategory (all scenarios are returned,
	 * even if they are not published in the PedagogicalScenario library).
	 * 
	 * @param category
	 *            A ResourceCategory to filter the PedagogicalScenarios
	 * @return the collection of PedagogicalScenario objects associated with the
	 *         category
	 */
	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(ResourceCategory category);

	/**
	 * Creates a new persistent Resource with associated entities.
	 * 
	 * @param category
	 *            The category of the Resource
	 * @param ownerOrganisation
	 *            The Organisation that owns the Resource
	 * @param supportOrganisation
	 *            The Organisation that supports the Resource
	 * @param resourceType
	 *            The type of the resource (use one of the
	 *            getResourceType_RESOURCE_TYPE_* methods)
	 * @param name
	 *            The name of the Resource
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The new Resource, or null if a parameter is null or resourceType
	 *         has an unrecognized value
	 */
	Resource createResource(ResourceCategory category, Organisation ownerOrganisation,
			Organisation supportOrganisation, String resourceType, String name, String iconFileName);

	/**
	 * Updates the given Resource and returns the updated entity.
	 * 
	 * @param resource
	 *            The Resource to update
	 * @return the updated entity
	 */
	Resource updateResource(Resource resource);

	/**
	 * Returns the Resource with the given id.
	 * 
	 * @param id
	 *            The id of the Resource to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link Resource} with the given id, or null if it doesn't
	 *         exist
	 */
	Resource retrieveResource(Long id, boolean initialize);

	/**
	 * Deletes the Resource with the given id.
	 * 
	 * @param id
	 *            The id of the Resource to delete
	 * @return true if the Resource doesn't exist after the operation, false
	 *         otherwise
	 */
	Boolean deleteResource(Long id);

	/**
	 * Returns the collection of {@link Software}s associated with the given
	 * ResourceCategory.
	 * 
	 * @param category
	 *            A ResourceCategory that could contain Softwares we want to get
	 * @return the collection of all Softwares associated with the given
	 *         category
	 */
	Collection<Software> retrieveSoftwaresWithCategory(ResourceCategory category);

	/**
	 * Returns the collection of {@link Documentation}s associated with the
	 * given ResourceCategory.
	 * 
	 * @param category
	 *            A ResourceCategory that could contain Documentations we want
	 *            to get
	 * @return the collection of all Documentations associated with the given
	 *         category
	 */
	Collection<Documentation> retrieveDocumentationsWithCategory(ResourceCategory category);

	/**
	 * Returns the collection of {@link Equipment}s associated with the given
	 * ResourceCategory.
	 * 
	 * @param category
	 *            A ResourceCategory that could contain Equipments we want to
	 *            get
	 * @return the collection of all Equipments associated with the given
	 *         category
	 */
	Collection<Equipment> retrieveEquipmentsWithCategory(ResourceCategory category);

	/**
	 * Returns the collection of {@link ProfessionalTraining}s associated with
	 * the given ResourceCategory.
	 * 
	 * @param category
	 *            A ResourceCategory that could contain ProfessionalTrainings we
	 *            want to get
	 * @return the collection of all ProfessionalTrainings associated with the
	 *         given category
	 */
	Collection<ProfessionalTraining> retrieveProfessionalTrainingsWithCategory(ResourceCategory category);

	/**
	 * Returns the collection of {@link PedagogicalAndDocumentaryResource}s
	 * associated with the given ResourceCategory.
	 * 
	 * @param category
	 *            A ResourceCategory that could contain
	 *            PedagogicalAndDocumentaryResources we want to get
	 * @return the collection of all PedagogicalAndDocumentaryResources
	 *         associated with the given category
	 */
	Collection<PedagogicalAndDocumentaryResource> retrievePedagogicalAndDocumentaryResourcesWithCategory(
			ResourceCategory category);

	/**
	 * Creates a new persistent {@link UseMode} with the given name, for the
	 * given Organisation.
	 * 
	 * @param name
	 *            A name for the UseMode
	 * @param referredOrganisation
	 *            An Organisation associated with the new UseMode
	 * @return the new UseMode
	 */
	UseMode createUseMode(String name, Organisation referredOrganisation);

	/**
	 * Updates the given UseMode and returns the updated entity.
	 * 
	 * @param resource
	 *            The UseMode to update
	 * @return the updated entity
	 */
	UseMode updateUseMode(UseMode resource);

	/**
	 * Returns the UseMode with the given id.
	 * 
	 * @param idUseMode
	 *            The id of the UseMode to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link UseMode} with the given id, or null if it doesn't
	 *         exist
	 */
	UseMode retrieveUseMode(Long idUseMode, boolean initialize);

	/**
	 * Deletes the UseMode with the given id.
	 * 
	 * @param id
	 *            The id of the UseMode to delete
	 * @return true if the UseMode doesn't exist after the operation, false
	 *         otherwise
	 */
	Boolean deleteUseMode(Long id);

	/**
	 * Associates the given UseMode and Resource.
	 * 
	 * @param useMode
	 *            A UseMode
	 * @param resource
	 *            A Resource
	 * @return True if the UseMode and Resource are associated at the end of the
	 *         call, false otherwise
	 */
	Boolean associateUseModeAndResource(UseMode useMode, Resource resource);

	/**
	 * Dissociates the given UseMode and Resource.
	 * 
	 * @param useMode
	 *            A UseMode
	 * @param resource
	 *            A Resource
	 * @return True if the UseMode and Resource are not associated at the end of
	 *         the call, false otherwise
	 */
	Boolean dissociateUseModeAndResource(UseMode useMode, Resource resource);

	/**
	 * Returns the collection of {@link Resource}s which are associated with the
	 * given {@link ResourceCategory} and visible by the given {@link Person}.
	 * 
	 * @param category
	 *            A ResourceCategory
	 * @param person
	 *            A Person
	 * @return The collection of matching Resources
	 * @see eu.ueb.acem.dal.common.jaune.ResourceDAO#retrieveResourcesInCategoryForPerson(ResourceCategory,
	 *      Person)
	 */
	Collection<Resource> getResourcesInCategoryForPerson(ResourceCategory category, Person person);

}
