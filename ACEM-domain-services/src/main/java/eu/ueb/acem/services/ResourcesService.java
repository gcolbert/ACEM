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

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ResourcesService {

	Collection<String> getCategoriesForResourceType(String resourceType);

	Ressource createResource(String resourceType, String category, String name);

	Ressource updateResource(Ressource resource);
	
	Ressource getResource(Long id);
	
	Collection<? extends Ressource> getSoftwaresWithCategory(String category);

	Collection<? extends Ressource> getSoftwareDocumentationsWithCategory(String category);

	Collection<? extends Ressource> getEquipmentWithCategory(String category);

	Collection<? extends Ressource> getProfessionalTrainingsWithCategory(String category);

	Collection<? extends Ressource> getPedagogicalAndDocumentaryResourcesWithCategory(String category);

	void saveResourceName(String resourceType, Long id, String label);
	
	Collection<Scenario> getScenariosAssociatedWithRessource(Long id);

}
