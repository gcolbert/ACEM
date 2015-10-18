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

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of PedagogicalSequence beans.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2015-02-27
 * 
 */
public interface PedagogicalSequenceDAO<ID> extends PedagogicalUnitDAO<ID, PedagogicalSequence> {

	/**
	 * This method retrieves the first sequences of a scenario. First sequences
	 * are sequences associated with a scenario and potentially to following
	 * sequences, but that have no predecessors.
	 * 
	 * @param pedagogicalScenario
	 *            A PedagogicalScenario to extract the collection of first
	 *            sequences from
	 * @return The collection of first sequences in the scenario
	 */
	Collection<PedagogicalSequence> retrieveFirstSequencesOfScenario(PedagogicalScenario pedagogicalScenario);
	
}
