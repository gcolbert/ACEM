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
package eu.ueb.acem.dal.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.SoftwareDocumentationRepository;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationApplicatifNode;

/**
 * @author Grégoire Colbert @since 2014-03-11
 * 
 */
@Repository("softwareDocumentationDAO")
public class SoftwareDocumentationDAO implements DAO<Long, DocumentationApplicatif> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SoftwareDocumentationDAO.class);

	@Autowired
	private SoftwareDocumentationRepository repository;

	public SoftwareDocumentationDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public DocumentationApplicatif create(DocumentationApplicatif entity) {
		return repository.save((DocumentationApplicatifNode) entity);
	}

	@Override
	public DocumentationApplicatif retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<DocumentationApplicatif> retrieveByName(String name) {
		Iterable<DocumentationApplicatifNode> softwareNodes = repository.findByName(name);
		Collection<DocumentationApplicatif> softwares = new HashSet<DocumentationApplicatif>();
		for (DocumentationApplicatif software : softwareNodes) {
			softwares.add(software);
		}
		return softwares;
	}

	@Override
	public Collection<DocumentationApplicatif> retrieveAll() {
		Iterable<DocumentationApplicatifNode> endResults = repository.findAll();
		Collection<DocumentationApplicatif> collection = new HashSet<DocumentationApplicatif>();
		if (endResults.iterator() != null) {
			Iterator<DocumentationApplicatifNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public DocumentationApplicatif update(DocumentationApplicatif entity) {
		return repository.save((DocumentationApplicatifNode) entity);
	}

	@Override
	public void delete(DocumentationApplicatif entity) {
		repository.delete((DocumentationApplicatifNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
