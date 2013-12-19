package eu.ueb.acem.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.dal.bleu.ReponseDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

@Service("besoinsReponsesService")
public class BesoinsReponsesServiceImpl implements BesoinsReponsesService {

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BesoinsReponsesServiceImpl.class);
	
	@Autowired
	BesoinDAO besoinDAO;

	@Autowired
	ReponseDAO reponseDAO;

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
	public Set<Besoin> getBesoinsLies(Besoin besoin) {
		Set<Besoin> besoins = null;
		besoins = besoinDAO.retrieveChildrenOf(besoin);
		/*
		if (nom == null) {
			besoins = besoinDAO.retrieveAll();
			Iterator<Besoin> iterator = besoins.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getParent() != null) {
					iterator.remove();
				}
			}
		}
		else {
			besoins = besoinDAO.retrieveChildrenOf(nom);
		}
		*/
		logger.info("getChildrenOf({}) returned {} children.", besoin.getNom(), besoins.size());
		return besoins;
	}

	@Override
	public Set<Reponse> getReponses(String parent) {
		Set<Reponse> reponses = reponseDAO.retrieveAll();
		return reponses;
	}

}
