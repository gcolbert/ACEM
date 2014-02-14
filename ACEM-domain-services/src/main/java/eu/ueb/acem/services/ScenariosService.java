package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;

public interface ScenariosService {

	Long countScenarios();

	Scenario createScenario(Personne author, String name, String objective);

	Scenario retrieveScenario(Long id);
	
	Scenario updateScenario(Scenario scenario);

	Boolean deleteScenario(Long id);

	void deleteAllScenarios();

	Long countPedagogicalActivities();

	ActivitePedagogique createPedagogicalActivity(String name);

	ActivitePedagogique retrievePedagogicalActivity(Long id);

	ActivitePedagogique updatePedagogicalActivity(ActivitePedagogique pedagogicalActivity);

	Boolean deletePedagogicalActivity(Long id);

	void deleteAllPedagogicalActivities();

	Collection<Scenario> retrieveScenariosWithAuthor(Personne author);
	
}
