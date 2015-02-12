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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-17
 * 
 */
public class PedagogicalScenarioViewBean extends AbstractViewBean implements Serializable,
		Comparable<PedagogicalScenarioViewBean> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 2589803338943536893L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalScenarioViewBean.class);

	private List<PedagogicalActivityViewBean> pedagogicalActivityViewBeans;

	private PedagogicalScenario domainBean;

	private String name;
	private String authors;
	private String objective;
	private Boolean published;
	private String creationDate;
	private String modificationDate;
	private String evaluationModes;

	public PedagogicalScenarioViewBean() {
		pedagogicalActivityViewBeans = new ArrayList<PedagogicalActivityViewBean>();
	}

	public PedagogicalScenarioViewBean(PedagogicalScenario scenario) {
		this();
		setScenario(scenario);
	}

	public List<PedagogicalActivityViewBean> getPedagogicalActivityViewBeans() {
		return pedagogicalActivityViewBeans;
	}

	public PedagogicalScenario getDomainBean() {
		return domainBean;
	}

	public void setScenario(PedagogicalScenario domainBean) {
		this.domainBean = domainBean;
		setId(domainBean.getId());
		this.name = domainBean.getName();
		this.objective = domainBean.getObjective();
		setAuthors(domainBean.getAuthors());
		this.evaluationModes = domainBean.getEvaluationModes();
		this.published = domainBean.isPublished();
		setCreationDate(domainBean.getCreationDate());
		setModificationDate(domainBean.getModificationDate());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		domainBean.setName(name);
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
		domainBean.setObjective(objective);
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Teacher> authors) {
		String authorsAsString = "";
		for (Teacher author : authors) {
			authorsAsString = authorsAsString.concat(author.getName().concat(", "));
		}
		if (authorsAsString.length() > 0) {
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
		domainBean.setEvaluationModes(evaluationModes);
	}

	public Boolean isPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
		domainBean.setPublished(published);
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Long creationTimeStampInSeconds) {
		DateTime creationDate = new DateTime(creationTimeStampInSeconds * 1000L);
		DateTimeFormatter fmt = DateTimeFormat.mediumDateTime();
		this.creationDate = fmt.print(creationDate);
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Long modificationTimeStampInSeconds) {
		if (modificationTimeStampInSeconds != null) {
			DateTime modificationDate = new DateTime(modificationTimeStampInSeconds * 1000L);
			DateTimeFormatter fmt = DateTimeFormat.mediumDateTime();
			this.modificationDate = fmt.print(modificationDate);
		}
	}

	@Override
	public int compareTo(PedagogicalScenarioViewBean o) {
		return domainBean.compareTo(o.getDomainBean());
	}

}
