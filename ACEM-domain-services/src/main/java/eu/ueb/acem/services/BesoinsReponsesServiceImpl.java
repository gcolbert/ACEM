package eu.ueb.acem.services;

import java.util.Iterator;
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
		return besoinDAO.retrieveAnswersOf(besoin);
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
				logger.info("idParent is not null, idParent == {}", idParent);
				Besoin parent = besoinDAO.retrieveById(idParent);
				if (parent != null) {
					parent.addChild(newBesoin);
					besoinDAO.update(newBesoin);
					besoinDAO.update(parent);
				}
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

	@Override
	@Transactional
	public void changeParentOfBesoin(Long id, Long idNewParent) {
		logger.info("entering changeParentOfBesoin({}, {})", id, idNewParent);
		// The "visible root node" has id=null, and we must not allow the user to move it
		if (id != null) {
			Besoin besoin = besoinDAO.retrieveById(id);
			logger.info("besoin is retrieved");
			if (besoin != null) {
				logger.info("besoin is not null");
				Set<Besoin> parents = besoin.getParents();
				if (parents != null) {
					logger.info("besoin.getParents() is not null");
					Iterator<Besoin> iterator = parents.iterator();
					if (iterator != null) {
						while (iterator.hasNext()) {
							iterator.next();
							iterator.remove();
							logger.info("besoin has parent removed");
						}
						besoin = besoinDAO.update(besoin);
						logger.info("besoin is updated");
					}
				}
				else {
					logger.info("besoin.getParents() is null");
				}
			}
			if (idNewParent != null) {
				Besoin newParent = besoinDAO.retrieveById(idNewParent);
				logger.info("Service changeParentOfBesoin, newParent={}", newParent);
				if (newParent != null) {
					newParent.addChild(besoin);
					newParent = besoinDAO.update(newParent);
				}
			}
		}
		logger.info("leaving changeParentOfBesoin({}, {})", id, idNewParent);
	}
}
