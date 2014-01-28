package eu.ueb.acem.web.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.ScenariosService;

@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	private Collection<Scenario> scenarios;

	@Autowired
	ScenariosService scenariosService;

	public MyScenariosController() {
		scenarios = new HashSet<Scenario>();
	}

	public Collection<Scenario> getScenarios() {
		try {
			Personne personne = getCurrentUser();
			return scenariosService.retrieveScenariosForUser(personne.getLogin());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashSet<Scenario>();
		}
	}

	public void setScenarios(Collection<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

}
