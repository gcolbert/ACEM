/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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

import java.io.Serializable;
import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ScenariosService extends Serializable {

	Long countScenarios();

	PedagogicalScenario createScenario(Teacher author, String name, String objective);

	PedagogicalScenario retrievePedagogicalScenario(Long id, boolean initialize);

	PedagogicalScenario updateScenario(PedagogicalScenario pedagogicalScenario);

	Boolean deleteScenario(Long id);

	Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author);

	Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor);


	Long countPedagogicalSequences();

	PedagogicalSequence createPedagogicalSequence(String name);

	PedagogicalSequence retrievePedagogicalSequence(Long id, boolean initialize);

	PedagogicalSequence updatePedagogicalSequence(PedagogicalSequence pedagogicalSequence);

	Boolean deletePedagogicalSequence(Long id);

	Collection<PedagogicalSequence> getFirstPedagogicalSequencesOfScenario(PedagogicalScenario pedagogicalScenario);


	Long countPedagogicalSessions();

	PedagogicalSession createPedagogicalSession(String name);

	PedagogicalSession retrievePedagogicalSession(Long id, boolean initialize);

	PedagogicalSession updatePedagogicalSession(PedagogicalSession pedagogicalSession);

	Boolean deletePedagogicalSession(Long id);

	Collection<PedagogicalSession> getFirstPedagogicalSessionsOfSequence(PedagogicalSequence pedagogicalSequence);


	Long countPedagogicalActivities();

	PedagogicalActivity createPedagogicalActivity(String name);

	PedagogicalActivity retrievePedagogicalActivity(Long id, boolean initialize);

	PedagogicalActivity updatePedagogicalActivity(PedagogicalActivity pedagogicalActivity);

	Boolean deletePedagogicalActivity(Long id);

	Collection<PedagogicalActivity> getFirstPedagogicalActivitiesOfSession(PedagogicalSession pedagogicalSession);

	Boolean addActivityToSession(PedagogicalActivity pedagogicalActivityToAdd, PedagogicalSession pedagogicalSession,
			PedagogicalActivity previousPedagogicalActivity, PedagogicalActivity nextPedagogicalActivity);

}
