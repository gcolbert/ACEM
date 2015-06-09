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

import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("AdministrativeDepartment")
public class AdministrativeDepartmentNode extends OrganisationNode implements AdministrativeDepartment {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3548058788461596320L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = InstitutionNode.class, type = "administrativeDepartmentPartOfInstitution", direction = OUTGOING)
	private Set<Institution> institutions = new HashSet<Institution>(0);

	public AdministrativeDepartmentNode() {
	}

	public AdministrativeDepartmentNode(String name, String shortname, String iconFileName) {
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

}
