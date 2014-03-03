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

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;

public interface ScenariosService {

	Long countScenarios();

	Scenario createScenario(Enseignant author, String name, String objective);

	Scenario retrieveScenario(Long id);
	
	Scenario updateScenario(Scenario scenario);

	Boolean deleteScenario(Long id);

	void deleteAllScenarios();

	Long countPedagogicalActivities();

	ActivitePedagogique createPedagogicalActivity(String name);

	ActivitePedagogique retrievePedagogicalActivity(Long id);

	ActivitePedagogique updatePedagogicalActivity(ActivitePedagogique pedagogicalActivity);

	Boolean deletePedagogicalActivity(Long id);

	void deleteAllPedagogicalActivities();

	Collection<Scenario> retrieveScenariosWithAuthor(Personne author);
	
}
