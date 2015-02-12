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
package eu.ueb.acem.dal;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface DAO<ID extends Serializable, E> extends Serializable {

	Boolean exists(ID id);
	
	E create(E entity);

	E retrieveById(ID id);

	E retrieveById(ID id, boolean initialize);

	Collection<E> retrieveByName(String name);

	Collection<E> retrieveByName(String name, boolean initialize);

	Collection<E> retrieveAll();

	E update(E entity);

	void delete(E entity);

	void deleteAll();

	Long count();

}
