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

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of PedagogicalScenario beans.
 * 
 * @author Grégoire Colbert
 * @since 2015-02-27
 * 
 */
public interface PedagogicalScenarioDAO<ID> extends PedagogicalUnitDAO<ID, PedagogicalScenario> {

	/**
	 * This method returns the collection of all scenarios for the specified
	 * author.
	 * 
	 * @param author
	 *            A Person instance
	 * @return The collection of scenarios
	 */
	Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author);

	/**
	 * This method returns the collection of all scenarios indirectly associated
	 * with the given PedagogicalAnswer.
	 * 
	 * @param answer
	 *            A PedagogicalAnswer instance
	 * @return The collection of scenarios associated with the given PedagogicalAnswer
	 */
	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithPedagogicalAnswer(PedagogicalAnswer answer);

	/**
	 * This method returns the collection of all scenarios indirectly associated
	 * with the given ResourceCategory.
	 * 
	 * @param resourceCategory
	 *            A ResourceCategory instance
	 * @return The collection of scenarios associated with the given ResourceCategory
	 */
	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(ResourceCategory resourceCategory);

}
