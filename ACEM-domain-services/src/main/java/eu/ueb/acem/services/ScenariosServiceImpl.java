package eu.ueb.acem.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(ScenariosServiceImpl.class);

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
	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author) {
		Collection<Scenario> scenarios = scenarioDAO.retrieveScenariosWithAuthor(author);
		/*
		if (! (scenarios instanceof List)) {
			scenarios = new ArrayList<Scenario>(scenarios);
			Collections.sort((List<Scenario>)scenarios);
			for (Scenario scenario : scenarios) {
				Set<ActivitePedagogique> activities = (Set<ActivitePedagogique>) scenario.getPedagogicalActivities();
				Iterator<ActivitePedagogique> iterator = activities.iterator();
				while (iterator.hasNext()) {
					// TODO : affecter le retour de retrieveById dans activities (ou refaire toute l'architecture?)
					//pedagogicalActivityDAO.retrieveById(iterator.next().getId());
				}
			}
		}
		*/
			/*
			Collection<ActivitePedagogique> activitiesSet = scenario.getPedagogicalActivities();
			Iterator<ActivitePedagogique> iterator = activitiesSet.iterator();
			List<ActivitePedagogique> activitiesList = new ArrayList<ActivitePedagogique>();
			while (iterator.hasNext()) {
				activitiesList.add(pedagogicalActivityDAO.retrieveById(iterator.next().getId()));
			}
			Collections.sort(activitiesList);
			scenario.setPedagogicalActivities(activitiesList);
			*/
		return scenarios;
	}

	@Override
	public Scenario updateScenario(Scenario scenario) {
		Iterator<ActivitePedagogique> iterator = (Iterator<ActivitePedagogique>)scenario.getPedagogicalActivities().iterator();
		int i = 1;
		while (iterator.hasNext()) {
			ActivitePedagogique pedagogicalActivity = iterator.next();
			pedagogicalActivity.setPositionInScenario(new Long(i));
			pedagogicalActivity = updatePedagogicalActivity(pedagogicalActivity);
			i++;
		}
		// TODO comment passer d'une List à un Set sans casser le pointeur nommé "selectedScenario.pedagogicalActivities"
		scenario = scenarioDAO.update(scenario);
		/*
		scenario.setPedagogicalActivities(new HashSet<ActivitePedagogique>(scenario.getPedagogicalActivities()));
		scenario = scenarioDAO.update(scenario);
		scenario.setPedagogicalActivities(new ArrayList<ActivitePedagogique>(scenario.getPedagogicalActivities()));
		*/
		return scenario;
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
		ActivitePedagogique activity = new ActivitePedagogiqueNode(name);
		return pedagogicalActivityDAO.create(activity);
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

}
