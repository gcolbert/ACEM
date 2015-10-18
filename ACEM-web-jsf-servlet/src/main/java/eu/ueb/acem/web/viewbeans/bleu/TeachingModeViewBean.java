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

import java.io.Serializable;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public class TeachingModeViewBean extends AbstractViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3990972011858074591L;

	private TeachingMode domainBean;

	private String name;

	private String description;

	public TeachingModeViewBean() {
		
	}

	public TeachingModeViewBean(TeachingMode domainBean) {
		setDomainBean(domainBean);
	}

	public TeachingMode getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(TeachingMode domainBean) {
		this.domainBean = domainBean;
		setName(domainBean.getName());
		setDescription(domainBean.getDescription());
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

}
