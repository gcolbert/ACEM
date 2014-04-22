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
package eu.ueb.acem.domain.beans.bleu;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Besoin extends Serializable, Comparable<Besoin> {

	Long getId();

	String getName();

	void setName(String name);

	Set<? extends Besoin> getParents();

	void setParents(Set<? extends Besoin> parents);

	Set<? extends Besoin> getChildren();

	void setChildren(Set<? extends Besoin> children);

	Set<? extends Reponse> getAnswers();

	void setAnswers(Set<? extends Reponse> reponses);

	void addParent(Besoin parent);

	void addChild(Besoin need);

	void addAnswer(Reponse answer);

	void removeParent(Besoin need);

	void removeChild(Besoin need);

	void removeAnswer(Reponse answer);

	int compareTo(Besoin o);

}
