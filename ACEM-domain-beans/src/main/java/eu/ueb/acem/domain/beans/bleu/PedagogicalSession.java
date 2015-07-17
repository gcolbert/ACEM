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
import eu.ueb.acem.domain.beans.violet.Class;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
public interface PedagogicalSession extends PedagogicalUnit {

	Set<ResourceCategory> getResourceCategories();

	void setResourceCategories(Set<ResourceCategory> resourceCategories);

	// a Session can be associated with many sequences in parallel, so we use a Set here.
	Set<PedagogicalSequence> getPedagogicalSequences();

	void setPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences);

	PedagogicalSession getNextPedagogicalSession();

	void setNextPedagogicalSession(PedagogicalSession pedagogicalSession);

	// TODO : voir comment gérer les requêtes pour la base relationnelle dans PedagogicalScenarioRepository
//	Set<PedagogicalActivity> getAllPedagogicalActivities();

	PedagogicalActivity getFirstPedagogicalActivity();

	void setFirstPedagogicalActivity(PedagogicalActivity pedagogicalActivity);

	Class getReferredClass();

	void setReferredClass(Class referredClass);

}
