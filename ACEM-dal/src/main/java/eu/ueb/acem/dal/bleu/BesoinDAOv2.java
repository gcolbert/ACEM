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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.GenericDAO;
import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.BesoinRepository;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 * TODO : this class is not used for now, because Spring 3 doesn't allow the autowiring of generics.
 * 	It is solved in Spring 4 :
 * 	 - http://www.jayway.com/2013/11/03/spring-and-autowiring-of-generic-types/
 *   - https://spring.io/blog/2013/12/03/spring-framework-4-0-and-java-generics
 *   but for now, we cannot use Spring 4 because Spring Data Neo4j isn't yet compatible.
 */
//@Repository("besoinDAOv2") // Uncomment to use this class
@SuppressWarnings("unused")
public class BesoinDAOv2 extends GenericDAO<Long, Besoin, BesoinNode> implements DAO<Long, Besoin> {

	/**
	 * For Logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BesoinDAOv2.class);

	//@Autowired // Uncomment to use this class
	private BesoinRepository repository;

	public BesoinDAOv2() {
	}

	/*
	public Collection<Besoin> retrieveAssociatedNeedsOf(Besoin need) {
		EndResult<BesoinNode> nodes;
		Map<String, Object> params = new HashMap<String, Object>();
		if (need != null) {
			params.put("id", need.getId());
			nodes = repository.query("start need=node({id}) match (n)-[:aPourBesoinParent]->(need) return n", params);
			// nodes = repository.findAssociatedNeedsOf(need.getId());
		} else {
			nodes = repository
					.query("start n=node(*) where has(n.__type__) and n.__type__ = 'Pedagogical_need' and not (n)-[:aPourBesoinParent]->() return n",
							params);
			// nodes = repository.findRoots();
		}

		Collection<Besoin> children = new HashSet<Besoin>();
		for (BesoinNode childNode : nodes) {
			children.add(childNode);
		}
		return children;
	}

	public Collection<Reponse> retrieveAssociatedAnswersOf(Besoin need) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", need.getId());
		Set<ReponseNode> nodes = (Set<ReponseNode>)need.getAnswers();
		//Collection<ReponseNode> nodes = repository.findAssociatedAnswersOf(need.getId());
		// EndResult<ReponseNode> nodes =
		// repository.query("start need=node({id}) match (need)-[:aPourReponse]->(n) return n",
		// params);
		Set<Reponse> reponses = new HashSet<Reponse>();
		for (ReponseNode node : nodes) {
			reponses.add(node);
		}
		return reponses;
	}
	*/

}
