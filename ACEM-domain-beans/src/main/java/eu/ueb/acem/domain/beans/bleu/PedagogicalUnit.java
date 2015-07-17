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

import java.io.Serializable;
import java.util.Set;

/**
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
public interface PedagogicalUnit extends Serializable, Comparable<PedagogicalUnit> {

	Long getId();

	String getName();

	void setName(String name);

	String getObjective();

	void setObjective(String objective);

	Long getStart();

	void setStart(Long startTimeStampInSeconds);

	Long getDuration();

	void setDuration(Long duration);

	Long getCreationDate();

	void setCreationDate(Long date);

	Long getModificationDate();

	void setModificationDate(Long date);

	TeachingMode getTeachingMode();

	void setTeachingMode(TeachingMode teachingMode);

	Set<PedagogicalKeyword> getPedagogicalKeywords();

	void setPedagogicalKeywords(Set<PedagogicalKeyword> pedagogicalKeywords);

	Set<PedagogicalUnit> getPrequisites();

	void setPrerequisites(Set<PedagogicalUnit> prerequisites);

	Set<PedagogicalUnit> getDependentPedagogicalUnits();

	void setDependentPedagogicalUnits(Set<PedagogicalUnit> dependentPedagogicalUnits);

}
