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
package eu.ueb.acem.dal.common.bleu;

import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of PedagogicalActivity beans.
 * 
 * @author Grégoire Colbert
 * @since 2015-02-27
 * 
 */
public interface PedagogicalActivityDAO<ID> extends PedagogicalUnitDAO<ID, PedagogicalActivity> {

	/**
	 * This method retrieves the first activities of a session. First activities
	 * are activities associated with a session and potentially to following
	 * activities, but that have no predecessors.
	 * 
	 * @param session
	 *            A PedagogicalSession to extract the collection of first
	 *            activities from
	 * @return The collection of first activities in the session
	 */
	Collection<PedagogicalActivity> retrieveFirstActivitiesOfSession(PedagogicalSession session);

}
