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
package eu.ueb.acem.domain.beans.rouge.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation:Administrative_department")
public class ServiceNode extends OrganisationNode implements Service {

	private static final long serialVersionUID = -5662533287772515643L;

	@Indexed(indexName = "indexOfAdministrativeDepartments")
	private String name;

	@RelatedTo(elementClass = EtablissementNode.class, type = "administrativeDepartmentPartOfInstitution", direction = OUTGOING)
	@Fetch
	private Set<EtablissementNode> institutions;

	public ServiceNode() {
	}

	public ServiceNode(String name) {
		this.setName(name);
	}

	@Override
	public Set<? extends Etablissement> getInstitutions() {
		return institutions;
	}

	@Override
	public void setInstitutions(Set<? extends Etablissement> institutions) {
		this.institutions = (Set<EtablissementNode>) institutions;
	}

}
