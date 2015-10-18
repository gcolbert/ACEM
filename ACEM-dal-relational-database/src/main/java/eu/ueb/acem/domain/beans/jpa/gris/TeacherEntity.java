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
package eu.ueb.acem.domain.beans.jpa.gris;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalScenarioEntity;
import eu.ueb.acem.domain.beans.jpa.violet.TeachingUnitEntity;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The Spring Data JPA implementation of Teacher domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Teacher")
@XmlRootElement(name = "teachers")
public class TeacherEntity extends PersonEntity implements Teacher {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1289247284705705734L;

	@JsonManagedReference
	@ManyToMany(targetEntity = TeachingUnitEntity.class,  fetch = FetchType.LAZY)
	@JoinTable(name = "Teachers_TeachingUnits")
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	@JsonBackReference
	@ManyToMany(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY, mappedBy = "authors")
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	public TeacherEntity() {
	}

	public TeacherEntity(String name, String login, String password) {
		super(name, login, password);
		setTeacher(true);
	}

	@Override
	public Set<PedagogicalScenario> getPedagogicalScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setPedagogicalScenarios(Set<PedagogicalScenario> pedagogicalScenarios) {
		this.pedagogicalScenarios = pedagogicalScenarios;
	}

	@Override
	public Set<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	@Override
	public void setTeachingUnits(Set<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

}
