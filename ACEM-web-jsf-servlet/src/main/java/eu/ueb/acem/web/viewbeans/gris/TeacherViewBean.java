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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.gris.Teacher;

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

	public TeacherViewBean() {
		super();
	}

	public TeacherViewBean(Teacher teacher) {
		this();
		this.setDomainBean(teacher);
	}

	@Override
	public Teacher getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(Teacher domainBean) {
		super.setDomainBean(domainBean);
		this.domainBean = domainBean;
	}

}
