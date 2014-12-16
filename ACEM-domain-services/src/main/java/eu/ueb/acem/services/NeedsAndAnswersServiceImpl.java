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
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.bleu.PedagogicalAnswerDAO;
import eu.ueb.acem.dal.bleu.PedagogicalNeedDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalNeedNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalAnswerNode;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Resource;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("needsAndAnswersService")
public class NeedsAndAnswersServiceImpl implements NeedsAndAnswersService {

	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersServiceImpl.class);

	@Inject
	private PedagogicalNeedDAO needDAO;

	@Inject
	private PedagogicalAnswerDAO answerDAO;
	
	@Inject
	private ResourceCategoryDAO resourceCategoryDAO;

	public NeedsAndAnswersServiceImpl() {

	}

	@Override
	public Boolean deleteNode(Long id) {
		if (needDAO.exists(id)) {
			return deleteNeed(id);
		}
		else if (answerDAO.exists(id)) {
			return deleteAnswer(id);
		}
		else {
			return false;
		}
	}
	
	@Override
	public Long countNeeds() {
		return needDAO.count();
	}

	@Override
	public PedagogicalNeed createPedagogicalNeed(String name) {
		return needDAO.create(new PedagogicalNeedNode(name));
	}

	@Override
	public Collection<PedagogicalNeed> retrieveNeedsAtRoot() {
		return needDAO.retrieveNeedsAtRoot();
	}

	@Override
	public PedagogicalNeed retrievePedagogicalNeed(Long id, boolean initialize) {
		return needDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalNeed updatePedagogicalNeed(PedagogicalNeed pedagogicalNeed) {
		PedagogicalNeed updatedEntity = needDAO.update(pedagogicalNeed);
		return retrievePedagogicalNeed(updatedEntity.getId(), true);
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
	public PedagogicalAnswer createPedagogicalAnswer(String name) {
		return answerDAO.create(new PedagogicalAnswerNode(name));
	}

	@Override
	public PedagogicalAnswer retrievePedagogicalAnswer(Long id, boolean initialize) {
		return answerDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalAnswer updatePedagogicalAnswer(PedagogicalAnswer pedagogicalAnswer) {
		PedagogicalAnswer updatedEntity = answerDAO.update(pedagogicalAnswer);
		return retrievePedagogicalAnswer(updatedEntity.getId(), true);
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
	@Transactional
	public PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent) {
		PedagogicalNeed need = null;
		if (needDAO.exists(id)) {
			need = needDAO.retrieveById(id);
			if (need.getName() != name) {
				need.setName(name);
				need = needDAO.update(need);
			}
		} else {
			need = needDAO.create(new PedagogicalNeedNode(name));
		}
		if (needDAO.exists(idParent)) {
			PedagogicalNeed parent = needDAO.retrieveById(idParent);
			if (!parent.getChildren().contains(need)) {
				need.getParents().add(parent);
				parent.getChildren().add(need);
				need = needDAO.update(need);
				parent = needDAO.update(parent);
			}
		}
		return need;
	}

	@Override
	public void saveNeedName(Long id, String newName) {
		if (needDAO.exists(id)) {
			PedagogicalNeed need = needDAO.retrieveById(id);
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
			PedagogicalNeed need = needDAO.retrieveById(id);
			logger.info("Need is retrieved");
			if (need != null) {
				logger.info("Need is not null");
				Collection<PedagogicalNeed> parents = need.getParents();
				if (parents != null) {
					logger.info("Need.getParents() is not null");
					Iterator<PedagogicalNeed> iterator = parents.iterator();
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
				PedagogicalNeed newParent = needDAO.retrieveById(idNewParent);
				logger.info("Service changeParentOfNeed, newParent={}", newParent);
				if (newParent != null) {
					newParent.getChildren().add(need);
					need.getParents().add(newParent);
					newParent = needDAO.update(newParent);
					need = needDAO.update(need);
				}
			}
		}
		logger.info("leaving changeParentOfNeed({}, {})", id, idNewParent);
	}

	@Override
	@Transactional
	public PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed) {
		PedagogicalAnswer answer = null;
		if (answerDAO.exists(id)) {
			answer = answerDAO.retrieveById(id);
			answer.setName(name);
			answer = answerDAO.update(answer);
		} else {
			answer = answerDAO.create(new PedagogicalAnswerNode(name));
		}
		if (needDAO.exists(idAssociatedNeed)) {
			PedagogicalNeed associatedNeed = needDAO.retrieveById(idAssociatedNeed);
			associatedNeed.getAnswers().add(answer);
			answer.getNeeds().add(associatedNeed);
			needDAO.update(associatedNeed);
			answerDAO.update(answer);
		}
		return answer;
	}

	@Override
	public void saveAnswerName(Long id, String newName) {
		if (answerDAO.exists(id)) {
			PedagogicalAnswer answer = answerDAO.retrieveById(id);
			answer.setName(newName);
			answer = answerDAO.update(answer);
		}
	}

	@Override
	public Collection<PedagogicalScenario> getScenariosRelatedToAnswer(Long id) {
		PedagogicalAnswer answer = answerDAO.retrieveById(id);
		return answer.getScenariosRelatedToAnswer();
	}

	@Override
	public Collection<Resource> getResourcesRelatedToAnswer(Long id) {
		PedagogicalAnswer answer = answerDAO.retrieveById(id);
		Collection<Resource> resources = new HashSet<Resource>();
		for (ResourceCategory resourceCategory : answer.getResourceCategories()) {
			resources.addAll(resourceCategory.getResources());
		}
		return resources;
	}

	@Override
	public Boolean associateAnswerWithResourceCategory(Long answerId, Long toolCategoryId) {
		PedagogicalAnswer answer = answerDAO.retrieveById(answerId);
		ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId);

		answer.getResourceCategories().add(resourceCategory);
		resourceCategory.getAnswers().add(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return ((answer.getResourceCategories().contains(resourceCategory)) && (resourceCategory.getAnswers().contains(resourceCategory)));
	}

	@Override
	public Boolean dissociateAnswerWithResourceCategory(Long answerId, Long toolCategoryId) {
		PedagogicalAnswer answer = answerDAO.retrieveById(answerId);
		ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId);

		answer.getResourceCategories().remove(resourceCategory);
		resourceCategory.getAnswers().remove(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return ((!answer.getResourceCategories().contains(resourceCategory)) && (!resourceCategory.getAnswers().contains(resourceCategory)));
	}

}
