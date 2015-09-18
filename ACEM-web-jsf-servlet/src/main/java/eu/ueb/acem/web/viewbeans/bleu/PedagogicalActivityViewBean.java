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
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class PedagogicalActivityViewBean extends AbstractPedagogicalUnitViewBean<PedagogicalActivity> implements Serializable {

	private static final long serialVersionUID = 8190209757734229700L;

	private PedagogicalActivity domainBean;

	private String instructions;
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
		super.setDomainBean(domainBean);
		setInstructions(domainBean.getInstructions());
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

	public List<ToolCategoryViewBean> getToolCategoryViewBeans() {
		return toolCategoryViewBeans;
	}

}
