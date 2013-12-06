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
import java.util.Collection;

import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
public interface Reponse extends Serializable {

	public Long getId();
	
	public String getNom();

	public void setNom(String nom);

	public Collection<Besoin> getBesoins();

	public Collection<Ressource> getRessources();

	void addBesoin(Besoin besoin);

	void addRessource(Ressource ressource);

}
