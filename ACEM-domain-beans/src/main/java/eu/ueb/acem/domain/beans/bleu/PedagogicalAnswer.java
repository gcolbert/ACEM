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
package eu.ueb.acem.domain.beans.bleu;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface PedagogicalAnswer extends Serializable, Comparable<PedagogicalAnswer> {

	Long getId();

	String getName();

	void setName(String name);
	
	String getDescription();
	
	void setDescription(String description);

	Set<PedagogicalNeed> getNeeds();
	
	void setNeeds(Set<PedagogicalNeed> needs);

	Set<ResourceCategory> getResourceCategories();

	void setResourceCategories(Set<ResourceCategory> resourceCategories);
	
}
