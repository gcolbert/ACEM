package eu.ueb.acem.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.bleu.EtapeDAO;
import eu.ueb.acem.dal.bleu.ScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.Etape;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.EtapeNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Personne;

@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

	@Autowired
	ScenarioDAO scenarioDAO;

	@Autowired
	EtapeDAO stepDAO;

	public ScenariosServiceImpl() {

	}

	@Override
	public Long countScenarios() {
		return scenarioDAO.count();
	}

	@Override
	public Scenario createScenario(String name, Personne author, String objective) {
		return scenarioDAO.create(new ScenarioNode(name, author, objective));
	}

	@Override
	public Scenario retrieveScenario(Long id) {
		return scenarioDAO.retrieveById(id);
	}

	@Override
	public Scenario updateScenario(Scenario scenario) {
		return scenarioDAO.update(scenario);
	}

	@Override
	public Boolean deleteScenario(Long id) {
		if (scenarioDAO.exists(id)) {
			scenarioDAO.delete(scenarioDAO.retrieveById(id));
		}
		return (!scenarioDAO.exists(id));
	}

	@Override
	public void deleteAllScenarios() {
		scenarioDAO.deleteAll();
	}

	@Override
	public Long countSteps() {
		return stepDAO.count();
	}

	@Override
	public Etape createStep(String name) {
		return stepDAO.create(new EtapeNode(name));
	}

	@Override
	public Etape retrieveStep(Long id) {
		return stepDAO.retrieveById(id);
	}

	@Override
	public Etape updateStep(Etape step) {
		return stepDAO.update(step);
	}

	@Override
	public Boolean deleteStep(Long id) {
		if (stepDAO.exists(id)) {
			stepDAO.delete(stepDAO.retrieveById(id));
		}
		return (!stepDAO.exists(id));
	}

	@Override
	public void deleteAllSteps() {
		stepDAO.deleteAll();
	}

	@Override
	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author) {
		return scenarioDAO.retrieveScenariosWithAuthor(author);
	}

}
