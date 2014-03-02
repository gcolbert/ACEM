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
package eu.ueb.acem.dal.gris;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.GestionnaireRepository;
import eu.ueb.acem.domain.beans.gris.Gestionnaire;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.GestionnaireNode;

/**
 * @author Grégoire Colbert @since 2013-12-11
 * 
 */
@Repository("gestionnaireDAO")
public class GestionnaireDAO implements DAO<Long, Gestionnaire> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(GestionnaireDAO.class);

	@Autowired
	private GestionnaireRepository repository;

	public GestionnaireDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Gestionnaire create(Gestionnaire entity) {
		return repository.save((GestionnaireNode) entity);
	}

	@Override
	public Collection<Gestionnaire> retrieveByName(String name) {
		Iterable<GestionnaireNode> nodes = repository.findByName(name);
		Collection<Gestionnaire> entities = new HashSet<Gestionnaire>();
		for (GestionnaireNode node : nodes) {
			entities.add(node);
		}
		return entities;
		//return repository.findByPropertyValue("name", name);
	}

	@Override
	public Gestionnaire retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Gestionnaire> retrieveAll() {
		Iterable<GestionnaireNode> endResults = repository.findAll();
		Collection<Gestionnaire> collection = new HashSet<Gestionnaire>();
		if (endResults.iterator() != null) {
			Iterator<GestionnaireNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Gestionnaire update(Gestionnaire entity) {
		GestionnaireNode GestionnaireNode = (GestionnaireNode) entity;
		return repository.save(GestionnaireNode);
	}

	@Override
	public void delete(Gestionnaire entity) {
		GestionnaireNode gestionnaireNode = (GestionnaireNode) entity;
		repository.delete(gestionnaireNode);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Personne retrieveByLogin(String id) {
		return repository.findByLogin(id);
	}

}
