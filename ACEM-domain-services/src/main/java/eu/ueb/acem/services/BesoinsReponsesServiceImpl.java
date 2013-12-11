package eu.ueb.acem.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

public class BesoinsReponsesServiceImpl implements BesoinsReponsesService {
	
	@Autowired
	DAO<Besoin> besoinDAO;
	
	@Autowired
	DAO<Reponse> reponseDAO;
	
	public BesoinsReponsesServiceImpl() {
		
	}

	@Override
	public DAO<Besoin> getBesoinDAO() {
		return besoinDAO;
	}

	@Override
	public DAO<Reponse> getReponseDAO() {
		return reponseDAO;
	}
	
	@Override
	public Set<Besoin> getBesoinsRacines() {
		Set<Besoin> besoins = besoinDAO.retrieveAll();
		Set<Besoin> besoinsRacines = new HashSet<Besoin>();
		Iterator<Besoin> iterator = besoins.iterator();
		Besoin courant;
		while (iterator.hasNext()) {
			courant = iterator.next();
			if (courant.getParent() == null) {
				besoinsRacines.add(courant);
			}
		}
		return besoinsRacines;
	}

}
