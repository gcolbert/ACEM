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
package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class TeachingDepartmentViewBean extends AbstractOrganisationViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6787135851384385849L;

	private TeachingDepartment teachingDepartment;

	public TeachingDepartmentViewBean() {
	}

	public TeachingDepartmentViewBean(TeachingDepartment teachingDepartment) {
		this();
		setTeachingDepartment(teachingDepartment);
	}

	public TeachingDepartment getTeachingDepartment() {
		return teachingDepartment;
	}

	public void setTeachingDepartment(TeachingDepartment teachingDepartment) {
		this.teachingDepartment = teachingDepartment;
		super.setDomainBean(teachingDepartment);
	}

	@Override
	public Organisation getDomainBean() {
		return getTeachingDepartment();
	}

	@Override
	public void setDomainBean(Organisation organisation) {
		setTeachingDepartment((TeachingDepartment) organisation);
	}

}
