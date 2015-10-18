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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * @author Grégoire Colbert
 * @since 2014-02-17
 * 
 */
public class PedagogicalScenarioViewBean extends AbstractPedagogicalUnitViewBean<PedagogicalScenario> implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 2589803338943536893L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalScenarioViewBean.class);

	private List<PedagogicalSequenceViewBean> pedagogicalSequenceViewBeans;

	private String authors;
	private Boolean published;
	private String evaluationModes;

	public PedagogicalScenarioViewBean() {
		pedagogicalSequenceViewBeans = new ArrayList<PedagogicalSequenceViewBean>();
	}

	public PedagogicalScenarioViewBean(PedagogicalScenario scenario) {
		this();
		setScenario(scenario);
	}

	public List<PedagogicalSequenceViewBean> getPedagogicalSequenceViewBeans() {
		return pedagogicalSequenceViewBeans;
	}

	public void setScenario(PedagogicalScenario domainBean) {
		super.setDomainBean(domainBean);
		setAuthors(domainBean.getAuthors());
		setEvaluationModes(domainBean.getEvaluationModes());
		setPublished(domainBean.isPublished());
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Teacher> authors) {
		String authorsAsString = "";
		for (Teacher author : authors) {
			authorsAsString = authorsAsString.concat(author.getName().concat(", "));
		}
		if (! authorsAsString.isEmpty()) {
			// We remove the last two characters (corresponding to the ending
			// ", " characters)
			authorsAsString = authorsAsString.substring(0, authorsAsString.length() - 2);
		}
		this.authors = authorsAsString;
	}

	public String getEvaluationModes() {
		return evaluationModes;
	}

	public void setEvaluationModes(String evaluationModes) {
		this.evaluationModes = evaluationModes;
		if (getDomainBean() != null) {
			((PedagogicalScenario)getDomainBean()).setEvaluationModes(evaluationModes);
		}
	}

	public Boolean isPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
		if (getDomainBean() != null) {
			((PedagogicalScenario)getDomainBean()).setPublished(published);
		}
	}

}
