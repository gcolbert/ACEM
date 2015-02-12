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
import java.util.ArrayList;
import java.util.List;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class PedagogicalActivityViewBean extends AbstractViewBean implements Serializable,
		Comparable<PedagogicalActivityViewBean> {

	private static final long serialVersionUID = 8190209757734229700L;

	private PedagogicalActivity domainBean;

	private Long positionInScenario;
	private String name;
	private String objective;
	private String instructions;
	private String duration;
	private List<ToolCategoryViewBean> toolCategoryViewBeans;

	public PedagogicalActivityViewBean() {
		toolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
	}

	public PedagogicalActivityViewBean(PedagogicalActivity pedagogicalActivity) {
		this();
		setDomainBean(pedagogicalActivity);
	}

	public PedagogicalActivity getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(PedagogicalActivity domainBean) {
		this.domainBean = domainBean;
		setId(domainBean.getId());
		setPositionInScenario(domainBean.getPositionInScenario());
		setName(domainBean.getName());
		setObjective(domainBean.getObjective());
		setInstructions(domainBean.getInstructions());
		setDuration(domainBean.getDuration());
	}

	public Long getPositionInScenario() {
		return positionInScenario;
	}

	public void setPositionInScenario(Long positionInScenario) {
		this.positionInScenario = positionInScenario;
		if (domainBean != null) {
			domainBean.setPositionInScenario(positionInScenario);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (domainBean != null) {
			domainBean.setName(name);
		}
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
		if (domainBean != null) {
			domainBean.setObjective(objective);
		}
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
		if (domainBean != null) {
			domainBean.setInstructions(instructions);
		}
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
		if (domainBean != null) {
			domainBean.setDuration(duration);
		}
	}

	public List<ToolCategoryViewBean> getToolCategoryViewBeans() {
		return toolCategoryViewBeans;
	}

	public void setToolCategoryViewBeans(List<ToolCategoryViewBean> toolCategoryViewBeans) {
		this.toolCategoryViewBeans = toolCategoryViewBeans;
	}

	@Override
	public int compareTo(PedagogicalActivityViewBean o) {
		return getDomainBean().compareTo(o.getDomainBean());
	}

}
