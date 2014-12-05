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

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ScenariosService {

	Long countScenarios();

	PedagogicalScenario createScenario(Teacher author, String name, String objective);

	PedagogicalScenario retrieveScenario(Long id);
	
	PedagogicalScenario updateScenario(PedagogicalScenario scenario);

	Boolean deleteScenario(Long id);

	Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor);
	
	void deleteAllScenarios();

	Long countPedagogicalActivities();

	PedagogicalActivity createPedagogicalActivity(String name);

	PedagogicalActivity retrievePedagogicalActivity(Long id);

	PedagogicalActivity updatePedagogicalActivity(PedagogicalActivity pedagogicalActivity);

	Boolean deletePedagogicalActivity(Long id);

	void deleteAllPedagogicalActivities();

	Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Personne author);

}
