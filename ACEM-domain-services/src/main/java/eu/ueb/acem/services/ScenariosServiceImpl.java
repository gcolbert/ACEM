package eu.ueb.acem.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.bleu.ActivitePedagogiqueDAO;
import eu.ueb.acem.dal.bleu.ScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ActivitePedagogiqueNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Personne;

@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

	@Autowired
	ScenarioDAO scenarioDAO;

	@Autowired
	ActivitePedagogiqueDAO pedagogicalActivityDAO;

	public ScenariosServiceImpl() {

	}

	@Override
	public Long countScenarios() {
		return scenarioDAO.count();
	}

	@Override
	public Scenario createScenario(Personne author, String name, String objective) {
		return scenarioDAO.create(new ScenarioNode(author, name, objective));
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
	public Long countPedagogicalActivities() {
		return pedagogicalActivityDAO.count();
	}

	@Override
	public ActivitePedagogique createPedagogicalActivity(String name) {
		return pedagogicalActivityDAO.create(new ActivitePedagogiqueNode(name));
	}

	@Override
	public ActivitePedagogique retrievePedagogicalActivity(Long id) {
		return pedagogicalActivityDAO.retrieveById(id);
	}

	@Override
	public ActivitePedagogique updatePedagogicalActivity(ActivitePedagogique pedagogicalActivity) {
		return pedagogicalActivityDAO.update(pedagogicalActivity);
	}

	@Override
	public Boolean deletePedagogicalActivity(Long id) {
		if (pedagogicalActivityDAO.exists(id)) {
			pedagogicalActivityDAO.delete(pedagogicalActivityDAO.retrieveById(id));
		}
		return (!pedagogicalActivityDAO.exists(id));
	}

	@Override
	public void deleteAllPedagogicalActivities() {
		pedagogicalActivityDAO.deleteAll();
	}

	@Override
	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author) {
		return scenarioDAO.retrieveScenariosWithAuthor(author);
	}

}
