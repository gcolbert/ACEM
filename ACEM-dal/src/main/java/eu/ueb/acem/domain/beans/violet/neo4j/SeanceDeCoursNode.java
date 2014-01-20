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
package eu.ueb.acem.domain.beans.violet.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.vert.EspacePhysique;
import eu.ueb.acem.domain.beans.vert.neo4j.EspacePhysiqueNode;
import eu.ueb.acem.domain.beans.violet.Cours;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@NodeEntity
@TypeAlias("Course")
public class SeanceDeCoursNode implements SeanceDeCours {

	private static final long serialVersionUID = -3903886807338724952L;

	@GraphId private Long id;
	
	@RelatedTo(elementClass = CoursNode.class, type="estUnePartieDe", direction = OUTGOING)
	private Cours cours;

	@RelatedTo(elementClass = EspacePhysiqueNode.class, type="aLieuDans", direction = OUTGOING)
	private EspacePhysique lieu;
	
	private String date;
	private String heure;
	private String duree;
	private String modalite;
	private Integer nbApprenants;
	
	public SeanceDeCoursNode() {
		
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getModalite() {
		return modalite;
	}

	public void setModalite(String modalite) {
		this.modalite = modalite;
	}

	public Integer getNbApprenants() {
		return nbApprenants;
	}

	public void setNbApprenants(Integer nbApprenants) {
		this.nbApprenants = nbApprenants;
	}

	public Cours getCours() {
		return cours;
	}

	public EspacePhysique getLieu() {
		return lieu;
	}

}
