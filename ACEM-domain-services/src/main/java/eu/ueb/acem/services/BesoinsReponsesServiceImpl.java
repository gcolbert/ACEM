package eu.ueb.acem.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.dal.bleu.ReponseDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

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
		return besoinDAO.retrieveChildrenOf(besoin);
	}

	@Override
	public Set<Reponse> getReponses(Besoin besoin) {
		return besoinDAO.retrieveLinkedWith(besoin);
	}

	@Override
	@Transactional
	public Besoin createOrUpdateBesoin(Long id, String name, Long idParent) {
		Besoin existingBesoin = besoinDAO.retrieveById(id);
		logger.info("createOrUpdateBesoin, existingBesoin == {}", existingBesoin);
		if (existingBesoin == null) {
			// TODO : ne pas appeler BesoinNode, passer par une factory (?)
			Besoin newBesoin = besoinDAO.create(new BesoinNode(name));
			if (idParent != null) {
				logger.info("createOrUpdateBesoin, idParent == {}", idParent);
				Besoin parent = besoinDAO.retrieveById(idParent);
				//newBesoin.addParent(parent);
				parent.addChild(newBesoin);
				besoinDAO.update(newBesoin);
				besoinDAO.update(parent);
			}
			return newBesoin;
		}
		else {
			existingBesoin.setName(name);
			return besoinDAO.update(existingBesoin);
		}
	}

	@Override
	@Transactional
	public void deleteBesoin(Long id) {
		Besoin besoin = besoinDAO.retrieveById(id);
		if (besoin != null) {
			besoinDAO.delete(besoin);
		}
		else {
			logger.info("Service deleteBesoin, cannot find besoin with id={}", id);
		}
	}
}
