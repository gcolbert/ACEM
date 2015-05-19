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
package eu.ueb.acem.domain.beans.violet;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Class extends Serializable, Comparable<Class> {

	Long getId();

	String getName();

	void setName(String name);

	String getDate();

	void setDate(String date);

	String getTime();

	void setTime(String time);

	String getDuration();

	void setDuration(String duration);

	String getTeachingMode();

	void setTeachingMode(String teachingMode);

	Integer getNumberOfLearners();

	void setNumberOfLearners(Integer numberOfLearners);

	Course getCourse();

	void setCourse(Course course);

	PhysicalSpace getLocation();

	void setLocation(PhysicalSpace location);

	Set<PedagogicalScenario> getPedagogicalScenarios();

	void setPedagogicalScenarios(Set<PedagogicalScenario> pedagogicalScenarios);

}
