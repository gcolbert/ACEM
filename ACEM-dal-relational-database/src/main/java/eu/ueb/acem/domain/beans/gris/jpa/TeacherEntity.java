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
package eu.ueb.acem.domain.beans.gris.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.jpa.PedagogicalScenarioEntity;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.jpa.ClassEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Teacher")
@Table(name = "Teacher")
public class TeacherEntity extends PersonEntity implements Teacher {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1289247284705705734L;

	@ManyToMany(targetEntity = ClassEntity.class,  fetch = FetchType.LAZY)
	private Set<Class> classes = new HashSet<Class>(0);

	@ManyToMany(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	public TeacherEntity() {
	}

	public TeacherEntity(String name, String login, String password) {
		super(name, login, password);
		setTeacher(true);
	}
	
	@Override
	public Set<PedagogicalScenario> getScenarios() {
		return pedagogicalScenarios;
	}

	@Override
	public void setScenarios(Set<PedagogicalScenario> pedagogicalScenarios) {
		this.pedagogicalScenarios = pedagogicalScenarios;
	}
	
	@Override
	public Set<Class> getClasses() {
		return classes;
	}

	@Override
	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}

}
