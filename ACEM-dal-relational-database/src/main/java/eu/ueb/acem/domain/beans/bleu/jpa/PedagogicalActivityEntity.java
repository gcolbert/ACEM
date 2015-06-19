/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.domain.beans.bleu.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "PedagogicalActivity")
@Table(name = "PedagogicalActivity")
public class PedagogicalActivityEntity extends AbstractEntity implements PedagogicalActivity {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3649534092473417932L;

	private String name;

	@ManyToMany(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "PedagogicalScenario_PedagogicalActivity")
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY)
	private Set<ResourceCategory> resourceCategories = new HashSet<ResourceCategory>(0);

	private Long positionInScenario;

	@Lob
	private String objective;

	@Lob
	private String instructions;

	@Lob
	private String duration;

	public PedagogicalActivityEntity() {
	}

	public PedagogicalActivityEntity(String name) {
		this();
		setName(name);
	}

	@Override
	public Set<PedagogicalScenario> getScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setScenarios(Set<PedagogicalScenario> scenarios) {
		this.pedagogicalScenarios = scenarios;
	}

	@Override
	public Long getPositionInScenario() {
		return positionInScenario;
	}

	@Override
	public void setPositionInScenario(Long positionInScenario) {
		this.positionInScenario = positionInScenario;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getObjective() {
		return objective;
	}

	@Override
	public void setObjective(String objective) {
		this.objective = objective;
	}

	@Override
	public String getInstructions() {
		return instructions;
	}

	@Override
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String getDuration() {
		return duration;
	}

	@Override
	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public Set<ResourceCategory> getResourceCategories() {
		return resourceCategories;
	}

	@Override
	public void setResourceCategories(Set<ResourceCategory> resourceCategories) {
		this.resourceCategories = resourceCategories;
	}

	@Override
	public int compareTo(PedagogicalActivity o) {
		return (int) (positionInScenario - o.getPositionInScenario());
	}

}
