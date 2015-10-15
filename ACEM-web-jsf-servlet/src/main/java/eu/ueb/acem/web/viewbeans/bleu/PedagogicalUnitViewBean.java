/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.viewbeans.bleu;

import java.util.List;

import eu.ueb.acem.domain.beans.bleu.PedagogicalUnit;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public interface PedagogicalUnitViewBean  extends Pickable, Comparable<PedagogicalUnitViewBean> {

	@Override
	Long getId();

	PedagogicalUnit getDomainBean();

	void setDomainBean(PedagogicalUnit resource);

	String getName();

	void setName(String name);

	String getObjective();

	void setObjective(String objective);

	String getStart();

	void setStart(Long startTimeStampInSeconds);

	String getDuration();

	void setDuration(String duration);

	String getCreationDate();

	void setCreationDate(Long date);

	String getModificationDate();

	void setModificationDate(Long date);

	TeachingModeViewBean getTeachingModeViewBean();

	void setTeachingModeViewBean(TeachingModeViewBean teachingModeViewBean);

	List<PedagogicalKeywordViewBean> getPedagogicalKeywordViewBeans();

	void setPedagogicalKeywordViewBeans(List<PedagogicalKeywordViewBean> pedagogicalKeywordViewBeans);

	List<PedagogicalUnitViewBean> getPrerequisiteViewBeans();

	void setPrerequisites(List<PedagogicalUnitViewBean> prerequisiteViewBeans);

	List<PedagogicalUnitViewBean> getDependentPedagogicalUnitViewBeans();

	void setDependentPedagogicalUnitViewBeans(List<PedagogicalUnitViewBean> dependentPedagogicalUnitViewBeans);

	PedagogicalUnitViewBean getNextViewBean();

	void setNextViewBean(PedagogicalUnitViewBean pedagogicalUnitViewBean);

}
