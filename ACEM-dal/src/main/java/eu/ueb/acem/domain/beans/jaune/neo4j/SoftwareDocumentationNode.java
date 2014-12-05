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

import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("SoftwareDocumentation")
public class SoftwareDocumentationNode extends ResourceNode implements SoftwareDocumentation {

	private static final long serialVersionUID = -2471928076966986715L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = SoftwareNode.class, type = "documentsSoftware", direction = OUTGOING)
	@Fetch
	private Set<SoftwareNode> softwares;

	public SoftwareDocumentationNode() {
	}

	public SoftwareDocumentationNode(String name, String iconFileName) {
		this();
		setName(name);
		setIconFileName(iconFileName);
	}

	@Override
	public Set<? extends Software> getApplicatifs() {
		return softwares;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setApplicatifs(Set<? extends Software> softwares) {
		this.softwares = (Set<SoftwareNode>)softwares;
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
	public void addApplicatif(Software software) {
		if (! softwares.contains(software)) {
			softwares.add((SoftwareNode)software);
		}
		if (! software.getDocumentations().contains(this)) {
			software.addDocumentation(this);
		}
	}

	@Override
	public void removeApplicatif(Software software) {
		if (softwares.contains(software)) {
			softwares.remove(software);
		}
		if (software.getDocumentations().contains(this)) {
			software.removeDocumentation(this);
		}
	}
	
}
