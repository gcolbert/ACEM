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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.EnseignantRepository;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

/**
 * @author gcolbert @since 2013-12-11
 *
 */
@Repository("enseignantDAO")
public class EnseignantDAO implements DAO<Enseignant> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnseignantRepository repository;
	
	public EnseignantDAO() {
		
	}
	
	@Override
	public void create(Enseignant enseignant) {
		repository.save((EnseignantNode) enseignant);
	}
	
	@Override
	public Enseignant retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Set<Enseignant> retrieveAll() {
		Iterable<EnseignantNode> endResults = repository.findAll();
		Set<Enseignant> set = new HashSet<Enseignant>();
		if (endResults.iterator() != null) {
			Iterator<EnseignantNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Enseignant update(Enseignant enseignant) {
		EnseignantNode EnseignantNode = (EnseignantNode) enseignant;
		return repository.save(EnseignantNode);
	}
	
	@Override
	public void delete(Enseignant enseignant) {
		EnseignantNode enseignantNode = (EnseignantNode) enseignant;
		repository.delete(enseignantNode);
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
