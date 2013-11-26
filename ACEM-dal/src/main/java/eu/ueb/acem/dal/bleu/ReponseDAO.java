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
	private ReponseRepository reponseRepository;
	
	public ReponseDAO() {
		
	}
	
	@Override
	public Reponse create(String nom) {
		ReponseNode reponse = new ReponseNode(nom);
		reponseRepository.save(reponse);
		return reponse;
	}
	
	@Override
	public Reponse retrieve(String nom) {
		return reponseRepository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Reponse update(Reponse reponse) {
		ReponseNode reponseNode = (ReponseNode) reponse;
		return reponseRepository.save(reponseNode);
	}
	
	@Override
	public void delete(Reponse reponse) {
		ReponseNode reponseNode = (ReponseNode) reponse;
		reponseRepository.delete(reponseNode);
	}
	
	@Override
	public void deleteAll() {
		reponseRepository.deleteAll();
	}

	@Override
	public Long count() {
		return reponseRepository.count();
	}

}
