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
package eu.ueb.acem.dao.bleu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dao.bleu.neo4j.BesoinRepository;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Repository("besoinDAO")
public class BesoinDAO {

	@Autowired
	private BesoinRepository besoinRepository;
	
	public BesoinDAO() {
		
	}
	
	public Besoin create(String nom) {
		BesoinNode besoin = new BesoinNode(nom);
		besoinRepository.save(besoin);
		return besoin;
	}
	
	public Besoin retrieve(Long id) {
		return besoinRepository.findOne(id);
	}

	public Besoin retrieve(String nom) {
		return besoinRepository.findByPropertyValue("nom", nom);
	}
	
	public Besoin update(Besoin besoin) {
		BesoinNode besoinNode = (BesoinNode) besoin;
		return besoinRepository.save(besoinNode);
	}
	
	public void delete(Besoin besoin) {
		BesoinNode besoinNode = (BesoinNode) besoin;
		besoinRepository.delete(besoinNode);
	}
	
	public void deleteAll() {
		besoinRepository.deleteAll();
	}

	public Long count() {
		return besoinRepository.count();
	}
	
}
