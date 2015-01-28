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
package eu.ueb.acem.web.viewbeans.gris;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-04-24
 * 
 */
public class TeacherViewBean extends PersonViewBean {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7825115133733181852L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeacherViewBean.class);

	private Teacher domainBean;

	private List<ToolCategoryViewBean> favoriteToolCategoryViewBeans;

	public TeacherViewBean() {
		super();
		favoriteToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
	}

	public TeacherViewBean(Teacher teacher) {
		this();
		this.setDomainBean(teacher);
	}

	public Teacher getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(Teacher teacher) {
		super.setDomainBean(teacher);
		this.domainBean = teacher;
		for (ResourceCategory toolCategory : teacher.getFavoriteToolCategories()) {
			favoriteToolCategoryViewBeans.add(new ToolCategoryViewBean(toolCategory));
		}
	}

	public List<ToolCategoryViewBean> getFavoriteToolCategoryViewBeans() {
		return favoriteToolCategoryViewBeans;
	}

}
