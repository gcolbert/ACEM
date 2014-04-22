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

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.rouge.Service;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface NeedsAndAnswersService {

	Boolean deleteNode(Long id);
	
	Long countNeeds();

	Besoin createNeed(String name);

	Collection<Besoin> retrieveNeedsAtRoot();

	Besoin retrieveNeed(Long id);

	Besoin updateNeed(Besoin need);

	Boolean deleteNeed(Long id);

	void deleteAllNeeds();

	Long countAnswers();

	Reponse createAnswer(String name);

	Reponse retrieveAnswer(Long id);

	Reponse updateAnswer(Reponse answer);

	Boolean deleteAnswer(Long id);

	void deleteAllAnswers();

	Besoin createOrUpdateNeed(Long id, String name, Long idParent);

	void saveNeedName(Long id, String newName);
	
	void changeParentOfNeed(Long id, Long idNewParent);

	Reponse createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	void saveAnswerName(Long id, String newName);

	Collection<Scenario> getScenariosRelatedToAnswer(Long id);

	Collection<Service> getAdministrativeDepartmentsRelatedToAnswer(Long id);

	Collection<Ressource> getResourcesRelatedToAnswer(Long id);

	Boolean associateAnswerWithToolCategory(Long answerId, Long toolCategoryId);

	Boolean dissociateAnswerWithToolCategory(Long answerId, Long toolCategoryId);

}
