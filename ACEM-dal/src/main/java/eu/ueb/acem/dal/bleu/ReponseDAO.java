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
package eu.ueb.acem.dal.bleu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.ReponseRepository;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;

/**
 * @author gcolbert @since 2013-11-26
 *
 */
@Repository("reponseDAO")
public class ReponseDAO implements DAO<Reponse> {

	@Autowired
	private ReponseRepository repository;
	
	public ReponseDAO() {
		
	}
	
	@Override
	public void create(Reponse reponse) {
		repository.save((ReponseNode) reponse);
	}
	
	@Override
	public Reponse retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Set<Reponse> retrieveAll() {
		Iterable<ReponseNode> endResults = repository.findAll();
		Set<Reponse> set = new HashSet<Reponse>();
		if (endResults.iterator() != null) {
			Iterator<ReponseNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Reponse update(Reponse reponse) {
		ReponseNode reponseNode = (ReponseNode) reponse;
		return repository.save(reponseNode);
	}
	
	@Override
	public void delete(Reponse reponse) {
		ReponseNode reponseNode = (ReponseNode) reponse;
		repository.delete(reponseNode);
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
