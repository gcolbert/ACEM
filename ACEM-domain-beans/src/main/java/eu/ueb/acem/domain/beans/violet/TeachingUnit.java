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
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * The TeachingUnit domain bean interface.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-13
 */
public interface TeachingUnit extends Serializable, Comparable<TeachingUnit> {

	Long getId();

	/**
	 * The id of the original object in the information system, if any.
	 * @return the id of the original object, or null.
	 */
	Long getIdSource();

	void setIdSource(Long idSource);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	Long getDuration();

	void setDuration(Long duration);

	Long getNumberOfLearners();

	void setNumberOfLearners(Long numberOfLearners);

	Long getECTS();

	void setECTS(Long ectsCredits);

	PedagogicalScenario getPedagogicalScenario();

	void setPedagogicalScenario(PedagogicalScenario pedagogicalScenario);

	TeachingDepartment getTeachingDepartment();
	
	void setTeachingDepartment(TeachingDepartment teachingDepartment);

	Set<Teacher> getTeachers();
	
	void setTeachers(Set<Teacher> teachers);

}
