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
package eu.ueb.acem.domain.beans.bleu;

import java.util.Set;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * The PedagogicalActivity domain bean interface.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface PedagogicalActivity extends PedagogicalUnit {

	/**
	 * Gets the instructions given to the students in order to complete the
	 * activity.
	 * 
	 * @return the instructions given to the students
	 */
	String getInstructions();

	/**
	 * Sets the instructions that will be given to the students so that they can
	 * complete the activity.
	 * 
	 * @param instructions
	 *            the instructions given to the students
	 */
	void setInstructions(String instructions);

	// ----------- SEQUENCES --------------

	// Set<PedagogicalSequence> getPedagogicalSequences();
	//
	// void setPedagogicalSequences(Set<PedagogicalSequence>
	// pedagogicalSequences);

	PedagogicalSequence getPedagogicalSequence();

	void setPedagogicalSequence(PedagogicalSequence pedagogicalSequence);

	// ----------- SESSIONS --------------

	// // TODO : enable if an Activity can be associated with many sessions
	// Set<PedagogicalSession> getPedagogicalSessions();
	//
	// void setPedagogicalSessions(Set<PedagogicalSession> pedagogicalSessions);

	// TODO : enable if an Activity can be associated with only one session
	PedagogicalSession getPedagogicalSession();

	void setPedagogicalSession(PedagogicalSession pedagogicalSession);

	// ----------- RESOURCE CATEGORIES --------------

	Set<ResourceCategory> getResourceCategories();

	void setResourceCategories(Set<ResourceCategory> resourceCategories);

	// ----------- LINKING --------------

	PedagogicalActivity getPreviousPedagogicalActivity();

	void setPreviousPedagogicalActivity(PedagogicalActivity pedagogicalActivity);

	PedagogicalActivity getNextPedagogicalActivity();

	void setNextPedagogicalActivity(PedagogicalActivity pedagogicalActivity);

}
