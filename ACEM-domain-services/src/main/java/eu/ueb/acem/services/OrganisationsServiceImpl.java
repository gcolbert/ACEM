package eu.ueb.acem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.rouge.CommunauteDAO;
import eu.ueb.acem.dal.rouge.ComposanteDAO;
import eu.ueb.acem.dal.rouge.EtablissementDAO;
import eu.ueb.acem.dal.rouge.ServiceDAO;

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

}
