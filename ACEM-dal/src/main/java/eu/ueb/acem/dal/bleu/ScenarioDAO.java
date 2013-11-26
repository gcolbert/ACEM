package eu.ueb.acem.dal.bleu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.ScenarioRepository;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;

@Repository("scenarioDAO")
public class ScenarioDAO implements DAO<Scenario>{

	@Autowired
	private ScenarioRepository scenarioRepository;
	
	public ScenarioDAO() {
		
	}
	
	@Override
	public Scenario create(String nom) {
		ScenarioNode scenario = new ScenarioNode(nom);
		scenarioRepository.save(scenario);
		return scenario;
	}
	
	@Override
	public Scenario retrieve(String nom) {
		return scenarioRepository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Scenario update(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		return scenarioRepository.save(scenarioNode);
	}
	
	@Override
	public void delete(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		scenarioRepository.delete(scenarioNode);
	}
	
	@Override
	public void deleteAll() {
		scenarioRepository.deleteAll();
	}

	@Override
	public Long count() {
		return scenarioRepository.count();
	}
	
}
