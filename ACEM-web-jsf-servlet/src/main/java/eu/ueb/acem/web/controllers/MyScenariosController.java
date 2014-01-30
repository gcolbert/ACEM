package eu.ueb.acem.web.controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.services.DomainService;

@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController {

	private static final long serialVersionUID = 2943632466935430900L;

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(MyScenariosController.class);
	
	@Autowired
	DomainService domainService;
	
	private Collection<Scenario> scenarios;

	private Personne currentUser;

	@Autowired
	ScenariosService scenariosService;

	public MyScenariosController() {
		scenarios = new HashSet<Scenario>();
	}

	@PostConstruct
	public void initScenariosController() {
		try {
			currentUser = getCurrentUser();
			logger.info("initScenariosController, currentUser={}", currentUser);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createScenario() {
		scenarios.add(scenariosService.createScenario("Nouveau scénario", currentUser, "Objectif du scénario"));
	}

	public Collection<Scenario> getScenarios() {
		return scenariosService.retrieveScenariosWithAuthor(currentUser);
	}

	public void setScenarios(Collection<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

}
