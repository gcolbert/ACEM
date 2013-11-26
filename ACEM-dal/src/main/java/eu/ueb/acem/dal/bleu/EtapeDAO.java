package eu.ueb.acem.dal.bleu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.EtapeRepository;
import eu.ueb.acem.domain.beans.bleu.Etape;
import eu.ueb.acem.domain.beans.bleu.neo4j.EtapeNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Repository("etapeDAO")
public class EtapeDAO implements DAO<Etape>{

	@Autowired
	private EtapeRepository etapeRepository;
	
	public EtapeDAO() {
		
	}
	
	@Override
	public Etape create(String nom) {
		EtapeNode etape = new EtapeNode(nom);
		etapeRepository.save(etape);
		return etape;
	}

	@Override
	public Etape retrieve(String nom) {
		return etapeRepository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Etape update(Etape etape) {
		EtapeNode etapeNode = (EtapeNode) etape;
		return etapeRepository.save(etapeNode);
	}
	
	@Override
	public void delete(Etape etape) {
		EtapeNode etapeNode = (EtapeNode) etape;
		etapeRepository.delete(etapeNode);
	}
	
	@Override
	public void deleteAll() {
		etapeRepository.deleteAll();
	}

	@Override
	public Long count() {
		return etapeRepository.count();
	}
	
}
