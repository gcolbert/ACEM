package eu.ueb.acem.dal.bleu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	private EtapeRepository repository;
	
	public EtapeDAO() {
		
	}
	
	@Override
	public void create(Etape etape) {
		repository.save((EtapeNode) etape);
	}

	@Override
	public Etape retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Set<Etape> retrieveAll() {
		Iterable<EtapeNode> endResults = repository.findAll();
		Set<Etape> set = new HashSet<Etape>();
		if (endResults.iterator() != null) {
			Iterator<EtapeNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Etape update(Etape etape) {
		EtapeNode etapeNode = (EtapeNode) etape;
		return repository.save(etapeNode);
	}
	
	@Override
	public void delete(Etape etape) {
		EtapeNode etapeNode = (EtapeNode) etape;
		repository.delete(etapeNode);
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
