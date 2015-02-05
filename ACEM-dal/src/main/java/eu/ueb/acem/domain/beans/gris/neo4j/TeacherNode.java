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
package eu.ueb.acem.domain.beans.gris.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.neo4j.ClassNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Teacher")
public class TeacherNode extends PersonNode implements Teacher {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3193454107919543890L;

	@RelatedTo(elementClass = ResourceCategoryNode.class, type = "hasFavoriteToolCategory", direction = OUTGOING)
	private Set<ResourceCategory> favoriteToolCategories = new HashSet<ResourceCategory>(0);

	@RelatedTo(elementClass = ClassNode.class, type = "leadsClass", direction = OUTGOING)
	private Set<Class> classes = new HashSet<Class>(0);

	@RelatedTo(elementClass = PedagogicalScenarioNode.class, type = "authorsScenario", direction = OUTGOING)
	private Set<PedagogicalScenario> pedagogicalScenarios = new HashSet<PedagogicalScenario>(0);

	public TeacherNode() {
	}

	public TeacherNode(String name, String login, String password) {
		super(name, login, password);
	}

	@Override
	public Set<ResourceCategory> getFavoriteToolCategories() {
		return favoriteToolCategories;
	}

	@Override
	public void setFavoriteToolCategories(Set<ResourceCategory> favoriteToolCategories) {
		this.favoriteToolCategories = favoriteToolCategories;
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
