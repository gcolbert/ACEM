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

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("SoftwareDocumentation")
public class DocumentationApplicatifNode extends RessourceNode implements DocumentationApplicatif {

	private static final long serialVersionUID = -2471928076966986715L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = ApplicatifNode.class, type = "documentsSoftware", direction = OUTGOING)
	@Fetch
	private Set<ApplicatifNode> softwares;

	public DocumentationApplicatifNode() {
	}

	public DocumentationApplicatifNode(String name) {
		setName(name);
	}

	@Override
	public Set<? extends Applicatif> getApplicatifs() {
		return softwares;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setApplicatifs(Set<? extends Applicatif> softwares) {
		this.softwares = (Set<ApplicatifNode>)softwares;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
