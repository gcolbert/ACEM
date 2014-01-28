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

import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.dal.bleu.ReponseDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
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
	public Besoin createNeed(String name) {
		return besoinDAO.create(new BesoinNode(name));
	}

	@Override
	public Besoin retrieveNeed(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Besoin updateNeed(Besoin need) {
		return besoinDAO.update(need);
	}

	@Override
	public void deleteAllNeeds() {
		besoinDAO.deleteAll();
	}

	@Override
	public Long countNeeds() {
		return besoinDAO.count();
	}

	@Override
	public Reponse createAnswer(String name) {
		return reponseDAO.create(new ReponseNode(name));
	}
	
	@Override
	public Reponse retrieveAnswer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reponse updateAnswer(Reponse answer) {
		return reponseDAO.update(answer);
	}

	@Override
	public void deleteAllAnswers() {
		reponseDAO.deleteAll();
	}

	@Override
	public Long countAnswers() {
		return reponseDAO.count();
	}
	
	@Override
	public Set<Besoin> getAssociatedNeedsOf(Besoin need) {
		return besoinDAO.retrieveAssociatedNeedsOf(need);
	}

	@Override
	public Set<Reponse> getAssociatedAnswersOf(Besoin need) {
		return besoinDAO.retrieveAssociatedAnswersOf(need);
	}

	@Override
	@Transactional
	public Besoin createOrUpdateNeed(Long id, String name, Long idParent) {
		Besoin need = null;
		if (besoinDAO.exists(id)) {
			need = besoinDAO.retrieveById(id);
			if (need.getName() != name) {
				need.setName(name);
				need = besoinDAO.update(need);
			}
		}
		else {
			need = besoinDAO.create(new BesoinNode(name));
		}
		if (besoinDAO.exists(idParent)) {
			Besoin parent = besoinDAO.retrieveById(idParent);
			if (! parent.getChildren().contains(need)) {
				need.addParent(parent);
				need = besoinDAO.update(need);
				parent = besoinDAO.update(parent);
			}
		}
		return need;
	}

	@Override
	@Transactional
	public Boolean deleteNeed(Long id) {
		if (besoinDAO.exists(id)) {
			besoinDAO.delete(besoinDAO.retrieveById(id));
		}
		return (! besoinDAO.exists(id));
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

	@Override
	@Transactional
	public Reponse createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed) {
		Reponse answer = null;
		if (reponseDAO.exists(id)) {
			answer = reponseDAO.retrieveById(id);
			answer.setName(name);
			answer = reponseDAO.update(answer);
		}
		else {
			answer = reponseDAO.create(new ReponseNode(name));
		}
		if (besoinDAO.exists(idAssociatedNeed)) {
			Besoin associatedNeed = besoinDAO.retrieveById(idAssociatedNeed);
			associatedNeed.addAnswer(answer);
			besoinDAO.update(associatedNeed);
			reponseDAO.update(answer);
		}
		return answer;
	}

	@Override
	public void saveNeedName(Long id, String newName) {
		if (besoinDAO.exists(id)) {
			Besoin need = besoinDAO.retrieveById(id);
			need.setName(newName);
			need = besoinDAO.update(need);
		}
	}

	@Override
	public void saveAnswerName(Long id, String newName) {
		if (reponseDAO.exists(id)) {
			Reponse answer = reponseDAO.retrieveById(id);
			answer.setName(newName);
			answer = reponseDAO.update(answer);
		}
	}
}
