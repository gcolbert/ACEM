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

import eu.ueb.acem.domain.beans.bleu.TeachingMode;

/**
 * A service to manage {@link TeachingMode}s.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-16
 */
public interface TeachingModesService extends Serializable {

	/**
	 * Counts the number of persistent TeachingMode objects
	 * 
	 * @return number of teaching modes
	 */
	Long countTeachingModes();

	/**
	 * Creates a new persistent TeachingMode.
	 * 
	 * @param name
	 *            A name for the TeachingMode
	 * @param description
	 *            A description for the TeachingMode
	 * @return The newly created TeachingMode, or null.
	 */
	TeachingMode createTeachingMode(String name, String description);

	/**
	 * Returns the TeachingMode whose id is given.
	 * 
	 * @param id
	 *            The id of the TeachingMode to retrieve
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return The TeachingMode with the given id, or null if it doesn't exist
	 */
	TeachingMode retrieveTeachingMode(Long id, boolean initialize);

	/**
	 * Updates the given TeachingMode and returns the updated entity.
	 * 
	 * @param teachingMode
	 *            The TeachingMode to update
	 * @return the updated entity
	 */
	TeachingMode updateTeachingMode(TeachingMode teachingMode);

	/**
	 * Deletes the TeachingMode whose id is given.
	 * 
	 * @param id
	 *            The id of the TeachingMode to delete.
	 * @return true if the entity doesn't exist after the call, false otherwise
	 */
	Boolean deleteTeachingMode(Long id);

}
