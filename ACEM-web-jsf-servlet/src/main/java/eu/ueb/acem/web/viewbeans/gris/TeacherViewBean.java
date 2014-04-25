/**
 *     Copyright Grégoire COLBERT 2013
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

import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.web.viewbeans.jaune.DocumentaryAndPedagogicalResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.EquipmentViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ProfessionalTrainingViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareDocumentationViewBean;
import eu.ueb.acem.web.viewbeans.jaune.SoftwareViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-04-24
 * 
 */
public class TeacherViewBean extends PersonViewBean {

	private static final long serialVersionUID = -7825115133733181852L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeacherViewBean.class);
	
	private Enseignant domainBean;
	
	private List<ResourceViewBean> favoriteResourceViewBeans;
	
	public TeacherViewBean() {
		super();
		this.favoriteResourceViewBeans = new ArrayList<ResourceViewBean>();
	}
	
	public TeacherViewBean(Enseignant teacher) {
		this();
		this.setDomainBean(teacher);
	}

	public Enseignant getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(Enseignant teacher) {
		super.setDomainBean(teacher);
		this.domainBean = teacher;
		for (Ressource resource : teacher.getFavoriteResources()) {
			if (resource instanceof Equipement) {
				addFavoriteResourceViewBean(new EquipmentViewBean((Equipement)resource));
			}
			else if (resource instanceof Applicatif) {
				addFavoriteResourceViewBean(new SoftwareViewBean((Applicatif)resource));
			}
			else if (resource instanceof DocumentationApplicatif) {
				addFavoriteResourceViewBean(new SoftwareDocumentationViewBean((DocumentationApplicatif)resource));
			}
			else if (resource instanceof FormationProfessionnelle) {
				addFavoriteResourceViewBean(new ProfessionalTrainingViewBean((FormationProfessionnelle)resource));
			}
			else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
				addFavoriteResourceViewBean(new DocumentaryAndPedagogicalResourceViewBean((RessourcePedagogiqueEtDocumentaire)resource));
			}
		}
	}

	public List<ResourceViewBean> getFavoriteResourceViewBeans() {
		return favoriteResourceViewBeans;
	}

	public void addFavoriteResourceViewBean(ResourceViewBean resourceViewBean) {
		favoriteResourceViewBeans.add(resourceViewBean);
		resourceViewBean.setFavoriteResource(true);
	}
	
	public void removeFavoriteResourceViewBean(ResourceViewBean resourceViewBean) {
		favoriteResourceViewBeans.remove(resourceViewBean);
		resourceViewBean.setFavoriteResource(false);
	}
	
}
