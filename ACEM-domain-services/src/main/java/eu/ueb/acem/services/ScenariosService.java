package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;

public interface ScenariosService {

	public Long countScenarios();

	public Scenario createScenario(Personne author, String name, String objective);

	public Scenario retrieveScenario(Long id);
	
	public Scenario updateScenario(Scenario scenario);

	public Boolean deleteScenario(Long id);

	public void deleteAllScenarios();

	public Long countPedagogicalActivities();

	public ActivitePedagogique createPedagogicalActivity(String name);

	public ActivitePedagogique retrievePedagogicalActivity(Long id);

	public ActivitePedagogique updatePedagogicalActivity(ActivitePedagogique step);

	public Boolean deletePedagogicalActivity(Long id);

	public void deleteAllPedagogicalActivities();

	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author);
	
}
