package eu.ueb.acem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.rouge.CommunauteDAO;
import eu.ueb.acem.dal.rouge.ComposanteDAO;
import eu.ueb.acem.dal.rouge.EtablissementDAO;
import eu.ueb.acem.dal.rouge.ServiceDAO;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.neo4j.EtablissementNode;

@Service("organisationsService")
public class OrganisationsServiceImpl implements OrganisationsService {

	@Autowired
	CommunauteDAO communityDAO;

	@Autowired
	EtablissementDAO institutionDAO;

	@Autowired
	ComposanteDAO teachingDepartmentDAO;

	@Autowired
	ServiceDAO administrativeDepartmentDAO;

	@Override
	public Long countInstitutions() {
		return institutionDAO.count();
	}

	@Override
	public Etablissement createInstitution(String name) {
		return institutionDAO.create(new EtablissementNode(name));
	}

	@Override
	public Etablissement retrieveInstitution(Long id) {
		return institutionDAO.retrieveById(id);
	}

	@Override
	public Etablissement updateInstitution(Etablissement entity) {
		return institutionDAO.update(entity);
	}

	@Override
	public Boolean deleteInstitution(Long id) {
		if (institutionDAO.exists(id)) {
			institutionDAO.delete(institutionDAO.retrieveById(id));
		}
		return (! institutionDAO.exists(id));
	}

	@Override
	public void deleteAllInstitutions() {
		institutionDAO.deleteAll();
	}

}
