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
 * A service to manage the pedagogical needs and pedagogical answers.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface NeedsAndAnswersService extends Serializable {

	/**
	 * Deletes the pedagogical need or pedagogical answer whose id is given.
	 * 
	 * @param id
	 *            The id of the entity to delete.
	 * @return true if the entity doesn't exist after the call, false otherwise
	 */
	Boolean deletePedagogicalNeedOrPedagogicalAnswer(Long id);

	/**
	 * Counts the number of persistent PedagogicalNeed objects
	 * 
	 * @return number of pedagogical needs
	 */
	Long countNeeds();

	/**
	 * Creates a new persistent PedagogicalNeed.
	 * 
	 * @param name
	 *            A name describing the PedagogicalNeed (e.g.
	 *            "How to better interact with the students?")
	 * @return The newly created PedagogicalNeed, or null.
	 */
	PedagogicalNeed createPedagogicalNeed(String name);

	/**
	 * Creates a new persistent PedagogicalNeed.
	 * 
	 * @param name
	 *            A name for the PedagogicalNeed (e.g.
	 *            "How to better interact with the students?")
	 * @param description
	 *            A description of the PedagogicalNeed (e.g.
	 *            "Interacting with the students is essential.")
	 * @return The newly created PedagogicalNeed, or null.
	 */
	PedagogicalNeed createPedagogicalNeed(String name, String description);

	/**
	 * Creates a new persistent PedagogicalNeed and adds it as a child of the
	 * given PedagogicalNeed (that must already exists in the datastore).
	 * 
	 * @param name
	 *            A name for the PedagogicalNeed (e.g.
	 *            "How to better interact with the students?")
	 * @param parent
	 *            A parent PedagogicalNeed
	 * @return The newly created PedagogicalNeed, or null.
	 */
	PedagogicalNeed createPedagogicalNeed(String name, PedagogicalNeed parent);

	/**
	 * Creates a new persistent PedagogicalNeed and adds it as a child of the
	 * given PedagogicalNeed (that must already exists in the datastore).
	 * 
	 * @param name
	 *            A name for the PedagogicalNeed (e.g.
	 *            "How to better interact with the students?")
	 * @param description
	 *            A description of the PedagogicalNeed (e.g.
	 *            "Interacting with the students is essential.")
	 * @param parent
	 *            A parent PedagogicalNeed
	 * @return The newly created PedagogicalNeed, or null.
	 */
	PedagogicalNeed createPedagogicalNeed(String name, String description, PedagogicalNeed parent);

	/**
	 * Returns the collection of {@link PedagogicalNeed}s that are the children
	 * of no other PedagogicalNeed, and as such are located at the root of the
	 * tree.
	 * 
	 * @return the collection of PedagogicalNeeds located at the root of the
	 *         tree
	 */
	Collection<PedagogicalNeed> retrieveNeedsAtRoot();

	/**
	 * Returns the PedagogicalNeed whose id is given.
	 * 
	 * @param id
	 *            The id of the PedagogicalNeed to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return The PedagogicalNeed with the given id, or null if it doesn't
	 *         exist
	 */
	PedagogicalNeed retrievePedagogicalNeed(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalNeed and returns the updated entity.
	 * 
	 * @param need
	 *            The PedagogicalNeed to update
	 * @return the updated entity
	 */
	PedagogicalNeed updatePedagogicalNeed(PedagogicalNeed need);

	/**
	 * Deletes the PedagogicalNeed whose id is given.
	 * 
	 * @param id
	 *            The id of the PedagogicalNeed to delete.
	 * @return true if the entity doesn't exist after the call, false otherwise
	 */
	Boolean deleteNeed(Long id);

	/**
	 * Counts the number of persistent PedagogicalAnswer objects
	 * 
	 * @return number of pedagogical answers
	 */
	Long countAnswers();

	/**
	 * Creates a new persistent PedagogicalAnswer.
	 * 
	 * @param name
	 *            A name for the PedagogicalAnswer (e.g.
	 *            "Use hand-held voting devices")
	 * @return The newly created PedagogicalAnswer, or null.
	 */
	PedagogicalAnswer createPedagogicalAnswer(String name);

	/**
	 * Creates a new persistent PedagogicalAnswer.
	 * 
	 * @param name
	 *            A name for the PedagogicalAnswer (e.g.
	 *            "Use hand-held voting devices")
	 * @param description
	 *            A description for the PedagogicalAnswer (e.g.
	 *            "Using hand-held voting devices during your course helps you see if the students understand you"
	 *            )
	 * @return The newly created PedagogicalAnswer, or null.
	 */
	PedagogicalAnswer createPedagogicalAnswer(String name, String description);

	/**
	 * Creates a new persistent PedagogicalAnswer and associated it with the
	 * given PedagogicalNeed (that must already exists in the datastore).
	 * 
	 * @param name
	 *            A name for the PedagogicalNeed (e.g.
	 *            "Use hand-held voting devices")
	 * @param needToAssociate
	 *            A PedagogicalNeed to associate with the new PedagogicalAnswer
	 * @return The newly created PedagogicalNeed, or null.
	 */
	PedagogicalAnswer createPedagogicalAnswer(String name, PedagogicalNeed needToAssociate);

	/**
	 * Creates a new persistent PedagogicalAnswer.
	 * 
	 * @param name
	 *            A name for the PedagogicalAnswer (e.g.
	 *            "Use hand-held voting devices")
	 * @param description
	 *            A description for the PedagogicalAnswer (e.g.
	 *            "Using hand-held voting devices during your course helps you see if the students understand you"
	 *            )
	 * @param needToAssociate
	 *            A PedagogicalNeed to associate with the new PedagogicalAnswer
	 * @return The newly created PedagogicalAnswer, or null.
	 */
	PedagogicalAnswer createPedagogicalAnswer(String name, String description, PedagogicalNeed needToAssociate);

	/**
	 * Returns the PedagogicalAnswer whose id is given.
	 * 
	 * @param id
	 *            The id of the PedagogicalAnswer to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return The PedagogicalAnswer with the given id, or null if it doesn't
	 *         exist
	 */
	PedagogicalAnswer retrievePedagogicalAnswer(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalAnswer and returns the updated entity.
	 * 
	 * @param answer
	 *            The PedagogicalAnswer to update
	 * @return the updated entity
	 */
	PedagogicalAnswer updatePedagogicalAnswer(PedagogicalAnswer answer);

	/**
	 * Deletes the PedagogicalAnswer whose id is given.
	 * 
	 * @param id
	 *            The id of the PedagogicalAnswer to delete.
	 * @return true if the entity doesn't exist after the call, false otherwise
	 */
	Boolean deleteAnswer(Long id);

	/**
	 * This method creates or updates a PedagogicalNeed whose id is given. It
	 * creates a new PedagogicalNeed if the id doesn't match any existing
	 * PedagogicalNeed, otherwise it updates the existing PedagogicalNeed with
	 * the given name and parent. If the given parent id is not found, the
	 * created or updated node will still be created/updated.
	 * 
	 * @param id
	 *            The id of the PedagogicalNeed to create or update
	 * @param name
	 *            The new name
	 * @param idParent
	 *            The new parent
	 * @return The created or updated PedagogicalNeed
	 */
	PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent);

	/**
	 * Changes the parent need of a PedagogicalNeed, or the associated need of a
	 * PedagogicalAnswer. All previously associated PedagogicalNeeds are removed
	 * from the modified entity.
	 * 
	 * @param id
	 *            The id of an existing PedagogicalNeed or PedagogicalAnswer
	 * @param idNewParent
	 *            The id of a PedagogicalNeed
	 */
	void changeParentOfNeedOrAnswer(Long id, Long idNewParent);

	/**
	 * This method creates or updates a PedagogicalAnswer whose id is given. It
	 * creates a new PedagogicalAnswer if the id doesn't match any existing
	 * PedagogicalAnswer, otherwise it updates the existing PedagogicalAnswer
	 * with the given name and associated PedagogicalNeed. If the associated
	 * PedagogicalNeed id does not lead to an existing entity, then then created
	 * or updated node will still be created/updated.
	 * 
	 * @param id
	 *            The id of the PedagogicalAnswer to create or update
	 * @param name
	 *            The new name
	 * @param idAssociatedNeed
	 *            The PedagogicalNeed to associate with the new/updated
	 *            PedagogicalAnswer
	 * @return The created or updated PedagogicalAnswer
	 */
	PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	/**
	 * Returns the collection of {@link PedagogicalScenario}s which are
	 * transitively related to the given PedagogicalAnswer id.
	 * 
	 * @param id
	 *            The id of the PedagogicalAnswer
	 * @return The collection of pedagogical scenarios
	 */
	Collection<PedagogicalScenario> retrieveScenariosRelatedToAnswer(Long id);

	/**
	 * Associates a PedagogicalAnswer with a ResourceCategory.
	 * 
	 * @param answerId
	 *            The id of the PedagogicalAnswer
	 * @param resourceCategoryId
	 *            The id of the ResourceCategory
	 * @return true if entities are associated at the end of the call, false
	 *         otherwise
	 */
	Boolean associateAnswerWithResourceCategory(Long answerId, Long resourceCategoryId);

	/**
	 * Dissociates a PedagogicalAnswer from a ResourceCategory.
	 * 
	 * @param answerId
	 *            The id of the PedagogicalAnswer
	 * @param resourceCategoryId
	 *            The id of the ResourceCategory
	 * @return true if entities are dissociated at the end of the call, false
	 *         otherwise
	 */
	Boolean dissociateAnswerWithResourceCategory(Long answerId, Long resourceCategoryId);

}
