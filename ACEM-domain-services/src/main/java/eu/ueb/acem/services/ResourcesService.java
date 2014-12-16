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

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ResourcesService {

	ResourceCategory createResourceCategory(String name, String description, String iconFileName);

	ResourceCategory retrieveResourceCategory(Long id);

	ResourceCategory updateResourceCategory(ResourceCategory resourceCategory);

	Collection<ResourceCategory> retrieveCategoriesForResourceType(String resourceType);

	Collection<ResourceCategory> retrieveAllCategories();

	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(Long id);

	Resource createResource(Long toolCategoryId, Long supportServiceId, String resourceType, String name, String iconFileName);

	Resource updateResource(Resource resource);

	Resource retrieveResource(Long id, boolean initialize);

	Boolean deleteResource(Long id);

	Collection<? extends Resource> retrieveSoftwaresWithCategory(ResourceCategory category);

	Collection<? extends Resource> retrieveSoftwareDocumentationsWithCategory(ResourceCategory category);

	Collection<? extends Resource> retrieveEquipmentWithCategory(ResourceCategory category);

	Collection<? extends Resource> retrieveProfessionalTrainingsWithCategory(ResourceCategory category);

	Collection<? extends Resource> retrievePedagogicalAndDocumentaryResourcesWithCategory(ResourceCategory category);

	void saveResourceName(String resourceType, Long id, String label);

	UseMode createUseMode(String name);

	UseMode updateUseMode(UseMode resource);

	UseMode retrieveUseMode(Long idUseMode, boolean initialize);

	Boolean deleteUseMode(Long id);
	
}
