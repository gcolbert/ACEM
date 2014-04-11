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
import eu.ueb.acem.dal.jaune.neo4j.PedagogicalAndDocumentaryResourcesRepository;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourcePedagogiqueEtDocumentaireNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("pedagogicalAndDocumentaryResourcesDAO")
public class PedagogicalAndDocumentaryResourcesDAO implements DAO<Long, RessourcePedagogiqueEtDocumentaire> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalAndDocumentaryResourcesDAO.class);

	@Autowired
	private PedagogicalAndDocumentaryResourcesRepository repository;

	public PedagogicalAndDocumentaryResourcesDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return repository.count(id) > 0 ? true : false;
		}
	}

	@Override
	public RessourcePedagogiqueEtDocumentaire create(RessourcePedagogiqueEtDocumentaire entity) {
		return repository.save((RessourcePedagogiqueEtDocumentaireNode) entity);
	}

	@Override
	public RessourcePedagogiqueEtDocumentaire retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<RessourcePedagogiqueEtDocumentaire> retrieveByName(String name) {
		Iterable<RessourcePedagogiqueEtDocumentaireNode> pedagogicalAndDocumentaryResourceNodes = repository.findByName(name);
		Collection<RessourcePedagogiqueEtDocumentaire> pedagogicalAndDocumentaryResources = new HashSet<RessourcePedagogiqueEtDocumentaire>();
		for (RessourcePedagogiqueEtDocumentaire pedagogicalAndDocumentaryResource : pedagogicalAndDocumentaryResourceNodes) {
			pedagogicalAndDocumentaryResources.add(pedagogicalAndDocumentaryResource);
		}
		return pedagogicalAndDocumentaryResources;
	}

	@Override
	public Collection<RessourcePedagogiqueEtDocumentaire> retrieveAll() {
		Iterable<RessourcePedagogiqueEtDocumentaireNode> endResults = repository.findAll();
		Collection<RessourcePedagogiqueEtDocumentaire> collection = new HashSet<RessourcePedagogiqueEtDocumentaire>();
		if (endResults.iterator() != null) {
			Iterator<RessourcePedagogiqueEtDocumentaireNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public RessourcePedagogiqueEtDocumentaire update(RessourcePedagogiqueEtDocumentaire entity) {
		return repository.save((RessourcePedagogiqueEtDocumentaireNode) entity);
	}

	@Override
	public void delete(RessourcePedagogiqueEtDocumentaire entity) {
		repository.delete((RessourcePedagogiqueEtDocumentaireNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Collection<ResourceCategory> getCategories() {
		Iterable<ResourceCategoryNode> endResults = repository.getCategories();
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	public Collection<RessourcePedagogiqueEtDocumentaire> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<RessourcePedagogiqueEtDocumentaireNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<RessourcePedagogiqueEtDocumentaire> collection = new HashSet<RessourcePedagogiqueEtDocumentaire>();
		if (endResults.iterator() != null) {
			Iterator<RessourcePedagogiqueEtDocumentaireNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}
	
}
