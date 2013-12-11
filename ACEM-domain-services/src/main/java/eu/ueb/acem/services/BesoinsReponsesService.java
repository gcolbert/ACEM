package eu.ueb.acem.services;

import java.util.Set;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

public interface BesoinReponseService {

	Set<Besoin> getBesoinsRacines();
	
	DAO<Besoin> getBesoinDAO();
	
	DAO<Reponse> getReponseDAO();
	
}
