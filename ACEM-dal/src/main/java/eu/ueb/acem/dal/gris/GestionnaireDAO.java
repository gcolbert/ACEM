package eu.ueb.acem.dal.gris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.GestionnaireRepository;
import eu.ueb.acem.domain.beans.gris.Gestionnaire;
import eu.ueb.acem.domain.beans.gris.neo4j.GestionnaireNode;

@Repository("gestionnaireDAO")
public class GestionnaireDAO implements DAO<Gestionnaire> {

	@Autowired
	private GestionnaireRepository gestionnaireRepository;
	
	public GestionnaireDAO() {
		
	}
	
	@Override
	public Gestionnaire create(String nom) {
		GestionnaireNode gestionnaire = new GestionnaireNode(nom);
		gestionnaireRepository.save(gestionnaire);
		return gestionnaire;
	}
	
	@Override
	public Gestionnaire retrieve(String nom) {
		return gestionnaireRepository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Gestionnaire update(Gestionnaire gestionnaire) {
		GestionnaireNode GestionnaireNode = (GestionnaireNode) gestionnaire;
		return gestionnaireRepository.save(GestionnaireNode);
	}
	
	@Override
	public void delete(Gestionnaire gestionnaire) {
		GestionnaireNode gestionnaireNode = (GestionnaireNode) gestionnaire;
		gestionnaireRepository.delete(gestionnaireNode);
	}
	
	@Override
	public void deleteAll() {
		gestionnaireRepository.deleteAll();
	}

	@Override
	public Long count() {
		return gestionnaireRepository.count();
	}

}
