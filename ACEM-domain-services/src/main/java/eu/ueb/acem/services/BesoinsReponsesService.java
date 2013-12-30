package eu.ueb.acem.services;

import java.util.Set;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

public interface BesoinsReponsesService {
	
	public Set<Besoin> getBesoinsLies(Besoin besoin);

	public Set<Reponse> getReponses(Besoin besoin);

	public DAO<Besoin> getBesoinDAO();

	public DAO<Reponse> getReponseDAO();

}
