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
import eu.ueb.acem.dal.jaune.neo4j.ActiviteRepository;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.neo4j.FormationProfessionnelleNode;

/**
 * @author Grégoire Colbert @since 2013-12-11
 * 
 */
@Repository("activiteDAO")
public class ActiviteDAO implements DAO<Long, FormationProfessionnelle> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ActiviteDAO.class);

	@Autowired
	private ActiviteRepository repository;

	public ActiviteDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public FormationProfessionnelle create(FormationProfessionnelle activite) {
		return repository.save((FormationProfessionnelleNode) activite);
	}

	@Override
	public FormationProfessionnelle retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public FormationProfessionnelle retrieveByName(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Collection<FormationProfessionnelle> retrieveAll() {
		Iterable<FormationProfessionnelleNode> endResults = repository.findAll();
		Collection<FormationProfessionnelle> collection = new HashSet<FormationProfessionnelle>();
		if (endResults.iterator() != null) {
			Iterator<FormationProfessionnelleNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public FormationProfessionnelle update(FormationProfessionnelle activite) {
		return repository.save((FormationProfessionnelleNode) activite);
	}

	@Override
	public void delete(FormationProfessionnelle activite) {
		repository.delete((FormationProfessionnelleNode) activite);
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
