/**
 *     Copyright Grégoire COLBERT 2013
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
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

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Service("needsAndAnswersService")
public class NeedsAndAnswersServiceImpl implements NeedsAndAnswersService {

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(NeedsAndAnswersServiceImpl.class);
	
	@Autowired
	BesoinDAO besoinDAO;

	@Autowired
	ReponseDAO reponseDAO;

	public NeedsAndAnswersServiceImpl() {
		
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
	public Set<Besoin> getNeedsWithParent(Besoin need) {
		return besoinDAO.retrieveChildrenOf(need);
	}

	@Override
	public Set<Reponse> getReponses(Besoin need) {
		return besoinDAO.retrieveAnswersOf(need);
	}

	@Override
	@Transactional
	public Besoin createOrUpdateNeed(Long id, String name, Long idParent) {
		Besoin existingNeed = besoinDAO.retrieveById(id);
		logger.info("createOrUpdateNeed, existingNeed == {}", existingNeed);
		if (existingNeed == null) {
			// TODO : ne pas appeler BesoinNode, passer par une factory (?)
			Besoin newNeed = besoinDAO.create(new BesoinNode(name));
			if (idParent != null) {
				logger.info("idParent is not null, idParent == {}", idParent);
				Besoin parent = besoinDAO.retrieveById(idParent);
				if (parent != null) {
					parent.addChild(newNeed);
					besoinDAO.update(newNeed);
					besoinDAO.update(parent);
				}
			}
			return newNeed;
		}
		else {
			existingNeed.setName(name);
			return besoinDAO.update(existingNeed);
		}
	}

	@Override
	@Transactional
	public void deleteNeed(Long id) {
		Besoin need = besoinDAO.retrieveById(id);
		if (need != null) {
			besoinDAO.delete(need);
		}
		else {
			logger.info("Service deleteBesoin, cannot find Need with id={}", id);
		}
	}

	@Override
	@Transactional
	public void changeParentOfNeed(Long id, Long idNewParent) {
		logger.info("entering changeParentOfNeed({}, {})", id, idNewParent);
		// The "visible root node" has id=null, and we must not allow the user to move it
		if (id != null) {
			Besoin need = besoinDAO.retrieveById(id);
			logger.info("Need is retrieved");
			if (need != null) {
				logger.info("Need is not null");
				Set<Besoin> parents = need.getParents();
				if (parents != null) {
					logger.info("Need.getParents() is not null");
					Iterator<Besoin> iterator = parents.iterator();
					if (iterator != null) {
						while (iterator.hasNext()) {
							iterator.next();
							iterator.remove();
							logger.info("Need has parent removed");
						}
						need = besoinDAO.update(need);
						logger.info("Need is updated");
					}
				}
				else {
					logger.info("need.getParents() is null");
				}
			}
			if (idNewParent != null) {
				Besoin newParent = besoinDAO.retrieveById(idNewParent);
				logger.info("Service changeParentOfNeed, newParent={}", newParent);
				if (newParent != null) {
					newParent.addChild(need);
					newParent = besoinDAO.update(newParent);
					need = besoinDAO.update(need);
				}
			}
		}
		logger.info("leaving changeParentOfNeed({}, {})", id, idNewParent);
	}
}
