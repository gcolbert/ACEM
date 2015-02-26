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

import javax.inject.Named;

import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;

/**
 * @author Grégoire Colbert
 * @since 2014-04-23
 * 
 */
@Named
public class ProfessionalTrainingViewBean extends AbstractResourceViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -884629268063400124L;

	private ProfessionalTraining domainBean;

	public ProfessionalTrainingViewBean() {
		super();
	}

	public ProfessionalTrainingViewBean(ProfessionalTraining professionalTraining) {
		this();
		setDomainBean(professionalTraining);
	}

	@Override
	public ProfessionalTraining getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(ProfessionalTraining domainBean) {
		this.domainBean = domainBean;
		super.setDomainBean(domainBean);
	}

	@Override
	public String getType() {
		return "RESOURCE_TYPE_PROFESSIONAL_TRAINING";
	}
}
