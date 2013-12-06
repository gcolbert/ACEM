package eu.ueb.acem.dal.bleu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.ScenarioRepository;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;

@Repository("scenarioDAO")
public class ScenarioDAO implements DAO<Scenario>{

	@Autowired
	private ScenarioRepository repository;
	
	public ScenarioDAO() {
		
	}
	
	@Override
	public void create(Scenario scenario) {
		repository.save((ScenarioNode) scenario);
	}
	
	@Override
	public Scenario retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Set<Scenario> retrieveAll() {
		Iterable<ScenarioNode> endResults = repository.findAll();
		Set<Scenario> set = new HashSet<Scenario>();
		if (endResults.iterator() != null) {
			Iterator<ScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Scenario update(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		return repository.save(scenarioNode);
	}
	
	@Override
	public void delete(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		repository.delete(scenarioNode);
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}
	
}
