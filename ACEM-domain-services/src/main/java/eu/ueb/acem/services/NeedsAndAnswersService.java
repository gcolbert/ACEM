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

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface NeedsAndAnswersService extends Serializable {

	Boolean deletePedagogicalNeedOrPedagogicalAnswer(Long id);
	
	Long countNeeds();

	PedagogicalNeed createPedagogicalNeed(String name);

	PedagogicalNeed createPedagogicalNeed(String name, String description);

	PedagogicalNeed createPedagogicalNeed(String name, PedagogicalNeed parent);

	PedagogicalNeed createPedagogicalNeed(String name, String description, PedagogicalNeed parent);

	Collection<PedagogicalNeed> retrieveNeedsAtRoot();

	PedagogicalNeed retrievePedagogicalNeed(Long id, boolean initialize);

	PedagogicalNeed updatePedagogicalNeed(PedagogicalNeed need);

	Boolean deleteNeed(Long id);

	Long countAnswers();

	PedagogicalAnswer createPedagogicalAnswer(String name);

	PedagogicalAnswer createPedagogicalAnswer(String name, String description);

	PedagogicalAnswer createPedagogicalAnswer(String name, PedagogicalNeed parent);

	PedagogicalAnswer createPedagogicalAnswer(String name, String description, PedagogicalNeed parent);

	PedagogicalAnswer retrievePedagogicalAnswer(Long id, boolean initialize);

	PedagogicalAnswer updatePedagogicalAnswer(PedagogicalAnswer answer);

	Boolean deleteAnswer(Long id);

	PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent);

	void changeParentOfNeedOrAnswer(Long id, Long idNewParent);

	PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	Collection<PedagogicalScenario> retrieveScenariosRelatedToAnswer(Long id);

	Boolean associateAnswerWithResourceCategory(Long answerId, Long toolCategoryId);

	Boolean dissociateAnswerWithResourceCategory(Long answerId, Long toolCategoryId);

}
