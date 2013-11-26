package eu.ueb.acem.dal.gris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.EnseignantRepository;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

@Repository("enseignantDAO")
public class EnseignantDAO implements DAO<Enseignant> {

	@Autowired
	private EnseignantRepository enseignantRepository;
	
	public EnseignantDAO() {
		
	}
	
	@Override
	public Enseignant create(String nom) {
		EnseignantNode enseignant = new EnseignantNode(nom);
		enseignantRepository.save(enseignant);
		return enseignant;
	}
	
	@Override
	public Enseignant retrieve(String nom) {
		return enseignantRepository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Enseignant update(Enseignant enseignant) {
		EnseignantNode EnseignantNode = (EnseignantNode) enseignant;
		return enseignantRepository.save(EnseignantNode);
	}
	
	@Override
	public void delete(Enseignant enseignant) {
		EnseignantNode enseignantNode = (EnseignantNode) enseignant;
		enseignantRepository.delete(enseignantNode);
	}
	
	@Override
	public void deleteAll() {
		enseignantRepository.deleteAll();
	}

	@Override
	public Long count() {
		return enseignantRepository.count();
	}

}
