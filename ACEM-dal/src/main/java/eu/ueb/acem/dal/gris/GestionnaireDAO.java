package eu.ueb.acem.dal.gris;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.GestionnaireRepository;
import eu.ueb.acem.domain.beans.gris.Gestionnaire;
import eu.ueb.acem.domain.beans.gris.neo4j.GestionnaireNode;

@Repository("gestionnaireDAO")
public class GestionnaireDAO implements DAO<Gestionnaire> {

	@Autowired
	private GestionnaireRepository repository;
	
	public GestionnaireDAO() {
		
	}
	
	@Override
	public void create(Gestionnaire gestionnaire) {
		repository.save((GestionnaireNode) gestionnaire);
	}
	
	@Override
	public Gestionnaire retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Set<Gestionnaire> retrieveAll() {
		Iterable<GestionnaireNode> endResults = repository.findAll();
		Set<Gestionnaire> set = new HashSet<Gestionnaire>();
		if (endResults.iterator() != null) {
			Iterator<GestionnaireNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Gestionnaire update(Gestionnaire gestionnaire) {
		GestionnaireNode GestionnaireNode = (GestionnaireNode) gestionnaire;
		return repository.save(GestionnaireNode);
	}
	
	@Override
	public void delete(Gestionnaire gestionnaire) {
		GestionnaireNode gestionnaireNode = (GestionnaireNode) gestionnaire;
		repository.delete(gestionnaireNode);
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
