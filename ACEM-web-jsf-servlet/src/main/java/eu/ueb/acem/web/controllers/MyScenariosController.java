package eu.ueb.acem.web.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Scenario;

@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	private Collection<Scenario> scenarios;

	public MyScenariosController() {
		setScenarios(new HashSet<Scenario>());
	}

	public Collection<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(Collection<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

}
