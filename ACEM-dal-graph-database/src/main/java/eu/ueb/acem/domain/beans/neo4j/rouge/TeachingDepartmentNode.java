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
package eu.ueb.acem.domain.beans.neo4j.rouge;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.neo4j.violet.TeachingUnitNode;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The Spring Data Neo4j implementation of TeachingDepartment domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("TeachingDepartment")
public class TeachingDepartmentNode extends OrganisationNode implements TeachingDepartment {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5662533287772515643L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = InstitutionNode.class, type = "teachingDepartmentPartOfInstitution", direction = OUTGOING)
	private Set<Institution> institutions = new HashSet<Institution>(0);

	@RelatedTo(elementClass = TeachingUnitNode.class, type = "teachesTeachingUnit", direction = OUTGOING)
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	public TeachingDepartmentNode() {
	}

	public TeachingDepartmentNode(String name, String shortname, String iconFileName) {
		this();
		setName(name);
		setShortname(shortname);
		setIconFileName(iconFileName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Set<Institution> getInstitutions() {
		return institutions;
	}

	@Override
	public void setInstitutions(Set<Institution> institutions) {
		this.institutions = institutions;
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
