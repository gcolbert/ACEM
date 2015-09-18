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

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.ueb.acem.domain.beans.bleu.PedagogicalUnit;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public abstract class AbstractPedagogicalUnitViewBean<E extends PedagogicalUnit> extends AbstractViewBean implements PedagogicalUnitViewBean {

	private E domainBean;

	private String name;

	private String objective;

	private String start;

	private String duration;

	private String creationDate;

	private String modificationDate;

	private TeachingModeViewBean teachingModeViewBean;

	private List<PedagogicalKeywordViewBean> pedagogicalKeywordViewBeans = new ArrayList<PedagogicalKeywordViewBean>(0);

	private List<PedagogicalUnitViewBean> childrenPrerequisites = new ArrayList<PedagogicalUnitViewBean>(0);

	private List<PedagogicalUnitViewBean> parentsPrerequisites = new ArrayList<PedagogicalUnitViewBean>(0);

	private PedagogicalUnitViewBean nextPedagogicalUnitViewBean;

	@Override
	public E getDomainBean() {
		return domainBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDomainBean(PedagogicalUnit pedagogicalUnit) {
		this.domainBean = (E)pedagogicalUnit;
		setId(domainBean.getId());
		setName(domainBean.getName());
		setObjective(domainBean.getObjective());
		setCreationDate(domainBean.getCreationDate());
		setModificationDate(domainBean.getModificationDate());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		if (domainBean != null) {
			domainBean.setName(name);
		}
	}

	@Override
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
		if (domainBean != null) {
			domainBean.setObjective(objective);
		}
	}

	@Override
	public String getStart() {
		return start;
	}

	@Override
	public void setStart(Long startTimeStampInSeconds) {
		this.start = startTimeStampInSeconds.toString();
	}

	@Override
	public String getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Long duration) {
		this.duration = duration.toString();
	}

	@Override
	public String getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Long creationTimeStampInSeconds) {
		DateTimeFormatter fmt = DateTimeFormat.mediumDateTime();
		this.creationDate = fmt.print(new DateTime(creationTimeStampInSeconds * 1000L));
	}

	@Override
	public String getModificationDate() {
		return modificationDate;
	}

	@Override
	public void setModificationDate(Long modificationTimeStampInSeconds) {
		if (modificationTimeStampInSeconds != null) {
			DateTimeFormatter fmt = DateTimeFormat.mediumDateTime();
			this.modificationDate = fmt.print(new DateTime(modificationTimeStampInSeconds * 1000L));
		}
	}

	@Override
	public TeachingModeViewBean getTeachingModeViewBean() {
		return teachingModeViewBean;
	}

	@Override
	public void setTeachingModeViewBean(TeachingModeViewBean teachingModeViewBean) {
		this.teachingModeViewBean = teachingModeViewBean;
	}

	@Override
	public List<PedagogicalKeywordViewBean> getPedagogicalKeywordViewBeans() {
		return pedagogicalKeywordViewBeans;
	}

	@Override
	public void setPedagogicalKeywordViewBeans(List<PedagogicalKeywordViewBean> pedagogicalKeywordViewBeans) {
		this.pedagogicalKeywordViewBeans = pedagogicalKeywordViewBeans;
	}

	@Override
	public List<PedagogicalUnitViewBean> getPrerequisiteViewBeans() {
		return parentsPrerequisites;
	}

	@Override
	public void setDependentPedagogicalUnitViewBeans(List<PedagogicalUnitViewBean> dependentPedagogicalUnitViewBeans) {
		this.childrenPrerequisites = dependentPedagogicalUnitViewBeans;
	}

	@Override
	public List<PedagogicalUnitViewBean> getDependentPedagogicalUnitViewBeans() {
		return childrenPrerequisites;
	}

	@Override
	public void setPrerequisites(List<PedagogicalUnitViewBean> prerequisiteViewBeans) {
		this.childrenPrerequisites = prerequisiteViewBeans;
	}

	@Override
	public PedagogicalUnitViewBean getNextViewBean() {
		return nextPedagogicalUnitViewBean;
	}

	@Override
	public void setNextViewBean(PedagogicalUnitViewBean pedagogicalUnitViewBean) {
		this.nextPedagogicalUnitViewBean = pedagogicalUnitViewBean;
	}

	@Override
	public int compareTo(PedagogicalUnitViewBean o) {
		return domainBean.compareTo(o.getDomainBean());
	}

}
