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

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalAnswerNode;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("AdministrativeDepartment")
public class AdministrativeDepartmentNode extends OrganisationNode implements AdministrativeDepartment {

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AdministrativeDepartmentNode.class);

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3548058788461596320L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = InstitutionNode.class, type = "administrativeDepartmentPartOfInstitution", direction = OUTGOING)
	@Fetch
	private Set<InstitutionNode> institutions;

	@RelatedTo(elementClass = PedagogicalAnswerNode.class, type="answeredByAdministrativeDepartment", direction = INCOMING)
	@Fetch
	private Set<PedagogicalAnswerNode> pedagogicalAnswers;
	
	public AdministrativeDepartmentNode() {
		institutions = new HashSet<InstitutionNode>();
	}

	public AdministrativeDepartmentNode(String name, String shortname, String iconFileName) {
		this();
		this.setName(name);
		this.setShortname(shortname);
		this.setIconFileName(iconFileName);
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
	public Set<? extends Institution> getInstitutions() {
		return institutions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setInstitutions(Set<? extends Institution> institutions) {
		this.institutions = (Set<InstitutionNode>) institutions;
	}

	@Override
	@Transactional
	public void addInstitution(Institution institution) {
		if (! institutions.contains(institution)) {
			institutions.add((InstitutionNode)institution);
		}
		if (! institution.getAdministrativeDepartments().contains(this)) {
			institution.addAdministrativeDepartment(this);
		}
	}

	@Override
	@Transactional
	public void removeInstitution(Institution institution) {
		if (institutions.contains(institution)) {
			institutions.remove(institution);
		}
		if (institution.getAdministrativeDepartments().contains(this)) {
			institution.removeAdministrativeDepartment(this);
		}
	}

	@Override
	public Set<? extends PedagogicalAnswer> getPedagogicalAnswers() {
		return pedagogicalAnswers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPedagogicalAnswers(Set<? extends PedagogicalAnswer> pedagogicalAnswers) {
		this.pedagogicalAnswers = (Set<PedagogicalAnswerNode>) pedagogicalAnswers;
	}

	@Override
	@Transactional
	public void addPedagogicalAnswer(PedagogicalAnswer answer) {
		if (! pedagogicalAnswers.contains(answer)) {
			pedagogicalAnswers.add((PedagogicalAnswerNode)answer);
		}
		if (! answer.getAdministrativeDepartments().contains(this)) {
			answer.addAdministrativeDepartment(this);
		}
	}

	@Override
	@Transactional
	public void removePedagogicalAnswer(PedagogicalAnswer answer) {
		if (pedagogicalAnswers.contains(answer)) {
			pedagogicalAnswers.remove(answer);
		}
		if (answer.getAdministrativeDepartments().contains(this)) {
			answer.removeAdministrativeDepartment(this);
		}
	}
	
}
