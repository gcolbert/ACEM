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
package eu.ueb.acem.web.viewbeans.bleu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class PedagogicalActivityViewBean implements Pickable, Serializable, Comparable<PedagogicalActivityViewBean> {

	private static final long serialVersionUID = 8190209757734229700L;

	private PedagogicalActivity pedagogicalActivity;

	private Long id;
	private Long positionInScenario;
	private String name;
	private String objective;
	private String instructions;
	private String duration;
	private List<ResourceViewBean> resources;

	public PedagogicalActivityViewBean(PedagogicalActivity pedagogicalActivity) {
		setPedagogicalActivity(pedagogicalActivity);
		resources = new ArrayList<ResourceViewBean>();
	}

	public PedagogicalActivity getPedagogicalActivity() {
		return pedagogicalActivity;
	}

	public void setPedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		this.pedagogicalActivity = pedagogicalActivity;
		setPositionInScenario(pedagogicalActivity.getPositionInScenario());
		setName(pedagogicalActivity.getName());
		setObjective(pedagogicalActivity.getObjective());
		setInstructions(pedagogicalActivity.getInstructions());
		setDuration(pedagogicalActivity.getDuration());
		//setResources(pedagogicalActivity.getResources());
	}

	public Long getPositionInScenario() {
		return positionInScenario;
	}

	public void setPositionInScenario(Long positionInScenario) {
		this.positionInScenario = positionInScenario;
		pedagogicalActivity.setPositionInScenario(positionInScenario);
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		pedagogicalActivity.setName(name);
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
		pedagogicalActivity.setObjective(objective);
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
		pedagogicalActivity.setInstructions(instructions);
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
		pedagogicalActivity.setDuration(duration);
	}

	public List<ResourceViewBean> getResources() {
		return resources;
	}

	public void setResources(List<ResourceViewBean> resources) {
		this.resources = resources;
		//pedagogicalActivity.setResources(resources.); // TODO ajouter les ressources (ou supprimer cette ligne?)
	}

	@Override
	public int compareTo(PedagogicalActivityViewBean o) {
		return getPedagogicalActivity().compareTo(o.getPedagogicalActivity());
	}
	
}
