/**
 *     Copyright Grégoire COLBERT 2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free equipment: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free equipment Foundation, either version 3 of the License, or
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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.Equipment;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class EquipmentViewBean extends AbstractResourceViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -555050946530463265L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EquipmentViewBean.class);

	private Equipment domainBean;

	public EquipmentViewBean() {
		super();
	}

	public EquipmentViewBean(Equipment equipment) {
		this();
		setDomainBean(equipment);
	}

	@Override
	public Equipment getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(Equipment domainBean) {
		this.domainBean = domainBean;
		super.setDomainBean(domainBean);
	}

	@Override
	public String getType() {
		return "RESOURCE_TYPE_EQUIPMENT";
	}

}
