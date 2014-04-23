/**
 *     Copyright Grégoire COLBERT 2013
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

import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-04-23
 * 
 */
public class ProfessionalTrainingViewBean implements ResourceViewBean, Serializable, Comparable<ProfessionalTrainingViewBean> {

	private static final long serialVersionUID = -884629268063400124L;

	private FormationProfessionnelle professionalTraining;

	private Long id;

	private String name;

	private Boolean favoriteResource;
	
	public ProfessionalTrainingViewBean() {
	}

	public ProfessionalTrainingViewBean(FormationProfessionnelle professionalTraining) {
		this();
		setProfessionalTraining(professionalTraining);
	}

	@Override
	public FormationProfessionnelle getDomainBean() {
		return professionalTraining;
	}
	
	@Override
	public void setDomainBean(Ressource resource) {
		setProfessionalTraining((FormationProfessionnelle) resource);
	}

	public FormationProfessionnelle getProfessionalTraining() {
		return professionalTraining;
	}

	public void setProfessionalTraining(FormationProfessionnelle professionalTraining) {
		this.professionalTraining = professionalTraining;
		setId(professionalTraining.getId());
		setName(professionalTraining.getName());
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Boolean getFavoriteResource() {
		return favoriteResource;
	}
	
	public void setFavoriteResource(Boolean favoriteResource) {
		this.favoriteResource = favoriteResource;
	}
	
	@Override
	public int compareTo(ProfessionalTrainingViewBean o) {
		return name.compareTo(o.getName());
	}

}
