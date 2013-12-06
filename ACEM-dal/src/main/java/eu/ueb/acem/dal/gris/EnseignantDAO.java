package eu.ueb.acem.dal.gris;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.EnseignantRepository;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

@Repository("enseignantDAO")
public class EnseignantDAO implements DAO<Enseignant> {

	@Autowired
	private EnseignantRepository repository;
	
	public EnseignantDAO() {
		
	}
	
	@Override
	public void create(Enseignant enseignant) {
		repository.save((EnseignantNode) enseignant);
	}
	
	@Override
	public Enseignant retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Set<Enseignant> retrieveAll() {
		Iterable<EnseignantNode> endResults = repository.findAll();
		Set<Enseignant> set = new HashSet<Enseignant>();
		if (endResults.iterator() != null) {
			Iterator<EnseignantNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Enseignant update(Enseignant enseignant) {
		EnseignantNode EnseignantNode = (EnseignantNode) enseignant;
		return repository.save(EnseignantNode);
	}
	
	@Override
	public void delete(Enseignant enseignant) {
		EnseignantNode enseignantNode = (EnseignantNode) enseignant;
		repository.delete(enseignantNode);
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
