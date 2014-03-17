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
package eu.ueb.acem.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.jaune.EquipmentDAO;
import eu.ueb.acem.dal.jaune.PedagogicalAndDocumentaryResourcesDAO;
import eu.ueb.acem.dal.jaune.ProfessionalTrainingDAO;
import eu.ueb.acem.dal.jaune.SoftwareDAO;
import eu.ueb.acem.dal.jaune.SoftwareDocumentationDAO;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.domain.beans.jaune.neo4j.ApplicatifNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationApplicatifNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipementNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.FormationProfessionnelleNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourcePedagogiqueEtDocumentaireNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {

	private static final Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Autowired
	private EquipmentDAO equipmentDAO;
	
	@Autowired
	private PedagogicalAndDocumentaryResourcesDAO pedagogicalAndDocumentaryResourcesDAO;
	
	@Autowired
	private ProfessionalTrainingDAO professionalTrainingDAO;
	
	@Autowired
	private SoftwareDAO softwareDAO;
	
	@Autowired
	private SoftwareDocumentationDAO softwareDocumentationDAO;
	
	@Override
	public Collection<String> getCategoriesForResourceType(String resourceType) {
		Set<String> categories = new HashSet<String>();
		switch (resourceType) {
		case "software":
			categories.addAll(softwareDAO.getCategories());
			break;
		case "softwareDocumentation":
			categories.addAll(softwareDocumentationDAO.getCategories());
			break;
		case "professionalTraining":
			categories.addAll(professionalTrainingDAO.getCategories());
			break;
		case "equipment":
			categories.addAll(equipmentDAO.getCategories());
			break;
		case "pedagogicalAndDocumentaryResources":
			categories.addAll(pedagogicalAndDocumentaryResourcesDAO.getCategories());
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
		return categories;
	}

	@Override
	public void createResource(String resourceType, String category, String name) {
		Ressource entity;
		switch (resourceType) {
		case "software":
			entity = new ApplicatifNode(name);
			entity.setCategory(category);
			entity = softwareDAO.create((Applicatif) entity);
			break;
		case "softwareDocumentation":
			entity = new DocumentationApplicatifNode(name);
			entity.setCategory(category);
			entity = softwareDocumentationDAO.create((DocumentationApplicatif) entity);
			break;
		case "professionalTraining":
			entity = new FormationProfessionnelleNode(name);
			entity.setCategory(category);
			entity = professionalTrainingDAO.create((FormationProfessionnelle) entity);
			break;
		case "equipment":
			entity = new EquipementNode(name);
			entity.setCategory(category);
			entity = equipmentDAO.create((Equipement) entity);
			break;
		case "pedagogicalAndDocumentaryResources":
			entity = new RessourcePedagogiqueEtDocumentaireNode(name);
			entity.setCategory(category);
			entity = pedagogicalAndDocumentaryResourcesDAO.create((RessourcePedagogiqueEtDocumentaire) entity);
			break;
		default:
			logger.error("Unknown resourceType '{}'", resourceType);
			break;
		}
	}

	@Override
	public Collection<Applicatif> getSoftwaresWithCategory(String category) {
		if (category != null) {
			return softwareDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDAO.retrieveAll();
		}
	}

	@Override
	public Collection<DocumentationApplicatif> getSoftwareDocumentationsWithCategory(String category) {
		if (category != null) {
			return softwareDocumentationDAO.retrieveAllWithCategory(category);
		}
		else {
			return softwareDocumentationDAO.retrieveAll();
		}
	}

	@Override
	public Collection<Equipement> getEquipmentWithCategory(String category) {
		if (category != null) {
			return equipmentDAO.retrieveAllWithCategory(category);
		}
		else {
			return equipmentDAO.retrieveAll();
		}
	}

	@Override
	public Collection<FormationProfessionnelle> getProfessionalTrainingsWithCategory(String category) {
		if (category != null) {
			return professionalTrainingDAO.retrieveAllWithCategory(category);
		}
		else {
			return professionalTrainingDAO.retrieveAll();
		}
	}

	@Override
	public Collection<RessourcePedagogiqueEtDocumentaire> getPedagogicalAndDocumentaryResourcesWithCategory(
			String category) {
		if (category != null) {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAllWithCategory(category);
		}
		else {
			return pedagogicalAndDocumentaryResourcesDAO.retrieveAll();
		}
	}

	@Override
	public void saveResourceName(Long id, String label) {
		
	}

}
