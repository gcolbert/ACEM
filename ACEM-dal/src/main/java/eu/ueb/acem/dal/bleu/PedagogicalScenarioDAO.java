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
package eu.ueb.acem.dal.bleu;

import java.util.Collection;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * @author Grégoire Colbert
 * @since 2015-02-27
 * 
 */
public interface PedagogicalScenarioDAO<ID> extends DAO<ID, PedagogicalScenario> {

	Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author);
	
	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithPedagogicalAnswer(PedagogicalAnswer answer);

	Collection<PedagogicalScenario> retrieveScenariosAssociatedWithResourceCategory(ResourceCategory resourceCategory);

}
