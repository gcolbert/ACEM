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

import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

/**
 * @author Grégoire Colbert @since 2013-11-20
 *
 */
public interface NeedsAndAnswersService {

	public Long countNeeds();

	public Besoin createNeed(String name);
	
	public Besoin retrieveNeed(Long id);

	public Besoin updateNeed(Besoin need);

	public Boolean deleteNeed(Long id);

	public void deleteAllNeeds();
	
	public Long countAnswers();

	public Reponse createAnswer(String name);

	public Reponse retrieveAnswer(Long id);
	
	public Reponse updateAnswer(Reponse answer);

	public void deleteAllAnswers();

	public Set<Besoin> getAssociatedNeedsOf(Besoin need);

	public Besoin createOrUpdateNeed(Long id, String name, Long idParent);

	public void changeParentOfNeed(Long id, Long idNewParent);

	public Set<Reponse> getAssociatedAnswersOf(Besoin need);

	public Reponse createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	public void saveNeedName(Long id, String newName);

	public void saveAnswerName(Long id, String newName);
	
}
