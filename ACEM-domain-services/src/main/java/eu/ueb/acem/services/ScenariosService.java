package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.Etape;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;

public interface ScenariosService {

	public Long countScenarios();

	public Scenario createScenario(String name, Personne author, String objective);

	public Scenario retrieveScenario(Long id);
	
	public Scenario updateScenario(Scenario scenario);

	public Boolean deleteScenario(Long id);

	public void deleteAllScenarios();

	public Long countSteps();

	public Etape createStep(String name);

	public Etape retrieveStep(Long id);

	public Etape updateStep(Etape step);

	public Boolean deleteStep(Long id);

	public void deleteAllSteps();

	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author);
	
}
