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
import eu.ueb.acem.dal.jaune.neo4j.EquipmentRepository;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipementNode;

/**
 * @author Grégoire Colbert @since 2014-03-11
 * 
 */
@Repository("equipmentDAO")
public class EquipmentDAO implements DAO<Long, Equipement> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EquipmentDAO.class);

	@Autowired
	private EquipmentRepository repository;

	public EquipmentDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Equipement create(Equipement entity) {
		return repository.save((EquipementNode) entity);
	}

	@Override
	public Equipement retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Equipement> retrieveByName(String name) {
		Iterable<EquipementNode> stationaryEquipmentNodes = repository.findByName(name);
		Collection<Equipement> stationaryEquipments = new HashSet<Equipement>();
		for (Equipement software : stationaryEquipmentNodes) {
			stationaryEquipments.add(software);
		}
		return stationaryEquipments;
	}

	@Override
	public Collection<Equipement> retrieveAll() {
		Iterable<EquipementNode> endResults = repository.findAll();
		Collection<Equipement> collection = new HashSet<Equipement>();
		if (endResults.iterator() != null) {
			Iterator<EquipementNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Equipement update(Equipement entity) {
		return repository.save((EquipementNode) entity);
	}

	@Override
	public void delete(Equipement entity) {
		repository.delete((EquipementNode) entity);
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
