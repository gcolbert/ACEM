package eu.ueb.acem.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

public class BesoinReponseServiceImpl implements BesoinReponseService {
	
	@Autowired
	DAO<Besoin> besoinDAO;
	
	@Autowired
	DAO<Reponse> reponseDAO;
	
	public BesoinReponseServiceImpl() {
		
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
	public List<Besoin> getChildren(Besoin parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Besoin parent, Besoin enfant) {
		// TODO Auto-generated method stub
		
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
