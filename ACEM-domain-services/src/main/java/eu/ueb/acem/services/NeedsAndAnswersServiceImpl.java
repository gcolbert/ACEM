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

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.dal.bleu.ReponseDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
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
	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersServiceImpl.class);

	@Autowired
	BesoinDAO needDAO;

	@Autowired
	ReponseDAO answerDAO;

	public NeedsAndAnswersServiceImpl() {

	}

	@Override
	public Long countNeeds() {
		return needDAO.count();
	}

	@Override
	public Besoin createNeed(String name) {
		return needDAO.create(new BesoinNode(name));
	}

	@Override
	public Collection<Besoin> retrieveNeedsAtRoot() {
		return needDAO.retrieveNeedsAtRoot();
	}

	@Override
	public Besoin retrieveNeed(Long id) {
		return needDAO.retrieveById(id);
	}

	@Override
	public Besoin updateNeed(Besoin need) {
		return needDAO.update(need);
	}

	@Override
	@Transactional
	public Boolean deleteNeed(Long id) {
		if (needDAO.exists(id)) {
			needDAO.delete(needDAO.retrieveById(id));
		}
		return (!needDAO.exists(id));
	}

	@Override
	public void deleteAllNeeds() {
		needDAO.deleteAll();
	}

	@Override
	public Long countAnswers() {
		return answerDAO.count();
	}

	@Override
	public Reponse createAnswer(String name) {
		return answerDAO.create(new ReponseNode(name));
	}

	@Override
	public Reponse retrieveAnswer(Long id) {
		return answerDAO.retrieveById(id);
	}

	@Override
	public Reponse updateAnswer(Reponse answer) {
		return answerDAO.update(answer);
	}

	@Override
	@Transactional
	public Boolean deleteAnswer(Long id) {
		if (answerDAO.exists(id)) {
			answerDAO.delete(answerDAO.retrieveById(id));
		}
		return (!answerDAO.exists(id));
	}

	@Override
	public void deleteAllAnswers() {
		answerDAO.deleteAll();
	}

	@Override
	public Collection<Besoin> getAssociatedNeedsOf(Besoin need) {
		return (Collection<Besoin>) need.getChildren();
	}

	@Override
	@Transactional
	public Besoin createOrUpdateNeed(Long id, String name, Long idParent) {
		Besoin need = null;
		if (needDAO.exists(id)) {
			need = needDAO.retrieveById(id);
			if (need.getName() != name) {
				need.setName(name);
				need = needDAO.update(need);
			}
		} else {
			need = needDAO.create(new BesoinNode(name));
		}
		if (needDAO.exists(idParent)) {
			Besoin parent = needDAO.retrieveById(idParent);
			if (!parent.getChildren().contains(need)) {
				need.addParent(parent);
				need = needDAO.update(need);
				parent = needDAO.update(parent);
			}
		}
		return need;
	}

	@Override
	public void saveNeedName(Long id, String newName) {
		if (needDAO.exists(id)) {
			Besoin need = needDAO.retrieveById(id);
			need.setName(newName);
			need = needDAO.update(need);
		}
	}

	@Override
	@Transactional
	public void changeParentOfNeed(Long id, Long idNewParent) {
		logger.info("entering changeParentOfNeed({}, {})", id, idNewParent);
		// The "visible root node" has id=null, and we must not allow the user
		// to move it
		if (id != null) {
			Besoin need = needDAO.retrieveById(id);
			logger.info("Need is retrieved");
			if (need != null) {
				logger.info("Need is not null");
				Collection<Besoin> parents = (Collection<Besoin>)need.getParents();
				if (parents != null) {
					logger.info("Need.getParents() is not null");
					Iterator<Besoin> iterator = parents.iterator();
					if (iterator != null) {
						while (iterator.hasNext()) {
							iterator.next();
							iterator.remove();
							logger.info("Need has parent removed");
						}
						need = needDAO.update(need);
						logger.info("Need is updated");
					}
				} else {
					logger.info("need.getParents() is null");
				}
			}
			if (idNewParent != null) {
				Besoin newParent = needDAO.retrieveById(idNewParent);
				logger.info("Service changeParentOfNeed, newParent={}", newParent);
				if (newParent != null) {
					newParent.addChild(need);
					newParent = needDAO.update(newParent);
					need = needDAO.update(need);
				}
			}
		}
		logger.info("leaving changeParentOfNeed({}, {})", id, idNewParent);
	}

	@Override
	public Collection<Reponse> getAssociatedAnswersOf(Besoin need) {
		return (Collection<Reponse>) need.getAnswers();
	}

	@Override
	@Transactional
	public Reponse createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed) {
		Reponse answer = null;
		if (answerDAO.exists(id)) {
			answer = answerDAO.retrieveById(id);
			answer.setName(name);
			answer = answerDAO.update(answer);
		} else {
			answer = answerDAO.create(new ReponseNode(name));
		}
		if (needDAO.exists(idAssociatedNeed)) {
			Besoin associatedNeed = needDAO.retrieveById(idAssociatedNeed);
			associatedNeed.addAnswer(answer);
			needDAO.update(associatedNeed);
			answerDAO.update(answer);
		}
		return answer;
	}

	@Override
	public void saveAnswerName(Long id, String newName) {
		if (answerDAO.exists(id)) {
			Reponse answer = answerDAO.retrieveById(id);
			answer.setName(newName);
			answer = answerDAO.update(answer);
		}
	}

	@Override
	public Collection<Scenario> getScenariosRelatedToAnswer(Long id) {
		return answerDAO.retrieveScenariosRelatedToAnswer(id);
	}
}
