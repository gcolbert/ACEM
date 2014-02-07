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

	public Long countSteps();

	public ActivitePedagogique createStep(String name);

	public ActivitePedagogique retrieveStep(Long id);

	public ActivitePedagogique updateStep(ActivitePedagogique step);

	public Boolean deleteStep(Long id);

	public void deleteAllSteps();

	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author);
	
}
