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
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-17
 * 
 */
public class ScenarioViewBean implements Pickable, Serializable, Comparable<ScenarioViewBean> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenarioViewBean.class);

	private static final long serialVersionUID = -3164178023755035995L;

	private List<PedagogicalActivityViewBean> pedagogicalActivityViewBeans;
	private List<PersonViewBean> authorViewBeans;

	private Scenario scenario;

	private Long id;
	private String name;
	private String objective;
	private Boolean published;
	private String creationDate;
	private String modificationDate;

	public ScenarioViewBean() {
		pedagogicalActivityViewBeans = new ArrayList<PedagogicalActivityViewBean>();
		authorViewBeans = new ArrayList<PersonViewBean>();
	}

	public ScenarioViewBean(Scenario scenario) {
		this();
		setScenario(scenario);
	}

	public List<PedagogicalActivityViewBean> getPedagogicalActivityViewBeans() {
		return pedagogicalActivityViewBeans;
	}

	public List<PersonViewBean> getAuthorViewBeans() {
		return authorViewBeans;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
		setId(scenario.getId());
		this.name = scenario.getName();
		this.objective = scenario.getObjective();
		this.published = scenario.isPublished();
		setCreationDate(scenario.getCreationDate());
		setModificationDate(scenario.getModificationDate());

		pedagogicalActivityViewBeans.clear();
		for (ActivitePedagogique pedagogicalActivity : scenario.getPedagogicalActivities()) {
			pedagogicalActivityViewBeans.add(new PedagogicalActivityViewBean(pedagogicalActivity));
		}
		Collections.sort(pedagogicalActivityViewBeans);

		authorViewBeans.clear();
		for (Enseignant author : scenario.getAuthors()) {
			authorViewBeans.add(new PersonViewBean(author));
		}
		Collections.sort(authorViewBeans);
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
		scenario.setName(name);
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
		scenario.setObjective(objective);
	}

	public Boolean isPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
		scenario.setPublished(published);
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
	public int compareTo(ScenarioViewBean o) {
		return scenario.compareTo(o.getScenario());
	}

}
