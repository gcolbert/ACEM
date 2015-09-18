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

	/**
	 * The name of the PedagogicalUnit. It should be distinctive and short.
	 * 
	 * @return the name of the PedagogicalUnit
	 */
	String getName();

	void setName(String name);

	/**
	 * A description of this PedagogicalUnit.
	 * It can be long, and can contain formatted HTML.
	 * 
	 * @return the description of the PedagogicalUnit
	 */
	String getDescription();

	void setDescription(String description);

	/**
	 * The objective (purpose) of this PedagogicalUnit relatively to the
	 * Scenario. It can be long, and can contain formatted HTML.
	 * 
	 * @return the objective
	 */
	String getObjective();

	void setObjective(String objective);

	/**
	 * A text describing what the students need to know or know-how before they can begin this PedagogicalUnit.
	 * It can be long, and can contain formatted HTML.
	 * 
	 * @return the prerequisites
	 */
	String getPrerequisites();

	void setPrerequisites(String prerequisites);

	/**
	 * The targeted skills of the students at the end of this PedagogicalUnit,
	 * what will they be able to understand or to do once they have completed
	 * this PedagogicalUnit.
	 * 
	 * @return a string describing the targeted skills
	 */
	String getTargetedSkills();

	void setTargetedSkills(String targetedSkills);

	/**
	 * The URL to the education material/medium of this PedagogicalUnit.
	 * 
	 * @return URL to the education material
	 */
	String getPedagogicalMaterial();

	void setPedagogicalMaterial(String pedagogicalMaterial);

	/**
	 * The TeachingMode of this PedagogicalUnit.
	 * 
	 * @return the TeachingMode associated with this PedagogicalUnit
	 */
	TeachingMode getTeachingMode();

	void setTeachingMode(TeachingMode teachingMode);

	/**
	 * The keywords (tags) associated with this PedagogicalUnit.
	 * 
	 * @return the Keywords associated with this PedagogicalUnit
	 */
	Set<PedagogicalKeyword> getPedagogicalKeywords();

	void setPedagogicalKeywords(Set<PedagogicalKeyword> pedagogicalKeywords);

	/**
	 * The start time of this PedagogicalUnit. It is a relative number of
	 * seconds, relatively to the owning PedagogicalUnit (the Scenario for a
	 * Sequence, the Sequence for a Session or an Activity, and the Session for
	 * an Activity).
	 * 
	 * @return the start time of the PedagogicalUnit
	 */
	Long getStart();

	void setStart(Long startTimeStampInSeconds);

	/**
	 * The duration of the PedagogicalUnit. It is a number of seconds.
	 * 
	 * @return the duration of the PedagogicalUnit
	 */
	Long getDuration();

	void setDuration(Long durationInSeconds);

	/**
	 * The creation date (as a TimeStamp relative to Unix epoch).
	 * 
	 * @return the creation date
	 */
	Long getCreationDate();

	void setCreationDate(Long date);

	/**
	 * The last modification date (as a TimeStamp relative to Unix epoch).
	 * 
	 * @return the last modification date
	 */
	Long getModificationDate();

	void setModificationDate(Long date);

}
