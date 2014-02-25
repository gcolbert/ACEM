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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.web.viewbeans.ViewBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-17
 * 
 */
public class ScenarioViewBean implements ViewBean, Serializable, Comparable<ScenarioViewBean> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenarioViewBean.class);

	private static final long serialVersionUID = -3164178023755035995L;

	private List<PedagogicalActivityViewBean> pedagogicalActivityViewBeans;

	private Scenario scenario;

	private Long id;
	private String name;
	private String objective;
	private PersonViewBean author;
	private Boolean published;

	public ScenarioViewBean() {
		pedagogicalActivityViewBeans = new ArrayList<PedagogicalActivityViewBean>();
	}

	public ScenarioViewBean(Scenario scenario) {
		this();
		setScenario(scenario);
	}

	public List<PedagogicalActivityViewBean> getPedagogicalActivityViewBeans() {
		return pedagogicalActivityViewBeans;
	}

	/*
	public void setPedagogicalActivityViewBeans(List<PedagogicalActivityViewBean> pedagogicalActivityViewBeans) {
		this.pedagogicalActivityViewBeans = pedagogicalActivityViewBeans;
	}
	*/

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
		setId(scenario.getId());
		this.name = scenario.getName();
		this.objective = scenario.getObjective();
		this.author = new PersonViewBean(scenario.getAuthor());
		this.published = scenario.isPublished();
		pedagogicalActivityViewBeans.clear();
		for (ActivitePedagogique pedagogicalActivity : scenario.getPedagogicalActivities()) {
			pedagogicalActivityViewBeans.add(new PedagogicalActivityViewBean(pedagogicalActivity));
		}
		Collections.sort(pedagogicalActivityViewBeans);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public PersonViewBean getAuthor() {
		return author;
	}

	public void setAuthor(PersonViewBean person) {
		this.author = person;
		scenario.setAuthor(person.getPerson());
	}

	public Boolean isPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
		scenario.setPublished(published);
	}

	@Override
	public int compareTo(ScenarioViewBean o) {
		return scenario.compareTo(o.getScenario());
	}

}
