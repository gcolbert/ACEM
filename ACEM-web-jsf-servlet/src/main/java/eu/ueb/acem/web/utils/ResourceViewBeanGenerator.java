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
package eu.ueb.acem.web.utils;

import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-28
 * 
 */
public class ResourceViewBeanGenerator {

	public static ResourceViewBean getViewBean(Resource resource) {
		ResourceViewBean viewBean = null;
		if (resource != null) {
			if (resource instanceof Software) {
				viewBean = new SoftwareViewBean((Software) resource);
			}
			else if (resource instanceof PedagogicalAndDocumentaryResource) {
				viewBean = new DocumentaryAndPedagogicalResourceViewBean((PedagogicalAndDocumentaryResource) resource);
			}
			else if (resource instanceof Equipment) {
				viewBean = new EquipmentViewBean((Equipment) resource);
			}
			else if (resource instanceof SoftwareDocumentation) {
				viewBean = new SoftwareDocumentationViewBean((SoftwareDocumentation) resource);
			}
			else if (resource instanceof ProfessionalTraining) {
				viewBean = new ProfessionalTrainingViewBean((ProfessionalTraining) resource);
			}
		}
		return viewBean;
	}
}
