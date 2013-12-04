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

package eu.ueb.acem.domain.beans.jaune.neo4j;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import eu.ueb.acem.domain.beans.jaune.Activite;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
public class ActiviteNode extends RessourceNode implements Activite {

	private static final long serialVersionUID = 1012212357185877701L;

	@GraphId private Long id;

	@Indexed(indexName = "rechercher-activite") private String nom;

	public ActiviteNode() {
	}

	public ActiviteNode(String nom) {
		this.setNom(nom);
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

}