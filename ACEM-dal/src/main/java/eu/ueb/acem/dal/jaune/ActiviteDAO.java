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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.ActiviteRepository;
import eu.ueb.acem.domain.beans.jaune.Activite;
import eu.ueb.acem.domain.beans.jaune.neo4j.ActiviteNode;

/**
 * @author gcolbert @since 2013-12-11
 *
 */
@Repository("activiteDAO")
public class ActiviteDAO implements DAO<Activite> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(ActiviteDAO.class);

	@Autowired
	private ActiviteRepository repository;
	
	public ActiviteDAO() {
		
	}
	
	@Override
	public void create(Activite activite) {
		repository.save((ActiviteNode) activite);
	}

	@Override
	public Activite retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Set<Activite> retrieveAll() {
		Iterable<ActiviteNode> endResults = repository.findAll();
		Set<Activite> set = new HashSet<Activite>();
		if (endResults.iterator() != null) {
			Iterator<ActiviteNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Activite update(Activite activite) {
		return repository.save((ActiviteNode) activite);
	}
	
	@Override
	public void delete(Activite activite) {
		repository.delete((ActiviteNode) activite);
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
