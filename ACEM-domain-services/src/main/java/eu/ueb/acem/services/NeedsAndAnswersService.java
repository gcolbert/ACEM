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

import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface NeedsAndAnswersService {

	Boolean deleteNode(Long id);
	
	Long countNeeds();

	PedagogicalNeed createNeed(String name);

	Collection<PedagogicalNeed> retrieveNeedsAtRoot();

	PedagogicalNeed retrieveNeed(Long id);

	PedagogicalNeed updateNeed(PedagogicalNeed need);

	Boolean deleteNeed(Long id);

	void deleteAllNeeds();

	Long countAnswers();

	PedagogicalAnswer createAnswer(String name);

	PedagogicalAnswer retrieveAnswer(Long id);

	PedagogicalAnswer updateAnswer(PedagogicalAnswer answer);

	Boolean deleteAnswer(Long id);

	void deleteAllAnswers();

	PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent);

	void saveNeedName(Long id, String newName);
	
	void changeParentOfNeed(Long id, Long idNewParent);

	PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	void saveAnswerName(Long id, String newName);

	Collection<PedagogicalScenario> getScenariosRelatedToAnswer(Long id);

	Collection<AdministrativeDepartment> getAdministrativeDepartmentsRelatedToAnswer(Long id);

	Collection<Resource> getResourcesRelatedToAnswer(Long id);

	Boolean associateAnswerWithToolCategory(Long answerId, Long toolCategoryId);

	Boolean dissociateAnswerWithToolCategory(Long answerId, Long toolCategoryId);

	Boolean associateAnswerWithAdministrativeDepartment(Long answerId, Long administrativeDepartmentId);

	Boolean dissociateAnswerWithAdministrativeDepartment(Long answerId, Long administrativeDepartmentId);
	
}
