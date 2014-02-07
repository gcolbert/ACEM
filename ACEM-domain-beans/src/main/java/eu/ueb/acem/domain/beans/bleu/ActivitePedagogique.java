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
import java.util.Collection;

import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
public interface ActivitePedagogique extends Serializable {

	public Long getId();

	public Long getPositionInScenario();
	
	public void setPositionInScenario(Long stepNumber);
	
	public String getName();

	public void setName(String name);

	public String getObjective();

	public void setObjective(String objectif);

	public String getDescription();

	public void setDescription(String descriptif);

	public String getDuration();

	public void setDuration(String duree);

	public Collection<Scenario> getScenarios();
	
	public Collection<Ressource> getResources();

	public void setResources(Collection<Ressource> resources);
	
}
