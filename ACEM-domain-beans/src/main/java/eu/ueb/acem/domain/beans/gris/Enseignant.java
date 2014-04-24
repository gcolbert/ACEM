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
package eu.ueb.acem.domain.beans.gris;

import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Enseignant extends Personne {

	Set<? extends Ressource> getFavoriteResources();

	void setFavoriteResources(Set<? extends Ressource> favoriteResources);

	Set<? extends SeanceDeCours> getTeachingClasses();

	void setTeachingClasses(Set<? extends SeanceDeCours> teachingClasses);

	Set<? extends Scenario> getScenarios();

	void setScenarios(Set<? extends Scenario> scenarios);

	void addAuthor(Scenario scenario);

	void removeAuthor(Scenario scenario);

	Boolean addFavoriteResource(Ressource ressourceNode);

	Boolean removeFavoriteResource(Ressource ressourceNode);

}
