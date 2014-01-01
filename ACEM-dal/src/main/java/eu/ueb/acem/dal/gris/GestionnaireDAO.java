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
import eu.ueb.acem.dal.gris.neo4j.GestionnaireRepository;
import eu.ueb.acem.domain.beans.gris.Gestionnaire;
import eu.ueb.acem.domain.beans.gris.neo4j.GestionnaireNode;

/**
 * @author gcolbert @since 2013-12-11
 *
 */
@Repository("gestionnaireDAO")
public class GestionnaireDAO implements DAO<Gestionnaire> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(GestionnaireDAO.class);

	@Autowired
	private GestionnaireRepository repository;
	
	public GestionnaireDAO() {
		
	}
	
	@Override
	public Gestionnaire create(Gestionnaire gestionnaire) {
		return repository.save((GestionnaireNode) gestionnaire);
	}
	
	@Override
	public Gestionnaire retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Gestionnaire retrieve(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Set<Gestionnaire> retrieveAll() {
		Iterable<GestionnaireNode> endResults = repository.findAll();
		Set<Gestionnaire> set = new HashSet<Gestionnaire>();
		if (endResults.iterator() != null) {
			Iterator<GestionnaireNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Gestionnaire update(Gestionnaire gestionnaire) {
		GestionnaireNode GestionnaireNode = (GestionnaireNode) gestionnaire;
		return repository.save(GestionnaireNode);
	}
	
	@Override
	public void delete(Gestionnaire gestionnaire) {
		GestionnaireNode gestionnaireNode = (GestionnaireNode) gestionnaire;
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

}
