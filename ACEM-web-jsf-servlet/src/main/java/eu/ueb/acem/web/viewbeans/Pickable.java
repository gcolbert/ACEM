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
package eu.ueb.acem.web.viewbeans;

/**
 * This interface must be implemented by "view beans" that will be used inside a
 * PickList component. The "Pickable.getId()" method is used in the
 * PickListConverter class to serialize/deserialize a view bean from client-side
 * to server-side and the other way round.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-25
 * @see <a href="http://www.primefaces.org/showcase/ui/picklist.jsf">PrimeFaces
 *      PickList component</a>
 */
public interface Pickable {

	public Long getId();

}
